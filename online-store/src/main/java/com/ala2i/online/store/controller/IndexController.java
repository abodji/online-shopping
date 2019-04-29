package com.ala2i.online.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ala2i.online.store.data.Category;
import com.ala2i.online.store.data.Product;
import com.ala2i.online.store.data.service.CategoryService;
import com.ala2i.online.store.data.service.ProductService;

@Controller
public class IndexController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/login")
	public String login(Model model, String error, String logout) {
		if(error != null) {
			model.addAttribute("error", "Username or password invalid.");
		}
		
		if(logout != null) {
			model.addAttribute("message", "You have been logged out successfully!");
		}

		return "/pages/login";
	}
	
	@RequestMapping(value = "/403") 
 	public String accessDenied() { 
 		return"/pages/403"; 
	}
	
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
		//try {
			model.addAttribute("categoryPageActive", true);
			
			Category category = categoryService.getCategory(categoryId);

			model.addAttribute("category", category);
			model.addAttribute("products", productService.getActiveProductsByCategory(category));
			model.addAttribute("categories", categoryService.getActiveCategories());
		//} catch(Exception e) {
			//model.addAttribute("exception", e);
		//}
		
		return "/pages/listProducts";
	}
	
	@RequestMapping(value = "/product/{productId}/show")
	public String showSingleProduct(Model model, @PathVariable(name = "productId") long productId) {
		try {		
			Product product = productService.getProductById(productId);	
			product.updateViews();
			productService.save(product);
			
			Category category = product.getCategory();

			model.addAttribute("singleProdPageActive", true);
			model.addAttribute("category", category);
			model.addAttribute("product", product);
			model.addAttribute("categories", categoryService.getActiveCategories());
		} catch(Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "/pages/product";
	}

	@RequestMapping(value = "/cart/add/{productId}/product")
	public String addToCart(Model model, @PathVariable(name = "productId") long productId) {
		
		
		return "";
	}
	
	@RequestMapping(value = "/products")
	public String products(Model model) {
		try {
			model.addAttribute("prodPageActive", true);
			model.addAttribute("categories", categoryService.getActiveCategories());
			model.addAttribute("products", productService.getActiveProducts());
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "/pages/products";
	}
}
