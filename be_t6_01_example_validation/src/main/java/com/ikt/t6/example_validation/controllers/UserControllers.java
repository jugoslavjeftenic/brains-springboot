package com.ikt.t6.example_validation.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t6.example_validation.entities.UserEntity;
import com.ikt.t6.example_validation.repositories.UserRepository;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserControllers {

	@Autowired
	private UserRepository userRepository;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationError(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = "";
			String errorMessage = "";
			if (error instanceof FieldError) {
				fieldName = ((FieldError) error).getField();
				errorMessage = error.getDefaultMessage();
			}
			errors.put(fieldName, errorMessage);
		});
		return null;
	}
	
	// Create
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody UserEntity user) {
		userRepository.save(user);
	    return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

//	// Read
//	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
//	public UserEntity read(@PathVariable Long id) {
//	    return userRepository.findById(id).orElse(null);
//	}
//
//	// Update
//	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
//	public UserEntity update(@PathVariable Long id, @RequestBody UserEntity user) {
//	    user.setId(id);
//		// TODO Vratiti odgovarajucu gresku kada id-a nema u bazi.
//	    return userRepository.save(userService.checkAndChangeUserData(user));
//	}
//
//	// Delete (soft)
//	// https://thorben-janssen.com/implement-soft-delete-hibernate/
//	// https://docs.jboss.org/hibernate/orm/6.2/javadocs/org/hibernate/annotations/SQLDelete.html#annotation.type.element.detail
//	// https://stackoverflow.com/questions/22477167/hibernate-softdelete-column-index-out-of-range-exception-while-soft-delete
//	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
//	public UserEntity deleteSoft(@PathVariable Long id) {
//		UserEntity userToSoftDelete = userRepository.findById(id).get();
//		// TODO Vratiti odgovarajucu gresku ako nema usera sa id-om (NoSuchElementException)
//	    userRepository.deleteById(id);
//	    userToSoftDelete.setDeleted(true); // TODO Da li je ovo OK da se stavi ovde ili treba u servis?
//	    return userToSoftDelete;
//	}
}
