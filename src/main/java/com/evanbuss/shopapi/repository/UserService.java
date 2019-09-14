package com.evanbuss.shopapi.repository;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.evanbuss.shopapi.model.UserResponse;

public interface UserService extends UserDetailsService {
	public UserResponse getUserByEmail(String email);
}
