package com.iktprekvalifikacija.data_examples.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.data_examples.entities.CityEntity;
import com.iktprekvalifikacija.data_examples.repositories.CityRepository;

@RestController
@RequestMapping(path = "/api/v1/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<CityEntity> getAll() {
		return cityRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public CityEntity getById(@PathVariable Integer id) {
		try {
			CityEntity city = cityRepository.findById(id).get();
			return city;
		} catch (Exception e) {
			return null;
		}
	}
	
//	@RequestMapping(method = RequestMethod.POST, path = "/populatetable")
//	public Iterable<CityEntity> populateTable() {
//		List<CityEntity> countries = new ArrayList<>();
//		for (int i = 0; i < RADE.listajSveZemlje().size(); i++) {
//			if (cityRepository.findByCountry(RADE.listajSveZemlje().get(i).getNaziv()).size() < 1) {
//				CountryEntity country = new CountryEntity();
//				country.setCountry(RADE.listajSveZemlje().get(i).getNaziv());
//				countries.add(country);
//			}
//		}
//		cityRepository.saveAll(countries);
//		return countries;
//	}
}
