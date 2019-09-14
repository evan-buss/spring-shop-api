package com.evanbuss.shopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evanbuss.shopapi.repository.UserRepository;;

@RestController
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/signup")
	public String signup(@RequestBody String name) {
		return name;
	}
	
	@PostMapping("/signin")
	public String signin() {
		return "signin";
	}
	
}
