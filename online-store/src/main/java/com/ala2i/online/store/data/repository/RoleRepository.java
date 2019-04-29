package com.ala2i.online.store.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ala2i.online.store.data.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	/**
	 * Gets Role by name
	 * 
	 * @param name The name of the role to fetch
	 * @return An optional of role
	 */
	public Optional<Role> findByName(String name);
		
	/**
	 * Deletes a role with the given roleId that has no user attached to it
	 * 
	 * @param roleId Id of the role
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Role r WHERE r.roleId = :roleId AND (SELECT COUNT(u) FROM User u WHERE r MEMBER OF u.roles) <= 0")
	public void deleteIfNoUserAttached(Long roleId);
	
	/**
	 * Deletes a role with the given name that has no user attached to it
	 * 
	 * @param name Name of the role
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Role r WHERE r.name = :name AND (SELECT COUNT(u) FROM User u WHERE r MEMBER OF u.roles) <= 0")
	public void deleteIfNoUserAttached(String name);
	
	/**
	 * Deletes all roles that have no product attached to them
	 */
	@Modifying
	@Query("DELETE FROM Role r WHERE (SELECT COUNT(u) FROM User u WHERE r MEMBER OF u.roles) <= 0")
	@Transactional
	public void deleteAllIfNoUserAttached();

}
