package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Role;
import com.ala2i.online.store.data.repository.RoleRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name).orElseThrow(
			() -> new ElementNotFoundException(String.format("Role '%s' not found", name))
		);
	}
	
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}

	public Role getRole(long roleId) {
		return roleRepository.findById(roleId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Role '%d' not found", roleId))
		);
	}
	
	public Role save(Role role) {
		if(role.getRoleId() != null)
			return roleRepository.save(role);
		
		Optional<Role> optDbRole = roleRepository.findByName(role.getName());
		
		if(optDbRole.isPresent())
			throw new ElementExistsException(String.format("Role '%s' already exists", role.getName()));
		
		return roleRepository.save(role);
	}
	
	public void deleteAll() {
		roleRepository.deleteAllIfNoUserAttached();
	}
	
	public void deleteSelected(Stream<Long> roleIds){
		roleIds.forEach(this::delete);
	}
	
	public void deleteSelected(Long[] roleIds){
		deleteSelected(Stream.of(roleIds));
	}
	
	public void deleteSelected(String[] selectedIds){
		deleteSelected(Stream.of(selectedIds).map(Long::new));
	}

	public void delete(long roleId) {
		roleRepository.deleteIfNoUserAttached(roleId);
	}
	
	public boolean exists(Long roleId) {
		return roleRepository.existsById(roleId);
	}
	
	public boolean exists(String name) {
		return roleRepository.findByName(name).isPresent();
	}
	
	public boolean exists(Role role) {
		return exists(role.getName());
	}
}
