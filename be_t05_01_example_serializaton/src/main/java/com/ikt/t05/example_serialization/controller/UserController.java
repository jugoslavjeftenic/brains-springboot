package com.ikt.t05.example_serialization.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.ikt.t05.example_serialization.controller.util.RESTError;
import com.ikt.t05.example_serialization.dto.UserRegisterDTO;
import com.ikt.t05.example_serialization.entities.AddressEntity;
import com.ikt.t05.example_serialization.entities.UserEntity;
import com.ikt.t05.example_serialization.security.Views;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

	public List<UserEntity> getDummyDB() {
		List<UserEntity> list = new ArrayList<>();

		AddressEntity address = new	AddressEntity();
		address.setId(1);
		address.setStreet("Glavna Ulica 1");
		address.setCity("Novi Sad");
		address.setCountry("Srbija");
		
		UserEntity user1 = new UserEntity();
		user1.setId(1);
		user1.setDateOfBirth(LocalDateTime.now());
		user1.setEmail("user1@example.com");
		user1.setName("Mirko");
		user1.setPassword("password1234");
		user1.setVersion(0);
		user1.setAddress(address);
		
		UserEntity user2 = new UserEntity();
		user2.setId(2);
		user2.setDateOfBirth(LocalDateTime.now());
		user2.setEmail("user2@example.com");
		user2.setName("Slavko");
		user2.setPassword("password4321");
		user2.setVersion(0);
		user2.setAddress(address);
		
		address.getUsers().add(user1);
		address.getUsers().add(user2);
		list.add(user1);
		list.add(user2);
		return list;
	}
	
	@GetMapping
	@JsonView(Views.Public.class)
	public List<UserEntity> getAll() {
		return getDummyDB();
	}
	
	@GetMapping(path = "/private")
	@JsonView(Views.Private.class)
	public List<UserEntity> getAllPrivate() {
		return getDummyDB();
	}
	
	@GetMapping(path = "/admin")
	@JsonView(Views.Admin.class)
	public List<UserEntity> getAllAdmin() {
		return getDummyDB();
	}
	
	@GetMapping(path = "/admin/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getUserById(@PathVariable Integer id) {
		try {
			if (id < 0) {
				return new ResponseEntity<RESTError>(new RESTError(1, "ID must be greater or equal to 0."), HttpStatus.BAD_REQUEST);
			}
			for (UserEntity user : getDummyDB()) {
				if (user.getId().equals(id)) {
					return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(2, "User with provided ID is not found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	@JsonView(Views.Private.class)
	public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO registerDTO) {
		if (registerDTO.getEmail() == null || registerDTO.getEmail().equals("")) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide an email."), HttpStatus.BAD_REQUEST);
		}
		if (registerDTO.getPassword() == null || registerDTO.getPassword().equals("")) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password."), HttpStatus.BAD_REQUEST);
		}
		if (registerDTO.getPassword().length() < 4) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Please provide a password with at least 4 characters."), HttpStatus.BAD_REQUEST);
		}
		if (registerDTO.getConfirmedPassword() == null || registerDTO.getConfirmedPassword().equals("")) {
			return new ResponseEntity<RESTError>(new RESTError(4, "Please provide a confirmed password."), HttpStatus.BAD_REQUEST);
		}
		
		// TODO Extract into service once we connext with a DB
		if (!registerDTO.getPassword().equals(registerDTO.getConfirmedPassword())) {
			return new ResponseEntity<RESTError>(new RESTError(5, "Password do not match."), HttpStatus.BAD_REQUEST);
		}
		for (UserEntity user : getDummyDB()) {
			if (user.getEmail().equals(registerDTO.getEmail())) {
				return new ResponseEntity<RESTError>(new RESTError(6, "Email already exist in DB."), HttpStatus.BAD_REQUEST);
			}
		}
		UserEntity user = new UserEntity();
		user.setEmail(registerDTO.getEmail());
		user.setPassword(registerDTO.getPassword());
		// TODO save to DB once it is implemented.
		return new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
	}
}
