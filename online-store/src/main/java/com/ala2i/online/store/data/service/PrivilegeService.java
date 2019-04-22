package com.ala2i.online.store.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Privilege;
import com.ala2i.online.store.data.repository.PrivilegeRepository;
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
	
	public Privilege getPrivilegeById(Long privilegId) {
		return privilegeRepository.findById(privilegId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Privilege '%s' not found", privilegId))
		);
	}
	
	public List<Privilege> getPrivileges(){
		return privilegeRepository.findAll();
	}
	
	public Privilege save(Privilege privilege){
		return privilegeRepository.save(privilege);
	}
}
