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

import com.ala2i.online.store.data.Address;
import com.ala2i.online.store.data.AddressType;
import com.ala2i.online.store.data.service.AddressService;
import com.ala2i.online.store.data.service.CountryService;

@Controller
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CountryService countryService;
	
	/**
	 *  List all addresses in the administration area
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/manage/addresses")
	public String showAddresss(Model model) {
		
		model.addAttribute("activePage", true);
		model.addAttribute("address", new Address());
		model.addAttribute("addressTypes", AddressType.values());
		model.addAttribute("countries", countryService.getAllCountries());
		model.addAttribute("addresses", addressService.getAllAddresses());
		
		return "/management/extras/address/addresses";
	}
	
	/**
	 * Receives a post request, validates data and saves it into the database
	 * @param address
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/manage/addresses")
	public String saveAddress(@Valid Address address, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("addresss", addressService.getAllAddresses());
			
			return "/management/extras/address/addresses";
		} 
		
		addressService.save(address);
		return "redirect:/manage/addresss";
	}
	
	/**
	 * Gets a particular address to edit
	 * 
	 * @param addressId The id of the address to edit
	 * @return A address to edit in JSON format
	 */
	@RequestMapping("/manage/addresses/{addressId}/edit")
	@ResponseBody
	public Address editAddress(@PathVariable(name = "addressId") long addressId) {
		Address address = null;
		
		try {
			address = addressService.getAddress(addressId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return address;
	}
	
	/**
	 * Receives a GET request and deletes address of the given id
	 * 
	 * @param addressId The ID of the address to delete
	 * @return
	 */
	@RequestMapping("/manage/addresses/{addressId}/delete")
	public String deleteAddress(@PathVariable(name = "addressId") long addressId) {
		addressService.delete(addressId);
		
		return "redirect:/manage/addresses";
	}
	
	/**
	 * Receives a GET request and deletes addresses of the given IDs
	 * @param ids IDs of the addresses to delete
	 * @return
	 */
	@RequestMapping("/manage/addresses/{ids}/delete/selected")
	public String deleteSelectedAddresses(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		addressService.deleteSelected(selectedIds);
		
		return "redirect:/manage/addresses";
	}	
	
	/**
	 * Receives a GET request and deletes all the addresses
	 * @return
	 */
	@RequestMapping("/manage/addresses/delete/all")
	public String deleteAllAddresss() {
		
		addressService.deleteAll();
		
		return "redirect:/manage/addresses";
	}
}
