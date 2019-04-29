package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Privilege;
import com.ala2i.online.store.data.repository.PrivilegeRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class PrivilegeService {
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	public Privilege getPrivilegeByName(String name) {
		return privilegeRepository.findByName(name).orElseThrow(
			() -> new ElementNotFoundException(String.format("Privilege '%s' not found", name))
		);
	}
	
	public List<Privilege> getAllPrivileges(){
		return privilegeRepository.findAll();
	}

	public Privilege getPrivilege(long privilegeId) {
		return privilegeRepository.findById(privilegeId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Privilege '%d' not found", privilegeId))
		);
	}
	
	public Privilege save(Privilege privilege) {
		if(privilege.getPrivilegeId() != null)
			return privilegeRepository.save(privilege);
		
		Optional<Privilege> optDbPrivilege = privilegeRepository.findByName(privilege.getName());
		
		if(optDbPrivilege.isPresent())
			throw new ElementExistsException(String.format("Privilege '%s' already exists", privilege.getName()));
		
		return privilegeRepository.save(privilege);
	}
	
	public void deleteAll() {
		privilegeRepository.deleteAllIfNoProductAttached();
	}
	
	public void deleteSelected(Stream<Long> privilegeIds){
		privilegeIds.forEach(this::delete);
	}
	
	public void deleteSelected(Long[] privilegeIds){
		deleteSelected(Stream.of(privilegeIds));
	}
	
	public void deleteSelected(String[] selectedIds){
		deleteSelected(Stream.of(selectedIds).map(Long::new));
	}

	public void delete(long privilegeId) {
		privilegeRepository.deleteIfNoRoleAttached(privilegeId);
	}
	
	public boolean exists(Long privilegeId) {
		return privilegeRepository.existsById(privilegeId);
	}
	
	public boolean exists(String name) {
		return privilegeRepository.findByName(name).isPresent();
	}
	
	public boolean exists(Privilege privilege) {
		return exists(privilege.getName());
	}
}
