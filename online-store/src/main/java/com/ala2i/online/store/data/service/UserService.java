package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.User;
import com.ala2i.online.store.data.repository.UserRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class UserService{
	@Autowired
	UserRepository userRepository;
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
			() -> new ElementNotFoundException(String.format("User '%s' not found", username))
		);
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
			() -> new ElementNotFoundException(String.format("User with email '%s' not found", email))
		);
	}
	
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new ElementNotFoundException(String.format("User with the ID '%s' not found", userId))
		);
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public User save(User user) {
		if(user.getUserId() != null)
			return userRepository.save(user);
		
		Optional<User> optDbUser = userRepository.findByUsername(user.getUsername());
		
		if(optDbUser.isPresent())
			throw new ElementExistsException(String.format("User '%s' already exists", user.getUsername()));

		/* encrypt the password before saving into the database */
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));	
		
		return userRepository.save(user);
	}
	
	public void deleteAll() {
		userRepository.deleteAll();
	}
	
	public void deleteSelected(Stream<Long> productIds){
		productIds.forEach(this::delete);
	}
	
	public void deleteSelected(Long[] productIds){
		deleteSelected(Stream.of(productIds));
	}
	
	public void deleteSelected(String[] selectedIds){
		deleteSelected(Stream.of(selectedIds).map(Long::new));
	}

	public void delete(long userId) {
		userRepository.deleteById(userId);
	}
	
	public boolean exists(Long userId) {
		return userRepository.existsById(userId);
	}
	
	public boolean exists(String username) {
		return userRepository.findByUsername(username).isPresent();
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
	
	public boolean exists(User user) {
		return exists(user.getUsername());
	}
}
