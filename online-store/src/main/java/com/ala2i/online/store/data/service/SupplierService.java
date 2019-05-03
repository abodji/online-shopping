package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
	
	public Supplier getSupplierByEmail(String email) {
		return supplierRepository.findByEmail(email).orElseThrow(
			() -> new ElementNotFoundException(String.format("Supplier with the email '%s' not found", email))
		);
	}
	
	public List<Supplier> getAllSuppliers(){
		return supplierRepository.findAll();
	}

	public Supplier getSupplier(long supplierId) {
		return supplierRepository.findById(supplierId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Supplier '%d' not found", supplierId))
		);
	}
	
	public Supplier save(Supplier supplier) {
		if(supplier.getSupplierId() != null)
			return supplierRepository.save(supplier);
		
		Optional<Supplier> optDbSupplier = supplierRepository.findByName(supplier.getName());
		
		if(optDbSupplier.isPresent())
			throw new ElementExistsException(String.format("Supplier '%s' already exists", supplier.getName()));
		
		return supplierRepository.save(supplier);
	}
	
	public void deleteAll() {
		supplierRepository.deleteAllIfNoProductAttached();
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

	public void delete(long supplierId) {
		supplierRepository.deleteIfNoProductAttached(supplierId);
	}
	
	public boolean exists(Long supplierId) {
		return supplierRepository.existsById(supplierId);
	}
	
	public boolean exists(String name) {
		return supplierRepository.findByName(name).isPresent();
	}
	
	public boolean exists(Supplier supplier) {
		return exists(supplier.getName());
	}
}
