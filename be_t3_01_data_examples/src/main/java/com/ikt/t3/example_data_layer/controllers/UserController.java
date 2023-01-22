package com.ikt.t3.example_data_layer.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t3.example_data_layer.entities.AddressEntity;
import com.ikt.t3.example_data_layer.entities.CityEntity;
import com.ikt.t3.example_data_layer.entities.CountryEntity;
import com.ikt.t3.example_data_layer.entities.UserEntity;
import com.ikt.t3.example_data_layer.repositories.AddressRepository;
import com.ikt.t3.example_data_layer.repositories.CityRepository;
import com.ikt.t3.example_data_layer.repositories.CountryRepository;
import com.ikt.t3.example_data_layer.repositories.UserRepository;

import rade.RADE;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAll() {
		return userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserEntity addUser(@RequestBody UserEntity newUser) {
		UserEntity user = new UserEntity();
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setBirthDate(newUser.getBirthDate());
		user.setPhoneNumber(newUser.getPhoneNumber());
		user.setJmbg(newUser.getJmbg());
		user.setRegBrLk(newUser.getRegBrLk());
		return userRepository.save(user);
	}

	// 1.1
	/*
	 * Popuniti bazu podataka sa podacima o deset osoba
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<UserEntity> populateTable(@PathVariable Integer count) {
		List<UserEntity> users = new ArrayList<>();
		for (int x = 0; x < count; x++) {
			UserEntity user = new UserEntity();
			String name = RADE.generisiIme(0) + " " + RADE.generisiPrezime(); 
			user.setName(name);
			user.setEmail(name.substring(0, name.indexOf(' ')).toLowerCase() + "."
					+ name.substring(name.indexOf(' ') + 1, name.indexOf(' ') + 2).toLowerCase()
					+ "@ikt.rs");
			user.setJmbg(RADE.generisiJMBG());
			user.setBirthDate(LocalDate.parse(
					((Integer.parseInt(user.getJmbg().substring(4, 7)) > 900) ? "1" : "2") +
							user.getJmbg().substring(4, 7) +
							user.getJmbg().substring(2, 4) +
							user.getJmbg().substring(0, 2),
					DateTimeFormatter.BASIC_ISO_DATE));
			user.setPhoneNumber(String.format("%3s", RADE.mrRobot(10, 37)).replace(" ", "0") + "/" +
					String.format("%3s", RADE.mrRobot(0, 999)).replace(" ", "0") + "-" +
					String.format("%3s", RADE.mrRobot(0, 9999)).replace(" ", "0"));
			user.setRegBrLk(String.format("%9s", RADE.mrRobot(1, 999999999)).replace(" ", "0"));
			users.add(user);
		}
		return userRepository.saveAll(users);
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
		} catch (Exception e) {
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
			if (updatedUser.getBirthDate() != null) {
				user.setBirthDate(updatedUser.getBirthDate());
			}
			if (updatedUser.getPhoneNumber() != null) {
				user.setPhoneNumber(updatedUser.getPhoneNumber());
			}
			if (updatedUser.getJmbg() != null) {
				user.setJmbg(updatedUser.getJmbg());
			}
			if (updatedUser.getRegBrLk() != null) {
				user.setRegBrLk(updatedUser.getRegBrLk());
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
	public Iterable<UserEntity> getByEmail(@RequestParam String email) {
		return userRepository.findByEmail(email);
	}
	
	// 1.4
	/*
	 * Omogućiti pronalaženje korisnika po imenu
	 * • vraćanje korisnika sortiranih po rastućoj vrednosti emaila
	 * • putanja /by-name
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-name")
	public Iterable<UserEntity> getByNameSortByEmailAsc(@RequestParam String name) {
		return userRepository.findByNameOrderByEmailAsc(name);
	}
	
	// 2.2
	/*
	 * Omogućiti pronalaženje korisnika po datumu rođenja sortiranih u rastućem redosledu imena
	 * • putanja /by-dob
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-dob")
	public Iterable<UserEntity> getByBirthDateAsc(@RequestParam String dob) {
		return userRepository.findByBirthDateOrderByBirthDateAsc(LocalDate.parse(dob));
	}
	
	// 2.3
	/*
	 * Omogućiti pronalaženje različitih imena korisnika po prvom slovu imena
	 * • putanja /by-name-first-letter
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-name-first-letter")
	public Iterable<UserEntity> getNameByFirstLetter(@RequestParam String first_letter) {
		return userRepository.findByNameStartsWith(first_letter);
	}
	
	// 2.1
	/*
	 * Dodati REST endpoint u UserController koji omogućava uklanjanje adrese iz
	 * entiteta korisnika
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/address-remove")
	public UserEntity removeAddressFromUser(@PathVariable Integer id) {
		try {
			UserEntity user = userRepository.findById(id).get();
			user.setAddress(null);
			return userRepository.save(user);
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/address/{addressid}")
	public UserEntity addAddressToUser(@PathVariable Integer id, @PathVariable Integer addressid) {
		try {
			AddressEntity address = addressRepository.findById(addressid).get();
			UserEntity user = userRepository.findById(id).get();
			user.setAddress(address);
			return userRepository.save(user);
		} catch (Exception e) {
			return null;
		}
	}

	// 2.3
	/*
	 * Dodati REST endpoint u UserController koji omogućava prosleđivanje parametara za
	 * kreiranje korisnika i adrese
	 * • kreira korisnika
	 * • proverava postojanje adrese
	 * • ukoliko adresa postoji u bazi podataka dodaje je korisniku
	 * • ukoliko adresa ne postoji, kreira adresu i dodaje je korisniku
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/add-user-and-address")
	// https://www.baeldung.com/spring-request-param
	public UserEntity addUserAndAddress(@RequestParam String name,
			@RequestParam(required = false) String email, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String jmbg, @RequestParam(required = false) String brlk,
			@RequestParam String street, @RequestParam String city, @RequestParam String country) {
		CountryEntity countryEntity;
		try {
			countryEntity = countryRepository.findByCountry(country).get(0);
		} catch (Exception e) {
			countryEntity = null;
		}
		CityEntity cityEntity;
		try {
			cityEntity = cityRepository.findByCityAndCountry(city, countryEntity).get(0);
		} catch (Exception e) {
			cityEntity = new CityEntity();
			cityEntity.setCity(city);
			cityEntity.setCountry(countryEntity);
			cityEntity.setId(cityRepository.save(cityEntity).getId());
		}
		AddressEntity addressEntity;
		try {
			addressEntity = addressRepository.findByStreetAndCity(street, cityEntity).get(0);
		} catch (Exception e) {
			addressEntity = new AddressEntity();
			addressEntity.setStreet(street);
			addressEntity.setCity(cityEntity);
			addressEntity.setId(addressRepository.save(addressEntity).getId());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);
		if (email != null) {
			userEntity.setEmail(email);
		}
		if (phone != null) {
			userEntity.setPhoneNumber(phone);
		}
		if (jmbg != null) {
			userEntity.setJmbg(jmbg);
		}
		if (brlk != null) {
			userEntity.setRegBrLk(brlk);
		}
		try {
			userEntity.setBirthDate(LocalDate.parse(
					((Integer.parseInt(userEntity.getJmbg().substring(4, 7)) > 900) ? "1" : "2") +
					userEntity.getJmbg().substring(4, 7) +
					userEntity.getJmbg().substring(2, 4) +
					userEntity.getJmbg().substring(0, 2),
					DateTimeFormatter.BASIC_ISO_DATE));
		} catch (Exception e) {
			userEntity.setBirthDate(null);
		}
		userEntity.setAddress(addressEntity);
		return userRepository.save(userEntity);
	}
}
