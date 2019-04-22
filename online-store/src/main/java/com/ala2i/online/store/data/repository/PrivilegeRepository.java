package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	
	/**
	 * Gets privilege by name
	 * 
	 * @param name The name of the category to fetch
	 * @return An optional of category
	 */
	public Optional<Privilege> findByName(String name);

}
