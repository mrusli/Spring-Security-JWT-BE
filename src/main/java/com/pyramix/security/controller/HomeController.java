package com.pyramix.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pyramix.security.domain.user.User;
import com.pyramix.security.repository.UserRepository;

@RestController
public class HomeController {

	@Autowired
	private UserRepository userRepository;
		
	@GetMapping
	public String home() {
		return "Hello...You are home...!!!";
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		
		return getUserRepository().findAll();
	}

	@GetMapping("/user/{userName}")
	public User getUserByUserName(@PathVariable String userName) {
		return getUserRepository().findUserByUserName(userName);
	}
	
	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
