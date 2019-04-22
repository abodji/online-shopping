package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	/**
	 * Gets supplier by its name
	 * 
	 * @param name The name of the supplier to fetch
	 * @return An optional of supplier
	 */
	public Optional<Supplier> findByName(String name);

}
