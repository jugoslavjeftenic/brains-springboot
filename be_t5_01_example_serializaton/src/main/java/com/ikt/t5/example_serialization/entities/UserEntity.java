package com.ikt.t5.example_serialization.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.ikt.t5.example_serialization.security.Views;

public class UserEntity {

	@JsonView(Views.Public.class)
	@JsonProperty("ID")
	private Integer id;
	@JsonView(Views.Public.class)
	private String name;
	@JsonView(Views.Admin.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDateTime dateOfBirth;
	@JsonView(Views.Admin.class)
	private String email;
	@JsonIgnore
	private String password;
	private Integer version;
	@JsonView(Views.Private.class)
	@JsonManagedReference
	private AddressEntity address;

	public UserEntity() {
		super();
	}

	public UserEntity(Integer id, String name, LocalDateTime dateOfBirth, String email, String password, Integer version,
			AddressEntity address) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.version = version;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}
}
