package com.ala2i.online.store.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ala2i.online.store.data.Product;
import com.ala2i.online.store.data.service.CategoryService;
import com.ala2i.online.store.data.service.ProductService;
import com.ala2i.online.store.data.service.SupplierService;

@Controller
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/manage/products")
	public String showProducts(Model model) {
		logger.info("Entering products");
		
		model.addAttribute("activePage", true);
		model.addAttribute("categories", categoryService.getActiveCategories());
		model.addAttribute("suppliers", supplierService.getAllSuppliers());
		model.addAttribute("product", new Product());
		model.addAttribute("products", productService.getProducts());
		
		return "/management/product/product/products";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/manage/products")
	public String saveProduct(@RequestParam(name = "image") MultipartFile imageFile, @Valid Product product, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("categories", categoryService.getActiveCategories());
			model.addAttribute("suppliers", supplierService.getAllSuppliers());
			model.addAttribute("products", productService.getProducts());
			
			return "/management/product/product/products";
		} 
		
		if(product.getProductId() == null && productService.exists(product)) {
			model.addAttribute("productExist", true);
			model.addAttribute("hasErrors", true);
			model.addAttribute("categories", categoryService.getActiveCategories());
			model.addAttribute("suppliers", supplierService.getAllSuppliers());
			model.addAttribute("products", productService.getProducts());			
			
			return "/management/product/product/products";
		}
		
		try {
			productService.save(product, imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/manage/products";
	}
	
	@RequestMapping("/manage/products/{productId}/activate")
	@ResponseBody
	public String activateProduct(@PathVariable("productId") Long productId) {
		String message = "";
		try {
			Product product = productService.getProductById(productId);
			product.setIsActive(!product.getIsActive());
			productService.save(product);
			message = "success";
		} catch (Exception e) {
			message = e.getMessage();
		}
		return message;
	}
	
	@RequestMapping("/manage/products/{productId}/edit")
	@ResponseBody
	public Product editProduct(@PathVariable(name = "productId") long productId) {
		Product product = null;
		
		try {
			product = productService.getProductById(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	@RequestMapping("/manage/products/{productId}/delete")
	public String deleteProduct(@PathVariable(name = "productId") long productId) {
		productService.delete(productId);
		
		return "redirect:/manage/products";
	}
	
	@RequestMapping("/manage/products/{ids}/delete/selected")
	public String deleteSelectedProducts(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		productService.deleteSelected(selectedIds);
		
		return "redirect:/manage/products";
	}
	
	@RequestMapping("/manage/products/delete/all")
	public String deleteAllProducts() {
		
		productService.deleteAllProducts();
		
		return "redirect:/manage/products";
	}
}
