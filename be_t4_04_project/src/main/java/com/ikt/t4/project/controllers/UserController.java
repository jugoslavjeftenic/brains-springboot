package com.ikt.t4.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t4.project.entities.UserEntity;
import com.ikt.t4.project.repositories.UserRepository;
import com.ikt.t4.project.services.UserDAOService;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDAOService userService;

	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<UserEntity> populateTable(@PathVariable Integer count) {
		return userRepository.saveAll(userService.generateRandomUsers(count));
	}
}
