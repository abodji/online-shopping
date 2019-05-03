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

import com.ala2i.online.store.data.Country;
import com.ala2i.online.store.data.service.CountryService;

@Controller
public class CountryController {
	
	@Autowired
	private CountryService countryService;
	
	/**
	 *  List all countries in the administration area
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/manage/countries")
	public String showCountries(Model model) {
		
		model.addAttribute("activePage", true);
		model.addAttribute("country", new Country());
		model.addAttribute("countries", countryService.getAllCountries());
		
		return "/management/extras/country/countries";
	}
	
	/**
	 * Receives a post request, validates data and saves it into the database
	 * @param country
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/manage/countries")
	public String saveCountry(@Valid Country country, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("countries", countryService.getAllCountries());
			
			return "/management/extras/country/countries";
		} 
		
		if(country.getCountryId() == null && countryService.exists(country)) {
			model.addAttribute("countryExist", true);
			model.addAttribute("hasErrors", true);
			model.addAttribute("countries", countryService.getAllCountries());		
			
			return "/management/extras/country/countries";
		}
		
		countryService.save(country);
		return "redirect:/manage/countries";
	} 
	
	/**
	 * Gets a particular country to edit
	 * 
	 * @param countryId The id of the country to edit
	 * @return A country to edit in JSON format
	 */
	@RequestMapping("/manage/countries/{countryId}/edit")
	@ResponseBody
	public Country editCountry(@PathVariable(name = "countryId") long countryId) {
		Country country = null;
		
		try {
			country = countryService.getCountry(countryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return country;
	}
	
	/**
	 * Receives a GET request and deletes a country of the given id
	 * 
	 * @param countryId The ID of the country to delete
	 * @return
	 */
	@RequestMapping("/manage/countries/{countryId}/delete")
	public String deleteCountry(@PathVariable(name = "countryId") long countryId) {
		countryService.delete(countryId);
		
		return "redirect:/manage/countries";
	}
	
	/**
	 * Receives a GET request and deletes countries of the given IDs
	 * @param ids IDs of the countries to delete
	 * @return
	 */
	@RequestMapping("/manage/countries/{ids}/delete/selected")
	public String deleteSelectedCountries(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		countryService.deleteSelected(selectedIds);
		
		return "redirect:/manage/countries";
	}	
	
	/**
	 * Receives a GET request and deletes all the countries
	 * @return
	 */
	@RequestMapping("/manage/countries/delete/all")
	public String deleteAllCountries() {
		
		countryService.deleteAll();
		
		return "redirect:/manage/countries";
	}
}
