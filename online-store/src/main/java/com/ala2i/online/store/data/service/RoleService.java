package com.ala2i.online.store.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Role;
import com.ala2i.online.store.data.repository.RoleRepository;
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
	
	public Role getRoleById(Long roleId) {
		return roleRepository.findById(roleId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Role '%s' not found", roleId))
		);
	}
	
	public List<Role> getRoles(){
		return roleRepository.findAll();
	}

	public Role save(Role role) {
		return roleRepository.save(role);
	}
}
