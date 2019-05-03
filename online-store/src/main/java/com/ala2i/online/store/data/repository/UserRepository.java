package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Retrieves user by its name
	 * 
	 * @param name The name of the user to retrieve
	 * @return An optional of user
	 */
	public Optional<User> findByUsername(String username);
	
	/**
	 * Retrieve a user by its email address
	 * 
	 * @param email The email address of the user to retrieve
	 * @return An optional of user
	 */
	public Optional<User> findByEmail(String email);

}
