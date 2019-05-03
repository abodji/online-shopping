package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Address;
import com.ala2i.online.store.data.repository.AddressRepository;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
		
	public List<Address> getAllAddresses(){
		return addressRepository.findAll();
	}

	public Address getAddress(long addressId) {
		return addressRepository.findById(addressId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Address '%d' not found", addressId))
		);
	}
	
	public Address save(Address address) {
		return addressRepository.save(address);
	}
	
	public void deleteAll() {
		addressRepository.deleteAllIfNoUserAttached();
	}
	
	public void deleteByUser(Long userId) {
		addressRepository.deleteByUser(userId);
	}
	
	public void deleteSelected(Stream<Long> productIds){
		productIds.forEach(this::delete);
	}
	
	public void deleteSelected(Long[] productIds){
		deleteSelected(Stream.of(productIds));
	}
	
	public void deleteSelected(String[] selectedIds){
		deleteSelected(Stream.of(selectedIds).map(Long::new));
	}

	public void delete(long addressId) {
		addressRepository.deleteIfNoUserAttached(addressId);
	}
	
	public boolean exists(Long addressId) {
		return addressRepository.existsById(addressId);
	}

	public void deleteSelectedByUser(String[] selectedIds) {
		Stream.of(selectedIds).map(Long::new).forEach(this::deleteByUser);		
	}
	
	public void deleteSelectedByUser(Long[] selectedIds) {
		Stream.of(selectedIds).forEach(this::deleteByUser);		
	}
}
