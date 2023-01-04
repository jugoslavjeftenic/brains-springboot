package com.iktprekvalifikacija.data_examples.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.data_examples.entities.AddressEntity;
import com.iktprekvalifikacija.data_examples.entities.UserEntity;
import com.iktprekvalifikacija.data_examples.repositories.AddressRepository;
import com.iktprekvalifikacija.data_examples.repositories.UserRepository;

import tools.RADE;

@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public AddressEntity saveAddres(@RequestBody AddressEntity newAddress) {
		
		AddressEntity address = new AddressEntity();
		address.setStreet(newAddress.getStreet());
		address.setCity(newAddress.getCity());
		address.setCountry(newAddress.getCountry());
		return addressRepository.save(address);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<AddressEntity> getAll() {
		return addressRepository.findAll();
	}
	
	// 1.1
	/*
	 * Popuniti bazu podataka sa podacima o deset adresa
	 */
	@RequestMapping(method = RequestMethod.POST, path = "populatetable/{count}")
	public Iterable<AddressEntity> populateTable(@PathVariable Integer count) {
		List<AddressEntity> addresses = new ArrayList<>();
		for (int x = 0; x < count; x++) {
			AddressEntity address = new AddressEntity();
			address.setStreet(RADE.generisiUlicu() + " " + RADE.generisiBrojUlice());
			address.setCity(RADE.generisiGrad());
			address.setCountry("Srbija");
			addresses.add(addressRepository.save(address));
		}
		return addresses;
	}
	
	// 1.2
	/*
	 * U potpunosti omogućiti rad sa adresama
	 * • vraćanje adrese po ID
	 * • ažuriranje adrese
	 * • brisanje adrese
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public AddressEntity getById(@PathVariable Integer id) {
		AddressEntity retVal;
		try {
			retVal = addressRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			retVal = null;
		}
		return retVal;
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public AddressEntity updateAddress(@PathVariable Integer id, @RequestBody AddressEntity updatedAddress) {
		try {
			AddressEntity address = addressRepository.findById(id).get();
			if (updatedAddress.getStreet() != null) {
				address.setStreet(updatedAddress.getStreet());
			}
			if (updatedAddress.getCity() != null) {
				address.setCity(updatedAddress.getCity());
			}
			if (updatedAddress.getCountry() != null) {
				address.setCountry(updatedAddress.getCountry());
			}
			return addressRepository.save(address);
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public AddressEntity deleteAddres(@PathVariable Integer id) {
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
	@RequestMapping(method = RequestMethod.GET, path = "/by-city")
	public List<AddressEntity> getByCity(@RequestParam String city) {
		return addressRepository.findByCity(city);
	}
	
	// 1.4
	/*
	 * Omogućiti pronalaženje adrese po državi
	 * • vraćanje adresa sortiranih po rastućoj vrednosti države
	 * • putanja /by-country
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-country")
	public List<AddressEntity> getByCountrySortAsc(@RequestParam String country) {
		return addressRepository.findByCountryOrderByCountryAsc(country);
	}
	
	// 2.2
	/*
	 * U AddressController dodati REST endpoint-e za dodavanje i brisanje korisnika u adresama
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/add-users")
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
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/remove-user/{userid}")
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
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/remove-users")
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
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/remove-all-users")
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
