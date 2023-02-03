package com.ikt.t5.example_serialization.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.ikt.t5.example_serialization.entities.AddressEntity;
import com.ikt.t5.example_serialization.entities.UserEntity;
import com.ikt.t5.example_serialization.security.Views;

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
}
