package com.iktprekvalifikacija.project.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.project.entities.EUserRole;
import com.iktprekvalifikacija.project.entities.UserEntity;
import com.iktprekvalifikacija.project.repositories.UserRepository;

import rade.RADE;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
	
	// T2 1.2
	/*
	 * U paketu com.iktpreobuka.project.controllers napraviti klasu UserController
	 * sa metodom getDB() koja vraća listu svih korisnika aplikacije

	List<UserEntity> users = new ArrayList<>();

	private List<UserEntity> getDB() {
		if(users.size() == 0) {
			uid = 0;
			users.add(generateUser(0));
			users.add(generateUser(1));
			users.add(generateUser(1));
			users.add(generateUser(2));
			users.add(generateUser(2));
			users.add(generateUser(2));
			users.add(generateUser(2));
		}
		return users;
	}

	private UserEntity generateUser(int userRoleIndex) {
		String firstName = RADE.generisiIme(0);
		String lastName = RADE.generisiPrezime();
		EUserRole userRole;
		switch(userRoleIndex) {
	        case 0: userRole = EUserRole.ROLE_ADMIN;
	        break;
	        case 1: userRole = EUserRole.ROLE_SELLER;
	        break;
	        default: userRole = EUserRole.ROLE_CUSTOMER;
		}
		UserEntity client = new UserEntity(
			++uid, firstName, lastName,
			firstName.toLowerCase() + lastName.substring(0, 2).toLowerCase(), "pass123",
			firstName.toLowerCase() + "." + lastName.substring(0, 1).toLowerCase() + "@bomba.net",
			userRole);
		return client;
	}
	*/
	
	// T3 1.3
	/*
	 * Izmeniti kontrolere kreirane na prethodnim časovima, tako da rade sa bazom
	 */
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAll() {
		return userRepository.findAll();
	}
	
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
		UserEntity user = new UserEntity();
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setUserName(newUser.getUserName());
		user.setPassword(newUser.getPassword());
		user.setEmail(newUser.getEmail());
		user.setUserRole(EUserRole.ROLE_CUSTOMER);
		return userRepository.save(user);
	}

	// TODO zavrsiti
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

	
	// T2 1.6
	/*
	 * Kreirati REST endpoint koji omogućava izmenu postojećeg korisnika
	 * • putanja /project/users/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	 *   a u suprotnom vraća podatke korisnika sa izmenjenim vrednostima
	 * • NAPOMENA: u okviru ove metode ne menjati vrednost atributa user role i password
	 */
	
	
//	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
//	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
//		for (UserEntity user : getDB()) {
//			if (user.getUserID().equals(id)) {
//				if (updatedUser.getFirstName() != null) {
//					user.setFirstName(updatedUser.getFirstName());
//				}
//				if (updatedUser.getLastName() != null) {
//					user.setLastName(updatedUser.getLastName());
//				}
//				if (updatedUser.getUserName() != null) {
//					user.setUserName(updatedUser.getUserName());
//				}
//				if (updatedUser.getEmail() != null) {
//					user.setEmail(updatedUser.getEmail());
//				}
//				return user;
//			}
//		}
//		return null;
//	}

	// 1.7
	/*
	 * Kreirati REST endpoint koji omogućava izmenu atributa user_role postojećeg korisnika
	 * • putanja /project/users/change/{id}/role/{role}
	 * • ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	 *   a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa user role
	 */
//	@RequestMapping(method = RequestMethod.PUT, path = "change/{id}/role/{role}")
//	public UserEntity updateUserRole(@PathVariable Integer id, @PathVariable EUserRole role) {
//		for (UserEntity user : getDB()) {
//			if (user.getUserID().equals(id)) {
//				if (role != null) {
//					user.setUserRole(role);
//				}
//				return user;
//			}
//		}
//		return null;
//	}
	
	// 1.8
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
//	@RequestMapping(method = RequestMethod.PUT, path = "changePassword/{id}")
//	public UserEntity updateUserPass(@PathVariable Integer id, @RequestParam String oldpass, @RequestParam String newpass) {
//		for (UserEntity user : getDB()) {
//			if (user.getUserID().equals(id)) {
//				if (user.getPassword().equals(oldpass)) {
//					user.setPassword(newpass);
//				}
//				return user;
//			}
//		}
//		return null;
//	}
	
	// 1.9
	/*
	 * Kreirati REST endpoint koji omogućava brisanje postojećeg korisnika
	 * • putanja /project/users/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null,
	 *   a u suprotnom vraća podatke o korisniku koji je obrisan
	 */
//	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
//	public UserEntity deleteUser(@PathVariable Integer id) {
//		for (UserEntity user : getDB()) {
//			if (user.getUserID().equals(id)) {
//				getDB().remove(user);
//				return user;
//			}
//		}
//		return null;
//	}
	
	// 1.10
	/*
	 * Kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog username-a
	 * • putanja /project/users/by-username/{username}
	 * • u slučaju da ne postoji korisnik sa traženim username-om vratiti null
	 */
//	@RequestMapping(method = RequestMethod.GET, path = "/by-username/{username}")
//	public UserEntity getClientByUserName(@PathVariable String username) {
//		for (UserEntity user : getDB()) {
//			if(user.getUserName().equals(username))
//				return user;
//		}
//		return null;
//	}
}
