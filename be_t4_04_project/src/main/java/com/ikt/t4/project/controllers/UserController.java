package com.ikt.t4.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t4.project.entities.UserEntity;
import com.ikt.t4.project.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/v2/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<UserEntity> populateTable(@PathVariable Integer count) {
		
		return null;
	}
}
