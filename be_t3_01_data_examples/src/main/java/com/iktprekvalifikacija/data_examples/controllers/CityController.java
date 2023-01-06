package com.iktprekvalifikacija.data_examples.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.data_examples.entities.CityEntity;
import com.iktprekvalifikacija.data_examples.repositories.CityRepository;
import com.iktprekvalifikacija.data_examples.repositories.CountryRepository;

import rade.RADE;

@RestController
@RequestMapping(path = "/api/v1/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;

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
	
	@RequestMapping(method = RequestMethod.POST, path = "/populatetable")
	public Iterable<CityEntity> populateTable() {
		List<CityEntity> cities = new ArrayList<>();
		for (int i = 0; i < RADE.listajSveOpstine().size(); i++) {
			if (cityRepository.findByCity(RADE.listajSveOpstine().get(i).getNaziv()).size() < 1) {
				CityEntity city = new CityEntity();
				city.setCity(RADE.listajSveOpstine().get(i).getNaziv());
				if (RADE.listajSveOpstine().get(i).getDrzava() != null) {
					city.setCountry(countryRepository.findById(RADE.listajSveOpstine().get(i).getDrzava()).get());
				}
				cities.add(city);
			}
		}
		cityRepository.saveAll(cities);
		return cities;
	}

	@RequestMapping(method = RequestMethod.POST)
	public CityEntity addCity(@RequestParam String city, @RequestParam(required = false) Integer country) {
		CityEntity newCity = new CityEntity();
		newCity.setCity(city);
		try {
			newCity.setCountry(countryRepository.findById(country).get());
		} catch (Exception e) {
			newCity.setCountry(null);
		}
		return cityRepository.save(newCity);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public CityEntity updateCountry(@PathVariable Integer id, @RequestParam String city,
			@RequestParam(required = false) Integer country) {
		try {
			CityEntity updatedCity = cityRepository.findById(id).get();
			updatedCity.setCity(city);
			if (country != null) {
				try {
					updatedCity.setCountry(countryRepository.findById(country).get());
				} catch (Exception e) {
					updatedCity.setCountry(null);
				}
			}
			return cityRepository.save(updatedCity);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public CityEntity deleteCity(@PathVariable Integer id) {
		try {
			CityEntity city = cityRepository.findById(id).get();
			cityRepository.delete(city);
			return city;
		} catch (Exception e) {
			return null;
		}
	}
}
