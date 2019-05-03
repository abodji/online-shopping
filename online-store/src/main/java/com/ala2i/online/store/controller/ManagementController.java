package com.ala2i.online.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ala2i.online.store.data.service.CategoryService;
import com.ala2i.online.store.data.service.ProductService;
import com.ala2i.online.store.data.service.SupplierService;

@Controller
public class ManagementController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ProductService productService;
	
	/* Dashboard */
	@RequestMapping(method = RequestMethod.GET, value = {"/manage", "/manage/dashboard"})
	public String showDashboard(Model model) {
		
		model.addAttribute("manageProducts", true);
		model.addAttribute("categories", categoryService.getActiveCategories());
		model.addAttribute("suppliers", supplierService.getAllSuppliers());
		model.addAttribute("products", productService.getProducts());
		
		return "/management/dashboard";
	}
	
	@RequestMapping("/manage/products/test-me")
	public String testMe() {
		return "/management/test";
	}
}
