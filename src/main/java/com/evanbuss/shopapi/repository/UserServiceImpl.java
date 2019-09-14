package com.evanbuss.shopapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.evanbuss.shopapi.model.User;
import com.evanbuss.shopapi.model.UserResponse;

@Service
public class UserServiceImpl implements UserService{

	BCryptPasswordEncoder encoder;
	UserRepository usersRepository;
	
	@Autowired
    public UserServiceImpl(BCryptPasswordEncoder encoder, UserRepository usersRepository) {
		this.encoder = encoder;
		this.usersRepository = usersRepository;
	}
	
	@Override
	public UserResponse getUserByEmail(String email) {
		User user = usersRepository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new ModelMapper().map(user, UserResponse.class);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = usersRepository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
		
	}
}
