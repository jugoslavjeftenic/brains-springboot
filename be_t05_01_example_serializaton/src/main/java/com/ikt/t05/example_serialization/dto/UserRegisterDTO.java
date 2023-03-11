package com.ikt.t05.example_serialization.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.ikt.t05.example_serialization.security.Views;

public class UserRegisterDTO {

	@JsonView(Views.Public.class)
	private String email;
	@JsonView(Views.Public.class)
	private String password;
	@JsonView(Views.Public.class)
	private String confirmedPassword;
	
	public UserRegisterDTO() {
		super();
	}

	public UserRegisterDTO(String email, String password, String confirmedPassword) {
		super();
		this.email = email;
		this.password = password;
		this.confirmedPassword = confirmedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
}
