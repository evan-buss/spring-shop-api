package com.evanbuss.shopapi.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.evanbuss.shopapi.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrinciple implements UserDetails {

  private static final long serialVersionUID = 1L;
  private final Long id;
  private final String username;
  private final String email;
  @JsonIgnore private final User user;
  @JsonIgnore private final String password;

  private final Collection<? extends GrantedAuthority> authorities;

  private UserPrinciple(
      Long id,
      String username,
      String email,
      String password,
      User user,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.user = user;
    this.authorities = authorities;
  }

  static UserPrinciple build(User user) {
    List<GrantedAuthority> authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());

    return new UserPrinciple(
        user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user, authorities);
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public User getUser() {
    return user;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserPrinciple user = (UserPrinciple) o;
    return Objects.equals(id, user.id);
  }
}
