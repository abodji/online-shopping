package com.ala2i.online.store.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ala2i.online.store.data.Country;
import com.ala2i.online.store.data.repository.CountryRepository;
import com.ala2i.online.store.exceptions.ElementExistsException;
import com.ala2i.online.store.exceptions.ElementNotFoundException;

@Service
public class CountryService {
	
	@Autowired
	private CountryRepository countryRepository;
	
	
	public List<Country> getAllCountries(){
		return countryRepository.findAll();
	}

	public Country getCountry(long countryId) {
		return countryRepository.findById(countryId).orElseThrow(
			() -> new ElementNotFoundException(String.format("Country '%d' not found", countryId))
		);
	}
	
	public Country getCountry(String name) {
		return countryRepository.findByName(name).orElseThrow(
			() -> new ElementNotFoundException(String.format("Country '%d' not found", name))
		);
	}
	
	public Country save(Country country) {
		Optional<Country> optDbCountry = countryRepository.findByName(country.getName());
		if(optDbCountry.isPresent())
			throw new ElementExistsException(String.format("Country '%s' already exists", country.getName()));
		
		return countryRepository.save(country);
	}
}
