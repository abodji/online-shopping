package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ala2i.online.store.data.Photo;
import com.ala2i.online.store.data.Product;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
	
	/**
	 * Retrieves photo by its file name
	 * 
	 * @param fileName File name must not be {@literal null}.
	 * @return the photo with the given fileName or {@literal Optional#empty()} if none found
	 */
	public Optional<Photo> findByFileName(String fileName);
	
	/**
	 * Retrieves photo of a given product 
	 * 
	 * @param product must not be {@literal null}.	 
	 * @return the photo with the given product or {@literal Optional#empty()} if none found
	 */
	public Optional<Photo> findByProduct(Product product);
	
	/**
	 * Deletes photo by product id
	 * 
	 * @param productId must not be {@literal null}.
	 */
	@Query("DELETE FROM Photo p WHERE p.product.productId = :productId")
	@Modifying
	@Transactional
	public void deleteByProduct(Long productId);
}
