package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.User;
import com.ala2i.online.store.data.repository.UserRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
			() -> new ElementNotFoundException(String.format("User '%s' not found", username))
		);
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
			() -> new ElementNotFoundException(String.format("User '%s' not found", email))
		);
	}
	
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new ElementNotFoundException(String.format("User '%s' not found", userId))
		);
	}
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}

	public User save(User user) {
		Optional<User> optDbUser = userRepository.findByUsername(user.getUsername());
		
		if(optDbUser.isPresent())
			throw new ElementExistsException(String.format("User '%s' already exists", user));

		/* encrypt the password before saving into the database */
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));	
		
		return userRepository.save(user);
	}
}
