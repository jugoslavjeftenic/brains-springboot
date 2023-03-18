package com.ikt.t10_02.example_data_testing.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ikt.t10_02.example_data_testing.entities.CityEntity;
import com.ikt.t10_02.example_data_testing.repositories.CityRepository;
import com.ikt.t10_02.example_data_testing.repositories.CountryRepository;

import rade.RADE;

@RestController
@RequestMapping(path = "/api/v1/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;

	@GetMapping
	public Iterable<CityEntity> getAll() {
		return cityRepository.findAll();
	}

	@GetMapping("/{id}")
	public CityEntity getById(@PathVariable Integer id) {
		try {
			CityEntity city = cityRepository.findById(id).get();
			return city;
		} catch (Exception e) {
			return null;
		}
	}
	
	@PostMapping("/populatetable")
	public Iterable<CityEntity> populateTable() {
		List<CityEntity> cities = new ArrayList<>();
		for (int i = 0; i < RADE.dobaviSveOpstine().size(); i++) {
			if (cityRepository.findByCity(RADE.dobaviSveOpstine().get(i).getNaziv()).size() < 1) {
				CityEntity city = new CityEntity();
				city.setCity(RADE.dobaviSveOpstine().get(i).getNaziv());
				if (RADE.dobaviSveOpstine().get(i).getDrzava() != null) {
					city.setCountry(countryRepository.findById(RADE.dobaviSveOpstine().get(i).getDrzava()).get());
				}
				cities.add(city);
			}
		}
		cityRepository.saveAll(cities);
		return cities;
	}

	@PostMapping
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
	
	@PutMapping("/{id}")
	public CityEntity updateCountry(@PathVariable Integer id,
			@RequestParam(required = false) String city, @RequestParam(required = false) Integer country) {
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

	@DeleteMapping("/{id}")
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
