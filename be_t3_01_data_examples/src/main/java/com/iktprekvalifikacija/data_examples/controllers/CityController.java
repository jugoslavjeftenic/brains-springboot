package com.iktprekvalifikacija.data_examples.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.data_examples.entities.CityEntity;
import com.iktprekvalifikacija.data_examples.repositories.CityRepository;

import rade.RADE;

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
	
	@RequestMapping(method = RequestMethod.POST, path = "/populatetable")
	public Iterable<CityEntity> populateTable() {
		List<CityEntity> cities = new ArrayList<>();
		for (int i = 0; i < RADE.listajSveOpstine().size(); i++) {
			if (cityRepository.findByCity(RADE.listajSveOpstine().get(i).getNaziv()).size() < 1) {
				CityEntity city = new CityEntity();
				city.setCity(RADE.listajSveOpstine().get(i).getNaziv());
				if (RADE.listajSveOpstine().get(i).getDrzava() != null) {
					// TODO treba zavrsiti
					city.setCountry(null);
				}
				else {
					city.setCountry(null);
				}
				cities.add(city);
			}
		}
		cityRepository.saveAll(cities);
		return cities;
	}
}
