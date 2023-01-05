package com.iktprekvalifikacija.data_examples.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.data_examples.entities.CountryEntity;
import com.iktprekvalifikacija.data_examples.repositories.CountryRepository;

import tools.RADE;

@RestController
@RequestMapping(path = "/api/v1/countries")
public class CountryController {
	
	@Autowired
	private CountryRepository countryRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<CountryEntity> getAll() {
		return countryRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public CountryEntity getById(@PathVariable Integer id) {
		try {
			CountryEntity country = countryRepository.findById(id).get();
			return country;
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/populatetable")
	public Iterable<CountryEntity> populateTable() {
		List<CountryEntity> countries = new ArrayList<>();
		for (int i = 0; i < RADE.listajSveZemlje().size(); i++) {
			if (countryRepository.findByCountry(RADE.listajSveZemlje().get(i).getNaziv()).size() < 1) {
				CountryEntity country = new CountryEntity();
				country.setCountry(RADE.listajSveZemlje().get(i).getNaziv());
				countries.add(country);
			}
		}
		countryRepository.saveAll(countries);
		return countries;
	}

	@RequestMapping(method = RequestMethod.POST)
	public CountryEntity addCountry(@RequestParam String country) {
		CountryEntity newCountry = new CountryEntity();
		newCountry.setCountry(country);
		return countryRepository.save(newCountry);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public CountryEntity updateCountry(@PathVariable Integer id, @RequestParam String country) {
		try {
			CountryEntity updatedCountry = countryRepository.findById(id).get();
			if (country != null) {
				updatedCountry.setCountry(country);
			}
			return countryRepository.save(updatedCountry);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public CountryEntity deleteAddress(@PathVariable Integer id) {
		try {
			CountryEntity country = countryRepository.findById(id).get();
			countryRepository.delete(country);
			return country;
		} catch (Exception e) {
			return null;
		}
	}
}
