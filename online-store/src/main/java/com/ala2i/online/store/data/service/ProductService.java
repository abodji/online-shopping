package com.ala2i.online.store.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Category;
import com.ala2i.online.store.data.Product;
import com.ala2i.online.store.data.repository.ProductRepository;
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
		return productRepository.save(product);
	}
}
