package com.ikt.t4.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

	// Create
	@RequestMapping(method = RequestMethod.POST)
	public UserEntity create(@RequestBody UserEntity user) {
	    return userRepository.save(userService.checkAndChangeUserData(user));
	}

	// Read
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public UserEntity read(@PathVariable Long id) {
	    return userRepository.findById(id).orElse(null);
	}

	// Update
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public UserEntity update(@PathVariable Long id, @RequestBody UserEntity user) {
	    user.setId(id);
		// TODO Vratiti odgovarajucu gresku kada id-a nema u bazi.
	    return userRepository.save(userService.checkAndChangeUserData(user));
	}

	// Soft Delete
	// https://thorben-janssen.com/implement-soft-delete-hibernate/
	// https://docs.jboss.org/hibernate/orm/6.2/javadocs/org/hibernate/annotations/SQLDelete.html#annotation.type.element.detail
	// https://stackoverflow.com/questions/22477167/hibernate-softdelete-column-index-out-of-range-exception-while-soft-delete
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public UserEntity delete(@PathVariable Long id) {
		UserEntity userToSoftDelete = userRepository.findById(id).get();
		// TODO Vratiti odgovarajucu gresku ako nema usera sa id-om (NoSuchElementException)
	    userRepository.deleteById(id);
	    userToSoftDelete.setDeleted(true); // TODO Da li je ovo OK da se stavi ovde ili treba u servis?
	    return userToSoftDelete;
	}

	// List all
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> list() {
	    return userRepository.findAll();
	}

	// Generate
	@RequestMapping(method = RequestMethod.POST, path = "/admin/populatetable/{count}")
	public Iterable<UserEntity> populateTable(@PathVariable Integer count) {
		return userRepository.saveAll(userService.generateListOfUsers(count));
	}
}
