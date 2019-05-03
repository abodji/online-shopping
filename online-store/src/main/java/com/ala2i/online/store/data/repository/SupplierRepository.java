package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ala2i.online.store.data.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	/**
	 * Retrieves supplier by its email
	 * 
	 * @param email The email address of the supplier to retrieve, must not be {@literal null}.
	 * @return The supplier with the given email or {@literal Optional#empty()} if none found
	 */
	public Optional<Supplier> findByEmail(String email);
	
	/**
	 * Retrieves supplier by its name
	 * 
	 * @param name The name of the supplier to retrieve, must not be {@literal null}.
	 * @return The supplier with the given name or {@literal Optional#empty()} if none found
	 */
	public Optional<Supplier> findByName(String name);

	/**
	 * Deletes the supplier with the given supplierId that has no product attached to it
	 * 
	 * @param supplierId The supplier ID, must not be {@literal null}.
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Supplier s WHERE s.supplierId = :supplierId AND s.supplierId NOT IN (SELECT DISTINCT p.supplier.supplierId FROM Product p)")
	public void deleteIfNoProductAttached(Long supplierId);
	
	/**
	 * Deletes the supplier with the given name that has no product attached to it
	 * 
	 * @param name The supplier name, must not be {@literal null}.
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Supplier s WHERE s.name = :name AND s.supplierId NOT IN (SELECT DISTINCT p.supplier.supplierId FROM Product p)")
	public void deleteIfNoProductAttached(String name);
	
	/**
	 * Deletes all suppliers that have no product attached to them
	 */
	@Modifying
	@Query("DELETE FROM Supplier s WHERE s.supplierId NOT IN (SELECT DISTINCT p.supplier.supplierId FROM Product p)")
	@Transactional
	public void deleteAllIfNoProductAttached();
}
