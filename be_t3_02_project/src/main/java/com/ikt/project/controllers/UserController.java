package com.ikt.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.project.entities.EUserRole;
import com.ikt.project.entities.UserEntity;
import com.ikt.project.repositories.UserRepository;

import rade.RADE;
import rade.entities.OsobaEntity;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
	
	// T3 1.3
	/*
	 * Izmeniti kontrolere kreirane na prethodnim časovima, tako da rade sa bazom
	 */
	@Autowired
	private UserRepository userRepository;

	// T2 1.3
	/*
	 * Kreirati REST endpoint koji vraća listu korisnika aplikacije
	 * • putanja /project/users
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAll() {
		return userRepository.findAll();
	}
	
	// T2 1.4
	/*
	 * Kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog ID-a
	 * • putanja /project/users/{id}
	 * • u slučaju da ne postoji korisnik sa traženom vrednošću ID-a vratiti null
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

	// T2 1.5
	/*
	 * Kreirati REST endpoint koji omogućava dodavanje novog korisnika
	 * • putanja /project/users
	 * • u okviru ove metode postavi vrednost atributa user role na ROLE_CUSTOMER
	 * • metoda treba da vrati dodatog korisnika
	 */
	@RequestMapping(method = RequestMethod.POST)
	public UserEntity addUser(@RequestBody UserEntity newUser) {
		newUser.setUserRole(EUserRole.ROLE_CUSTOMER);
		return userRepository.save(newUser);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<UserEntity> populateTable(@PathVariable Integer count) {
		List<UserEntity> users = new ArrayList<>();
		EUserRole[] roles = EUserRole.values();
		for (int i = 0; i < count; i++) {
			OsobaEntity osoba = RADE.generisiOsobu();
			UserEntity user = new UserEntity();
			user.setFirstName(osoba.getIme());
			user.setLastName(osoba.getPrezime());
			user.setUserName(osoba.getUsername());
			user.setPassword("1234");
			user.setEmail(osoba.getEmail());
			user.setUserRole(roles[RADE.mrRobot(0, roles.length)]);
			users.add(user);
		}
		return userRepository.saveAll(users);
	}
	
	// T2 1.6
	/*
	 * Kreirati REST endpoint koji omogućava izmenu postojećeg korisnika
	 * • putanja /project/users/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	 *   a u suprotnom vraća podatke korisnika sa izmenjenim vrednostima
	 * • NAPOMENA: u okviru ove metode ne menjati vrednost atributa user role i password
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
		UserEntity user;
		try {
			user = userRepository.findById(id).get();
			if (updatedUser.getFirstName() != null) {
				user.setFirstName(updatedUser.getFirstName());
			}
			if (updatedUser.getLastName() != null) {
				user.setLastName(updatedUser.getLastName());
			}
			if (updatedUser.getUserName() != null) {
				user.setUserName(updatedUser.getUserName());
			}
			if (updatedUser.getEmail() != null) {
				user.setEmail(updatedUser.getEmail());
			}
			return userRepository.save(user);
		} catch (Exception e) {
			return null;
		}
	}

	// T2 1.7
	/*
	 * Kreirati REST endpoint koji omogućava izmenu atributa user_role postojećeg korisnika
	 * • putanja /project/users/change/{id}/role/{role}
	 * • ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	 *   a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa user role
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "change/{id}/role/{role}")
	public UserEntity updateUserRole(@PathVariable Integer id, @PathVariable EUserRole role) {
		UserEntity user;
		try {
			user = userRepository.findById(id).get();
			user.setUserRole(role);
			return userRepository.save(user);
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 1.8
	/*
	 * Kreirati REST endpoint koji omogućava izmenu vrednosti atributa password postojećeg korisnika
	 * • putanja /project/users/changePassword/{id}
	 * • kao RequestParam proslediti vrednosti stare i nove lozinke
	 * • ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	 *   a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa password
	 * • NAPOMENA: da bi vrednost atributa password mogla da bude zamenjena sa novom vrednošću,
	 *   neophodno je da se vrednost stare lozinke korisnika poklapa sa vrednošću stare lozinke
	 *   prosleđene kao RequestParam
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "changePassword/{id}")
	public UserEntity updateUserPass(@PathVariable Integer id, @RequestParam String oldpass, @RequestParam String newpass) {
		UserEntity user;
		try {
			user = userRepository.findById(id).get();
			if (user.getPassword().equals(oldpass)) {
				user.setPassword(newpass);
			}
			return userRepository.save(user);
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 1.9
	/*
	 * Kreirati REST endpoint koji omogućava brisanje postojećeg korisnika
	 * • putanja /project/users/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	 *   a u suprotnom vraća podatke o korisniku koji je obrisan
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public UserEntity deleteUser(@PathVariable Integer id) {
		UserEntity user;
		try {
			user = userRepository.findById(id).get();
			userRepository.delete(user);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 1.10
	/*
	 * Kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog username-a
	 * • putanja /project/users/by-username/{username}
	 * • u slučaju da ne postoji korisnik sa traženim username-om vratiti null
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-username/{username}")
	public List<UserEntity> getByUserName(@PathVariable String username) {
		return userRepository.findByUserName(username);
	}
}
