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

import com.ala2i.online.store.data.Supplier;
import com.ala2i.online.store.data.service.SupplierService;

@Controller
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	/**
	 *  List all suppliers in the administration area
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/manage/suppliers")
	public String showSuppliers(Model model) {
		
		model.addAttribute("activePage", true);
		model.addAttribute("supplier", new Supplier());
		model.addAttribute("suppliers", supplierService.getAllSuppliers());
		
		return "/management/product/supplier/suppliers";
	}
	
	/**
	 * Receives a post request, validates data and saves it into the database
	 * @param supplier
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/manage/suppliers")
	public String saveSupplier(@Valid Supplier supplier, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("suppliers", supplierService.getAllSuppliers());
			
			return "/management/product/supplier/suppliers";
		} 
		
		if(supplier.getSupplierId() == null && supplierService.exists(supplier)) {
			model.addAttribute("supplierExist", true);
			model.addAttribute("hasErrors", true);
			model.addAttribute("suppliers", supplierService.getAllSuppliers());		
			
			return "/management/product/supplier/suppliers";
		}
		
		supplierService.save(supplier);
		return "redirect:/manage/suppliers";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/manage/suppliers/products")
	@ResponseBody
	public String saveSupplierFromProduct(@Valid Supplier supplier, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "failure:hasErrors";
		} 
		
		if(supplier.getSupplierId() == null && supplierService.exists(supplier)) {
			return "failure:alreadyExist";
		}
		
		supplierService.save(supplier);
		
		return "success";
	}
	
	/**
	 * Gets a particular supplier to edit
	 * 
	 * @param supplierId The id of the supplier to edit
	 * @return A supplier to edit in JSON format
	 */
	@RequestMapping("/manage/suppliers/{supplierId}/edit")
	@ResponseBody
	public Supplier editSupplier(@PathVariable(name = "supplierId") long supplierId) {
		Supplier supplier = null;
		
		try {
			supplier = supplierService.getSupplier(supplierId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return supplier;
	}
	
	/**
	 * Receives a GET request and deletes a supplier of the given id
	 * 
	 * @param supplierId The ID of the supplier to delete
	 * @return
	 */
	@RequestMapping("/manage/suppliers/{supplierId}/delete")
	public String deleteSupplier(@PathVariable(name = "supplierId") long supplierId) {
		supplierService.delete(supplierId);
		
		return "redirect:/manage/suppliers";
	}
	
	/**
	 * Receives a GET request and deletes suppliers of the given IDs
	 * @param ids IDs of the suppliers to delete
	 * @return
	 */
	@RequestMapping("/manage/suppliers/{ids}/delete/selected")
	public String deleteSelectedSuppliers(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		supplierService.deleteSelected(selectedIds);
		
		return "redirect:/manage/suppliers";
	}	
	
	/**
	 * Receives a GET request and deletes all the suppliers
	 * @return
	 */
	@RequestMapping("/manage/suppliers/delete/all")
	public String deleteAllSuppliers() {
		
		supplierService.deleteAll();
		
		return "redirect:/manage/suppliers";
	}
}
