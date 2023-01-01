package com.iktprekvalifikacija.data_examples.controllers;

import java.time.LocalDate;
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
@RequestMapping(path = "/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public UserEntity saveUser(@RequestBody UserEntity newUser) {
		
		UserEntity user = new UserEntity();
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		userRepository.save(user);
		return user;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAll() {
		return userRepository.findAll();
	}

	// 1.1
	/*
	 * Popuniti bazu podataka sa podacima o deset osoba
	 */
	@RequestMapping(method = RequestMethod.POST, path = "populatetable/{count}")
	public List<UserEntity> populateTable(@PathVariable Integer count) {
		List<UserEntity> users = new ArrayList<>();
		for (int x = 0; x < count; x++) {
			UserEntity user = new UserEntity();
			String name = RADE.generisiIme(0) + " " + RADE.generisiPrezime(); 
			user.setName(name);
			user.setEmail(name.substring(0, name.indexOf(' ')).toLowerCase() + "."
					+ name.substring(name.indexOf(' ') + 1, name.indexOf(' ') + 2).toLowerCase()
					+ "@iktprekvalifikacija.rs");
			userRepository.save(user);
			users.add(user);
		}
		return users;
	}
	
	// 1.2
	/*
	 * U potpunosti omogućiti rad sa korisnicima
	 * • vraćanje korisnika po ID
	 * • ažuriranje korisnika
	 * • brisanje korisnika
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public UserEntity getById(@PathVariable Integer id) {
		UserEntity user;
		try {
			user = userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			user = null;
		}
		return user;
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
		try {
			UserEntity user = userRepository.findById(id).get();
			if (updatedUser.getName() != null) {
				user.setName(updatedUser.getName());
			}
			if (updatedUser.getEmail() != null) {
				user.setEmail(updatedUser.getEmail());
			}
			userRepository.save(user);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public UserEntity deleteUser(@PathVariable Integer id) {
		try {
			UserEntity user = userRepository.findById(id).get();
			userRepository.delete(user);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	// 1.3
	/*
	 * Omogućiti pronalaženje korisnika po email adresi
	 * • putanja /by-email
	 */
	// https://www.baeldung.com/spring-data-derived-queries
	// https://www.baeldung.com/spring-data-jpa-query
	@RequestMapping(method = RequestMethod.GET, path = "/by-email")
	public List<UserEntity> getByEmail(@RequestParam String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}
	
	// 1.4
	/*
	 * Omogućiti pronalaženje korisnika po imenu
	 * • vraćanje korisnika sortiranih po rastućoj vrednosti emaila
	 * • putanja /by-name
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-name")
	public List<UserEntity> getByNameSortByEmail(@RequestParam String name) {
		return userRepository.findByNameIgnoreCaseOrderByEmailAsc(name);
	}
	
	// 2.2
	/*
	 * Omogućiti pronalaženje korisnika po datumu rođenja sortiranih u rastućem redosledu imena
	 * • putanja /by-dob
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-dob")
	public List<UserEntity> getByBirthDateAsc(@RequestParam LocalDate dob) {
		return userRepository.findByBirthDateOrderByBirthDateAsc(dob);
	}
	
	
	
	
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{userId}/address/{addressId}")
	public UserEntity addAddressToUser(@PathVariable Integer userId, @PathVariable Integer addressId) {
		UserEntity user;
		try {
			user = userRepository.findById(userId).get();
		} catch (NoSuchElementException e) {
			user = null;
		}
		AddressEntity address;
		try {
			address = addressRepository.findById(addressId).get();
		} catch (NoSuchElementException e) {
			address = null;
		}
		user.setAddress(address);
		userRepository.save(user);
		return user;
	}
}
