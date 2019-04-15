package com.ala2i.online.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ala2i.online.store.service.CategoryService;

@Controller
public class IndexController implements ErrorController {

	private static final String PATH = "/error";
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = {"/", "/home", "index"})
	public String index(Model model) {
		try {
			model.addAttribute("homePageActive", true);
			model.addAttribute("categories", categoryService.getActiveCategories());
			model.addAttribute("productsForCarousel", null);
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "/pages/home";
	}
	
	@RequestMapping(value = "/about")
	public String about(Model model) {
		try {
			model.addAttribute("aboutPageActive", true);
			model.addAttribute("categories", categoryService.getActiveCategories());
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "/pages/about";
	}
	
	@RequestMapping(value = "/contact")
	public String contact(Model model) {
		try {
			model.addAttribute("contactPageActive", true);
			model.addAttribute("categories", categoryService.getActiveCategories());
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "/pages/contact";
	}
	
	@RequestMapping(value = "/category/{categoryId}/products")
	public String categoryProducts(Model model, @PathVariable(name = "categoryId") long categoryId) {
		try {
			model.addAttribute("categoryPageActive", true);

			model.addAttribute("category", categoryService.getCategory(categoryId));
			model.addAttribute("categories", categoryService.getActiveCategories());
		} catch(Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "/pages/listProducts";
	}
	
	@RequestMapping(value = "/products")
	public String services(Model model) {
		try {
			model.addAttribute("prodPageActive", true);
			model.addAttribute("categories", categoryService.getActiveCategories());
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "/pages/products";
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
	
	@RequestMapping("/error")
	public String error() {
		return "pages/error";
	}
}
