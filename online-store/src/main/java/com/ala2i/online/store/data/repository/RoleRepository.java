package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	/**
	 * Gets role by its name
	 * 
	 * @param name The name of the role to fetch
	 * @return An optional of role
	 */
	public Optional<Role> findByName(String name);
}
