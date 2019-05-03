package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ala2i.online.store.data.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	/**
	 * Retrieves country by name
	 * 
	 * @param name must not be {@literal null}.
	 * @return the country with the given name or {@literal Optional#empty()} if none found
	 */
	public Optional<Country> findByName(String name);
	
	/**
	 * Deletes a country with the given countryId that is not used
	 * 
	 * @param countryId Id of the country, must not be {@literal null}.
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Country c WHERE c.countryId = :countryId AND c.countryId NOT IN (SELECT DISTINCT a.country.countryId FROM Address a)")
	public void deleteIfNoAddressAttached(Long countryId);
	
	/**
	 * Deletes a country with the given name that has no product attached to it
	 * 
	 * @param name Name of the country
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Country c WHERE c.name = :name AND c.countryId NOT IN (SELECT DISTINCT a.country.countryId FROM Address a)")
	public void deleteIfNoAddressAttached(String name);
	
	/**
	 * Deletes all countries that have no address attached to them
	 */
	@Modifying
	@Query("DELETE FROM Country c WHERE c.countryId NOT IN (SELECT DISTINCT a.country.countryId FROM Address a)")
	@Transactional
	public void deleteAllIfNoAddressAttached();
}
