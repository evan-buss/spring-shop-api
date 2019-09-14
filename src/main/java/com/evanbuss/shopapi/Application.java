package com.evanbuss.shopapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.evanbuss.shopapi.model.User;
import com.evanbuss.shopapi.repository.UserRepository;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
		
	@Bean
	CommandLineRunner init(final UserRepository userRepository) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				userRepository.save(new User("evan", "securepass"));
			}
		};
	}
	
}
