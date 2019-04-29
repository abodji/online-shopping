package com.ala2i.online.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ala2i.online.store.data.Category;
import com.ala2i.online.store.data.service.CategoryService;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 *  List all categories in the administration area
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/manage/categories")
	public String showCategories(Model model) {
		
		model.addAttribute("activePage", true);
		model.addAttribute("category", new Category());
		model.addAttribute("categories", categoryService.getAllCategories());
		
		return "/management/product/category/categories";
	}
	
	/**
	 * Receives a post request, validates data and saves it into the database
	 * @param category
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/manage/categories")
	public String saveCategory(@Valid Category category, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("categories", categoryService.getAllCategories());
			
			return "/management/product/category/categories";
		} 
		
		if(category.getCategoryId() == null && categoryService.exists(category)) {
			model.addAttribute("categoryExist", true);
			model.addAttribute("hasErrors", true);
			model.addAttribute("categories", categoryService.getAllCategories());		
			
			return "/management/product/category/categories";
		}
		
		categoryService.save(category);
		return "redirect:/manage/categories";
	}
	
	@RequestMapping("/manage/categories/{categoryId}/activate")
	@ResponseBody
	public String activateCategory(@PathVariable("categoryId") Long categoryId) {
		String message = "";
		try {
			Category category = categoryService.getCategory(categoryId);
			category.setActive(!category.getActive());
			categoryService.save(category);
			message = "success";
		} catch (Exception e) {
			message = e.getMessage();
		}
		return message;
	}
	
	/**
	 * Gets a particular category to edit
	 * 
	 * @param categoryId The id of the category to edit
	 * @return A category to edit in JSON format
	 */
	@RequestMapping("/manage/categories/{categoryId}/edit")
	@ResponseBody
	public Category editCategory(@PathVariable(name = "categoryId") long categoryId) {
		Category category = null;
		
		try {
			category = categoryService.getCategory(categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return category;
	}
	
	/**
	 * Receives a GET request and deletes a category of the given id
	 * 
	 * @param categoryId The ID of the category to delete
	 * @return
	 */
	@RequestMapping("/manage/categories/{categoryId}/delete")
	public String deleteCategory(@PathVariable(name = "categoryId") long categoryId) {
		categoryService.delete(categoryId);
		
		return "redirect:/manage/categories";
	}
	
	/**
	 * Receives a GET request and deletes categories of the given IDs
	 * @param ids IDs of the categories to delete
	 * @return
	 */
	@RequestMapping("/manage/categories/{ids}/delete/selected")
	public String deleteSelectedCategories(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		categoryService.deleteSelected(selectedIds);
		
		return "redirect:/manage/categories";
	}	
	
	/**
	 * Receives a GET request and deletes all the categories
	 * @return
	 */
	@RequestMapping("/manage/categories/delete/all")
	public String deleteAllCategories() {
		
		categoryService.deleteAll();
		
		return "redirect:/manage/categories";
	}
}
