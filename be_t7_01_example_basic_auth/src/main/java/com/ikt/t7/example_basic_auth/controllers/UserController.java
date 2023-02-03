package com.ikt.t7.example_basic_auth.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
		
		RoleEntity re = new RoleEntity();
		re.setId(1);
		re.setName("admin");

		UserEntity ue1 = new UserEntity();
		ue1.setId(1);
		ue1.setEmail("user@example.com");
		ue1.setName("Vladimir");
		ue1.setLastName("Dimitrieski");
		ue1.setPassword("password1234");
		ue1.setRole(re);

		UserEntity ue2 = new UserEntity();
		ue2.setId(2);
		ue2.setEmail("user2@example.com");
		ue2.setName("Milan");
		ue2.setLastName("Celikovic");
		ue2.setPassword("password4321");
		ue2.setRole(re);
		
		list.add(ue1);
		list.add(ue2);
		return list;
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<List<UserEntity>>(getDummyDB(), HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> listUsers(){
		return new ResponseEntity<List<UserEntity>>(getDummyDB(), HttpStatus.OK);
	}
}
