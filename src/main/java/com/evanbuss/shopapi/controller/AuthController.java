package com.evanbuss.shopapi.controller;

import com.evanbuss.shopapi.message.request.LoginRequest;
import com.evanbuss.shopapi.message.request.SignUpRequest;
import com.evanbuss.shopapi.message.response.JwtResponse;
import com.evanbuss.shopapi.models.Role;
import com.evanbuss.shopapi.models.RoleName;
import com.evanbuss.shopapi.models.User;
import com.evanbuss.shopapi.repository.RoleRepository;
import com.evanbuss.shopapi.repository.UserRepository;
import com.evanbuss.shopapi.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  @Autowired PasswordEncoder encoder;

  @Autowired JwtProvider jwtProvider;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateJwtToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<String>(
          "Fail -> Username is already taken!", HttpStatus.BAD_REQUEST);
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<String>("Fail -> Email is already in use!", HttpStatus.BAD_REQUEST);
    }

    // Creating user's account
    User user =
        new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    strRoles.forEach(
        role -> {
          switch (role) {
            case "member":
              Role adminRole =
                  roleRepository
                      .findByName(RoleName.ROLE_MEMBER)
                      .orElseThrow(
                          () -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(adminRole);

              break;
            case "owner":
              Role pmRole =
                  roleRepository
                      .findByName(RoleName.ROLE_OWNER)
                      .orElseThrow(
                          () -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(pmRole);

              break;
            default:
              Role userRole =
                  roleRepository
                      .findByName(RoleName.ROLE_MEMBER)
                      .orElseThrow(
                          () -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(userRole);
          }
        });

    user.setRoles(roles);
    userRepository.save(user);

    String token = jwtProvider.generateJWTToken(user);

    return ResponseEntity.ok().body(token);
  }
}
