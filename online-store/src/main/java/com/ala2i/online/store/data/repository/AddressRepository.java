package com.ala2i.online.store.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ala2i.online.store.data.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	/**
	 * Deletes address if no user attached to it
	 * 
	 * @param addressId 
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Address a WHERE a.addressId = :addressId AND (SELECT COUNT(u) FROM User u WHERE a MEMBER OF u.billingAddresses) <= 0")
	public void deleteIfNoUserAttached(Long addressId);
	
	/**
	 * Deletes addresses of a given user
	 * 
	 * @param userId 
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Address a WHERE (SELECT COUNT(u) FROM User u WHERE u.userId = : userId AND a MEMBER OF u.billingAddresses) > 0")
	public void deleteByUser(Long userId);
	
	/**
	 * Deletes addresses if no user attached to them
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Address a WHERE (SELECT COUNT(u) FROM User u WHERE a MEMBER OF u.billingAddresses) <= 0")
	public void deleteAllIfNoUserAttached();
}
