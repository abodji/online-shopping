package com.ala2i.online.store.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.Category;
import com.ala2i.online.store.data.Product;
import com.ala2i.online.store.data.Supplier;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	/**
	 * Gets product by its name
	 * 
	 * @param name The name of the product to fetch
	 * @return An optional of product
	 */
	public Optional<Product> findByName(String name);
	
	/**
	 * Gets product by its code
	 * 
	 * @param code The code of the product to fetch
	 * @return An optional of product
	 */
	public Optional<Product> findByCode(String code);
	
	/**
	 * Gets a list of products of a given category
	 * 
	 * @param Category of the products to fetch
	 * @return A list of products of a given category
	 */
	public List<Product> findByCategory(Category category);
	
	/**
	 * Gets a list of products depending on their active state
	 * 
	 * @param isActive The state of the products to fetch
	 * @return A list of products of a given state
	 */
	public List<Product> findByIsActive(boolean isActive);
	
	/**
	 * Gets a list of products of a given supplier
	 * 
	 * @param Category of the products to fetch
	 * @return A list of products of a given category
	 */
	public List<Product> findBySupplier(Supplier supplier);
	
	/**
	 * Gets a list of active products by category
	 * @param category
	 * @return
	 */
	@Query(value = "SELECT p FROM Product p WHERE p.isActive = true AND p.category = :category")
	public List<Product> findActiveProductsByCategory(@Param("category") Category category);
	
	/**
	 * Gets a list of latest active products
	 * @param category
	 * @return
	 */
	@Query(value = "SELECT p FROM Product p WHERE p.isActive = true ORDER BY p.productId DESC")
	public List<Product> findLatestActiveProducts(Pageable pageable);

}
