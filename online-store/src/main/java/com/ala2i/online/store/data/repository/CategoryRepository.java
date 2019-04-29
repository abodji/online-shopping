package com.ala2i.online.store.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	/**
	 * Deletes a category with the given categoryId that has no product attached to it
	 * 
	 * @param categoryId Id of the category
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Category c WHERE c.categoryId = :categoryId AND c.categoryId NOT IN (SELECT DISTINCT p.category.categoryId FROM Product p)")
	public void deleteIfNoProductAttached(Long categoryId);
	
	/**
	 * Deletes a category with the given name that has no product attached to it
	 * 
	 * @param name Name of the category
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Category c WHERE c.name = :name AND c.categoryId NOT IN (SELECT DISTINCT p.category.categoryId FROM Product p)")
	public void deleteIfNoProductAttached(String name);
	
	/**
	 * Deletes all categories that have no product attached to them
	 */
	@Modifying
	@Query("DELETE FROM Category c WHERE c.categoryId NOT IN(SELECT DISTINCT p.category.categoryId FROM Product p)")
	@Transactional
	public void deleteAllIfNoProductAttached();

}
