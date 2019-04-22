package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Category;
import com.ala2i.online.store.data.repository.CategoryRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name).orElseThrow(
			() -> new ElementNotFoundException(String.format("Category '%s' not found", name))
		);
	}
	
	public List<Category> getActiveCategories(){
		return categoryRepository.findByActive(true);
	}
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}

	public Category getCategory(long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Category '%d' not found", categoryId))
		);
	}
	
	public Category save(Category category) {
		Optional<Category> optDbCategory = categoryRepository.findByName(category.getName());
		
		if(optDbCategory.isPresent())
			throw new ElementExistsException(String.format("Category '%s' already exists", category.getName()));
		
		return categoryRepository.save(category);
	}
}
