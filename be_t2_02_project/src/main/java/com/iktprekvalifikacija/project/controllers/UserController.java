package com.iktprekvalifikacija.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.project.entities.EUserRole;
import com.iktprekvalifikacija.project.entities.UserEntity;

import tools.RADE;

@RestController
@RequestMapping(value = "/project/users")
public class UserController {
	
	static int uid = 0;
	List<UserEntity> users = new ArrayList<>();

	private List<UserEntity> getDB() {
		if(users.size() == 0) {
			uid = 0;
			users.add(generateUser(0));
			users.add(generateUser(1));
			users.add(generateUser(2));
		}
		return users;
	}

	// 1.3
	@RequestMapping(method = RequestMethod.GET)
	public List<UserEntity> getAllUsers() {
		return getDB();
	}
	
	// 1.4
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public UserEntity getClientById(@PathVariable Integer id) {
		for (UserEntity user : getDB()) {
			if(user.getUserID().equals(id))
				return user;
		}
		return null;
	}
	
	// 1.5
	@RequestMapping(method = RequestMethod.POST)
	public UserEntity saveUser(@RequestBody UserEntity newUser) {
		getDB();
		newUser.setUserID(++uid);
		users.add(newUser);
		return newUser;
	}

	// 1.6
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
		for (UserEntity user : getDB()) {
			if (user.getUserID().equals(id)) {
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
				if (updatedUser.getEmail() != null) {
					user.setEmail(updatedUser.getEmail());
				}
				return user;
			}
		}
		return null;
	}

	// 1.7
	/*
	 * Kreirati REST endpoint koji omogućava izmenu atributa user_role
	 * postojećeg korisnika • putanja /project/users/change/{id}/role/{role} •
	 * ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da
	 * vrati null, a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću
	 * atributa user role
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "change/{id}/role/{role}")
	public UserEntity updateUserRole(@PathVariable Integer id, @PathVariable EUserRole role) {
		for (UserEntity user : getDB()) {
			if (user.getUserID().equals(id)) {
				if (role != null) {
					user.setUserRole(role);
				}
				return user;
			}
		}
		return null;
	}
	
	// 1.8
	/*
	 * Kreirati REST endpoint koji omogućava izmenu vrednosti atributa password
	 * postojećeg korisnika • putanja /project/users/changePassword/{id} • kao
	 * RequestParam proslediti vrednosti stare i nove lozinke • ukoliko je prosleđen
	 * ID koji ne pripada nijednom korisniku metoda treba da vrati null, a u
	 * suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa password •
	 * NAPOMENA: da bi vrednost atributa password mogla da bude zamenjena sa novom
	 * vrednošću, neophodno je da se vrednost stare lozinke korisnika poklapa sa
	 * vrednošću stare lozinke prosleđene kao RequestParam
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "changePassword/{id}")
	public UserEntity updateUserPass(@PathVariable Integer id, @RequestParam String oldpass, @RequestParam String newpass) {
		for (UserEntity user : getDB()) {
			if (user.getUserID().equals(id)) {
				if (user.getPassword().equals(oldpass)) {
					user.setPassword(newpass);
				}
				return user;
			}
		}
		return null;
	}
	
	// 1.9
	/*
	 * Kreirati REST endpoint koji omogućava brisanje postojećeg korisnika •
	 * putanja /project/users/{id} • ukoliko je prosleđen ID koji ne pripada
	 * nijednom korisniku metoda treba da vrati null, a u suprotnom vraća podatke o
	 * korisniku koji je obrisan
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public UserEntity deleteUser(@PathVariable Integer id) {
		for (UserEntity user : getDB()) {
			if (user.getUserID().equals(id)) {
				getDB().remove(user);
				return user;
			}
		}
		return null;
	}
	
	// 1.10
	/*
	 * Kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog
	 * username-a • putanja /project/users/by-username/{username} • u slučaju da ne
	 * postoji korisnik sa traženim username-om vratiti null
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/by-username/{username}")
	public UserEntity getClientByUserName(@PathVariable String username) {
		for (UserEntity user : getDB()) {
			if(user.getUserName().equals(username))
				return user;
		}
		return null;
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
			++uid,
			firstName,
			lastName,
			firstName.toLowerCase() + lastName.substring(0, 2).toLowerCase(),
			"pass123",
			firstName.toLowerCase() + "." + lastName.substring(0, 1).toLowerCase() + "@bomba.net",
			userRole);
		return client;
	}
}
