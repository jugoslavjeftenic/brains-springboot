package com.ikt.t06.example_validation.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ikt.t06.example_validation.dtos.UserDTO;

@Component
public class UserCustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDTO user = (UserDTO) target;
		if (user.getPassword() != null && !user.getPassword().equals(user.getConfirmedPassword())) {
			errors.reject("400", "Passwords must match");
		}
	}

}
