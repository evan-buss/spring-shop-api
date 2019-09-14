package com.evanbuss.shopapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
//	private String username;
	private String email;
	private String password;
//	private String passwordSalt;
	
	
	
	public User(String email, String passwordHash) {
		this.email = email;
		this.password = passwordHash;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String passwordHash) {
		this.password = passwordHash;
	}

//	public String getPasswordSalt() {
//		return passwordSalt;
//	}
//
//	public void setPasswordSalt(String passwordSalt) {
//		this.passwordSalt = passwordSalt;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String userName) {
//		this.username = userName;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
