package com.iktprekvalifikacija.services_examples.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.services_examples.entities.CountryEntity;
import com.iktprekvalifikacija.services_examples.repositories.CountryRepository;

import rade.RADE;

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
		for (int i = 0; i < RADE.dobaviSveDrzave().size(); i++) {
			if (countryRepository.findByCountry(RADE.dobaviSveDrzave().get(i).getNaziv()).size() < 1) {
				CountryEntity country = new CountryEntity();
				country.setId(RADE.dobaviSveDrzave().get(i).getSifra());
				country.setCountry(RADE.dobaviSveDrzave().get(i).getNaziv());
				countries.add(country);
			}
		}
		countryRepository.saveAll(countries);
		return countries;
	}

	@RequestMapping(method = RequestMethod.POST)
	public CountryEntity addCountry(@RequestParam Integer id, @RequestParam String country) {
		CountryEntity newCountry = new CountryEntity();
		newCountry.setId(id);
		newCountry.setCountry(country);
		return countryRepository.save(newCountry);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public CountryEntity updateCountry(@PathVariable Integer id, @RequestParam(required = false) String country) {
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
	public CountryEntity deleteCountry(@PathVariable Integer id) {
		try {
			CountryEntity country = countryRepository.findById(id).get();
			countryRepository.delete(country);
			return country;
		} catch (Exception e) {
			return null;
		}
	}
}
