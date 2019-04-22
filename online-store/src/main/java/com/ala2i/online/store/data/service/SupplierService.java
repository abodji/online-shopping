package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Supplier;
import com.ala2i.online.store.data.repository.SupplierRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	public Supplier getSupplierByName(String name) {
		return supplierRepository.findByName(name).orElseThrow(
			() -> new ElementNotFoundException(String.format("Supplier '%s' not found", name))
		);
	}
	
	public Supplier getSupplierById(Long supplierId) {
		return supplierRepository.findById(supplierId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Supplier '%s' not found", supplierId))
		);
	}
	
	public List<Supplier> getSuppliers(){
		return supplierRepository.findAll();
	}
	
	public Supplier save(Supplier supplier) {
		Optional<Supplier> optDbSupplier = supplierRepository.findByName(supplier.getName());
		
		if(optDbSupplier.isPresent())
			throw new ElementExistsException(String.format("Supplier '%s' already exists", supplier.getName()));
		
		return supplierRepository.save(supplier);
	}
}
