package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Address;
import com.ala2i.online.store.data.repository.AddressRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
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
		Optional<Address> optAddress = addressRepository.findAll().stream()
			.filter(ad -> ad.equals(address))
			.findFirst();
		if(optAddress.isPresent())
			throw new ElementExistsException(String.format("Address '%s' already exists", address.getAddressString()));
		
		return addressRepository.save(address);
	}
}
