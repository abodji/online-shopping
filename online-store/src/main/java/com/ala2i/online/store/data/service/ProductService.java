package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Category;
import com.ala2i.online.store.data.Product;
import com.ala2i.online.store.data.repository.ProductRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product getProductByName(String name) {
		return productRepository.findByName(name).orElseThrow(
			() -> new ElementNotFoundException(String.format("Product '%s' not found", name))
		);
	}
	
	public Product getProductByCode(String code) {
		return productRepository.findByCode(code).orElseThrow(
			() -> new ElementNotFoundException(String.format("Product '%s' not found", code))
		);
	}
	
	public Product getProductById(Long productId) {
		return productRepository.findById(productId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Product '%s' not found", productId))
		);
	}
	
	public List<Product> getActiveProducts() {
		return productRepository.findByIsActive(true);
	}
	
	public List<Product> getActiveProductsByCategory(Category category) {
		return productRepository.findActiveProductsByCategory(category);
	}
	
	public List<Product> getLatestActiveProducts(int count) {
		return productRepository.findLatestActiveProducts(PageRequest.of(0, count));
	}
	
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	public Product save(Product product){
		if(product.getProductId() != null)
			return productRepository.save(product);
		
		Optional<Product> optDbProduct = productRepository.findByCode(product.getCode());
		
		if(optDbProduct.isPresent())
			throw new ElementExistsException(String.format("Product '%s' already exists", product.getName()));
		
		return productRepository.save(product);
	}
	
	public void delete(Long productId){
		productRepository.deleteById(productId);
	}
	
	public void deleteSelected(Long[] productIds){
		Stream.of(productIds).forEach(productRepository::deleteById);
	}
	
	public void deleteSelected(String[] productIds){
		Stream.of(productIds).map(Long::new).forEach(productRepository::deleteById);
	}
	
	public void deleteAllProducts() {
		productRepository.deleteAll();
	}
	
	public boolean exists(Long productId) {
		return productRepository.existsById(productId);
	}
	
	public boolean exists(Product product) {
		return productRepository.findByCode(product.getCode()).isPresent();
	}
}
