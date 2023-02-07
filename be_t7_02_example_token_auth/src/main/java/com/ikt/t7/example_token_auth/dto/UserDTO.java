package com.ikt.t7.example_token_auth.dto;

public class UserDTO {

	private String user;
	private String token;
	
	public UserDTO() {
		super();
	}

	public UserDTO(String user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
