package com.ikt.t06.example_validation.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

	@NotNull(message = "First name must be specified.")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} character long.")
	private String firstName;
	@NotNull(message = "Last name must be specified.")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} character long.")
	private String lastName;
	@Email(message = "Email is not valid")
	private String email;
	@NotNull(message = "Username must be specified.")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} character long.")
	private String username;
	@NotNull(message = "Password must be specified.")
	@Size(min = 5, max = 15, message = "Password must be between {min} and {max} character long.")
	private String password;
	@NotNull(message = "Age must be specified.")
	@Min(value = 18, message = "You have to be at least 18 years old.")
	private Integer age;
	@NotNull(message = "Confirmed password must be specified.")
	@Size(min = 5, max = 15, message = "Confirmed password must be between {min} and {max} character long.")
	private String confirmedPassword;

	public UserDTO() {
		super();
	}

	public UserDTO(
			@NotNull(message = "First name must be specified.") @Size(min = 2, max = 30, message = "First name must be between {min} and {max} character long.") String firstName,
			@NotNull(message = "Last name must be specified.") @Size(min = 2, max = 30, message = "Last name must be between {min} and {max} character long.") String lastName,
			@Email(message = "Email is not valid") String email,
			@NotNull(message = "User name must be specified.") @Size(min = 5, max = 15, message = "User name must be between {min} and {max} character long.") String username,
			@NotNull(message = "Password name must be specified.") @Size(min = 5, max = 15, message = "Password name must be between {min} and {max} character long.") String password,
			@NotNull(message = "Age must be specified.") @Min(value = 18, message = "You have to be at least 18 years old.") Integer age,
			@NotNull(message = "Confirmed password name must be specified.") @Size(min = 5, max = 15, message = "Confirmed password name must be between {min} and {max} character long.") String confirmedPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.age = age;
		this.confirmedPassword = confirmedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
}
