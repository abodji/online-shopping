package com.ala2i.online.store.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	/**
	 * Gets Category by name
	 * 
	 * @param name The name of the category to fetch
	 * @return An optional of category
	 */
	public Optional<Category> findByName(String name);
	
	/**
	 * Gets a list of active categories
	 * 
	 * @param active Whether to fetch active categories or not
	 * @return A list of active categories
	 */
	public List<Category> findByActive(boolean active);

}
