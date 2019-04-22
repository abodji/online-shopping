package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	/**
	 * Gets Country by name
	 * 
	 * @param name The name of the country to fetch
	 * @return An optional of country
	 */
	public Optional<Country> findByName(String name);
}
