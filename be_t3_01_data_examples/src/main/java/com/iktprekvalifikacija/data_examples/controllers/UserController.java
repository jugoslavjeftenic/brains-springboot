package com.iktprekvalifikacija.data_examples.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(method = RequestMethod.POST, path = "populatetable/{count}")
	public List<UserEntity> populateTable(@PathVariable Integer count) {
		List<UserEntity> retVal = new ArrayList<>();
		for (int x = 0; x < count; x++) {
			UserEntity user = new UserEntity();
			String name = RADE.generisiIme(0) + " " + RADE.generisiPrezime(); 
			user.setName(name);
			user.setEmail(name.substring(0, name.indexOf(' ')).toLowerCase() + "."
					+ name.substring(name.indexOf(' ') + 1, name.indexOf(' ') + 2).toLowerCase()
					+ "@iktprekvalifikacija.rs");
			userRepository.save(user);
			retVal.add(user);
		}
		return retVal;
	}
	
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
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public UserEntity getById(@PathVariable Integer id) {
		UserEntity retVal;
		try {
			retVal = userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			retVal = null;
		}
		return retVal;
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
