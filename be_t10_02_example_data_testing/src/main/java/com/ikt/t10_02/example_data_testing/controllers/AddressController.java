package com.ikt.t10_02.example_data_testing.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ikt.t10_02.example_data_testing.entities.AddressEntity;
import com.ikt.t10_02.example_data_testing.entities.CityEntity;
import com.ikt.t10_02.example_data_testing.entities.UserEntity;
import com.ikt.t10_02.example_data_testing.repositories.AddressRepository;
import com.ikt.t10_02.example_data_testing.repositories.CityRepository;
import com.ikt.t10_02.example_data_testing.repositories.CountryRepository;
import com.ikt.t10_02.example_data_testing.repositories.UserRepository;

import rade.RADE;
import rade.entities.AdresaEntity;

@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private UserRepository userRepository;
	
	// 3.2
	/*
	 * Za svaki od entiteta napraviti REST kontrolere
	 * • koji podržavaju standardne CRUD operacije
	 */
	
	@GetMapping
	public Iterable<AddressEntity> getAll() {
		return addressRepository.findAll();
	}
	
	@PostMapping
	public AddressEntity addAddress(@RequestParam String street, @RequestParam(required = false) String city) {
		AddressEntity address = new AddressEntity();
		address.setStreet(street);
		try {
			address.setCity(cityRepository.findByCity(city).get(0));
		} catch (Exception e) {
			address.setCity(null);
		}
		return addressRepository.save(address);
	}
	
	// 1.1
	/*
	 * Popuniti bazu podataka sa podacima o deset adresa
	 */
	@PostMapping("/populatetable/{count}")
	public Iterable<AddressEntity> populateTable(@PathVariable Integer count) {
		List<AddressEntity> addresses = new ArrayList<>();
		for (int x = 0; x < count; x++) {
			AdresaEntity RADEsAddress = RADE.generisiAdresu();
			AddressEntity address = new AddressEntity();
			address.setStreet(RADEsAddress.getUlica());
			try {
				address.setCity(cityRepository.findByCity(RADEsAddress.getOpstina().getNaziv()).get(0));
			} catch (Exception e) {
				address.setCity(null);
			}
			addresses.add(address);
		}
		addressRepository.saveAll(addresses);
		return addresses;
	}
	
	// 1.2
	/*
	 * U potpunosti omogućiti rad sa adresama
	 * • vraćanje adrese po ID
	 * • ažuriranje adrese
	 * • brisanje adrese
	 */
	@GetMapping("/{id}")
	public AddressEntity getById(@PathVariable Integer id) {
		AddressEntity retVal;
		try {
			retVal = addressRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			retVal = null;
		}
		return retVal;
	}
	
	@PutMapping("/{id}")
	public AddressEntity updateAddress(@PathVariable Integer id,
			@RequestParam(required = false) String street, @RequestParam(required = false) String city) {
		try {
			AddressEntity address = addressRepository.findById(id).get();
			if (street != null) {
				address.setStreet(street);
			}
			if (city != null) {
				try {
					address.setCity(cityRepository.findByCity(city).get(0));
				} catch (Exception e) {
					address.setCity(null);
				}
			}
			return addressRepository.save(address);
		} catch (Exception e) {
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public AddressEntity deleteAddress(@PathVariable Integer id) {
		try {
			AddressEntity address = addressRepository.findById(id).get();
			addressRepository.delete(address);
			return address;
		} catch (Exception e) {
			return null;
		}
	}
	
	// 1.3
	/*
	 * Omogućiti pronalaženje adrese po gradu
	 * • putanja /by-city
	 */
	@GetMapping("/by-city")
	public List<AddressEntity> getAllByCity(@RequestParam(required = false) String city) {
		try {
			CityEntity cityEntity = cityRepository.findByCity(city).get(0);
			return addressRepository.findByCity(cityEntity);
		} catch (Exception e) {
			return null;
		}
	}
	
	// 1.4
	/*
	 * Omogućiti pronalaženje adrese po državi
	 * • vraćanje adresa sortiranih po rastućoj vrednosti države
	 * • putanja /by-country
	 */
	@GetMapping("/by-country")
	public List<AddressEntity> getAllByCountriesSortAsc(@RequestParam(required = false) List<String> country) {
		try {
			return addressRepository.findByCityIn(cityRepository.findByCountryIn(countryRepository.findByCountryInOrderByCountryAsc(country)));
		} catch (Exception e) {
			return null;
		}
	}
	
	// 2.2
	/*
	 * U AddressController dodati REST endpoint-e za dodavanje i brisanje korisnika u adresama
	 */
	@PutMapping("/{id}/add-users")
	public List<UserEntity> addUsersToAddress(@PathVariable Integer id, @RequestParam List<Integer> userid) {
		try {
			AddressEntity address = addressRepository.findById(id).get();
			List<UserEntity> users = (List<UserEntity>) userRepository.findAllById(userid);
//			for (UserEntity user : users) {
//				user.setAddress(address);
//			}
			// https://www.baeldung.com/foreach-java
			users.forEach(user -> user.setAddress(address));
			addressRepository.save(address);
			return address.getUsers();
		} catch (Exception e) {
			return null;
		}
	}
	
	@PutMapping("/{id}/remove-user/{userid}")
	public UserEntity removeUserFromAddress(@PathVariable Integer id, @PathVariable Integer userid) {
		try {
			UserEntity user = userRepository.findById(userid).get();
			if (user.getAddress().equals(addressRepository.findById(id).get())) {
				user.setAddress(null);
				return userRepository.save(user);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	@PutMapping("/{id}/remove-users")
	public List<UserEntity> removeUsersFromAddress(@PathVariable Integer id, @RequestParam List<Integer> userid) {
		try {
			AddressEntity address = addressRepository.findById(id).get();
			List<UserEntity> users = (List<UserEntity>) userRepository.findAllById(userid);
			users.forEach(user -> user.setAddress(null));
			addressRepository.save(address);
			return users;
		} catch (Exception e) {
			return null;
		}
	}
	
	@PutMapping("/{id}/remove-all-users")
	public List<UserEntity> removeAllUsersFromAddress(@PathVariable Integer id) {
		try {
			AddressEntity address = addressRepository.findById(id).get();
			List<UserEntity> users = address.getUsers();
			users.forEach(user -> user.setAddress(null));
			addressRepository.save(address);
			return users;
		} catch (Exception e) {
			return null;
		}
	}
}
