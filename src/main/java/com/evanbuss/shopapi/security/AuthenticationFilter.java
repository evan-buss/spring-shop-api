package com.evanbuss.shopapi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.evanbuss.shopapi.model.SignInRequest;
import com.evanbuss.shopapi.repository.UserRepository;
import com.evanbuss.shopapi.repository.UserService;
import com.evanbuss.shopapi.model.User;
import com.evanbuss.shopapi.model.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final UserRepository userRepository;

	private final String TOKEN_SECRET = "GedYikZYaGn95SR7c6bSDnQzyA%VX2xJ&*hn!t@sV7Gm*R7rU*5CctwqJPBa2hTYHEz3F!6vsvi&4qGH9fC%U!uUe*iz!TwJYp&9g@VLM5F*u@NJ#WLd%#K@dW^4Mxut";
	private final Algorithm secret = Algorithm.HMAC256(TOKEN_SECRET);

	public AuthenticationFilter(AuthenticationManager manager, UserRepository userRepository) {
		super.setAuthenticationManager(manager);
		this.userRepository = userRepository;

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			SignInRequest creds = new ObjectMapper().readValue(request.getInputStream(), SignInRequest.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getEmail();

		User user = userRepository.findByEmail(username);

		String token = JWT.create().withSubject(String.)
				.withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong("3600000"))).sign(secret).build();

		response.addHeader("Token", token);
		response.addHeader("UserID", user.getId());
	}
}
