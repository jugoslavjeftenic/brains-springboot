package com.ikt.t7.example_basic_auth.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t7.example_basic_auth.entities.RoleEntity;
import com.ikt.t7.example_basic_auth.entities.UserEntity;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

	public List<UserEntity> getDummyDB() {
		List<UserEntity> list = new ArrayList<>();
		
		RoleEntity role = new RoleEntity();
		role.setId(1);
		role.setName("admin");
		
		UserEntity user1 = new UserEntity();
		user1.setId(1);
		user1.setEmail("user1@example.com");
		user1.setName("Mirko");
		user1.setLastName("Dimitrieski");
		user1.setPassword("1234");
		user1.setRole(role);

		UserEntity user2 = new UserEntity();
		user2.setId(2);
		user2.setEmail("user2@example.com");
		user2.setName("Slavko");
		user2.setLastName("Celikovic");
		user2.setPassword("4321");
		user2.setRole(role);
		
		list.add(user1);
		list.add(user2);
		return list;
	}
	
	@GetMapping
	public ResponseEntity<?> readAll() {
		return new ResponseEntity<List<UserEntity>>(getDummyDB(), HttpStatus.OK);
	}
}
