package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ala2i.online.store.data.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	
	/**
	 * Gets Privilege by name
	 * 
	 * @param name The name of the category to fetch
	 * @return An optional of category
	 */
	public Optional<Privilege> findByName(String name);
	
	/**
	 * Deletes a privilege with the given privilegeId that has no role attached to it
	 * 
	 * @param privilegeId Id of the privilege
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Privilege p WHERE p.privilegeId = :privilegeId AND (SELECT COUNT(r) FROM Role r WHERE p MEMBER OF r.privileges) <= 0")
	public void deleteIfNoRoleAttached(Long privilegeId);
	
	/**
	 * Deletes a category with the given name that has no product attached to it
	 * 
	 * @param name Name of the category
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Privilege p WHERE p.name = :name AND (SELECT COUNT(r) FROM Role r WHERE p MEMBER OF r.privileges) <= 0")
	public void deleteIfNoProductAttached(String name);
	
	/**
	 * Deletes all categories that have no product attached to them
	 */
	@Modifying
	@Query("DELETE FROM Privilege p WHERE (SELECT COUNT(r) FROM Role r WHERE p MEMBER OF r.privileges) <= 0")
	@Transactional
	public void deleteAllIfNoProductAttached();

}
