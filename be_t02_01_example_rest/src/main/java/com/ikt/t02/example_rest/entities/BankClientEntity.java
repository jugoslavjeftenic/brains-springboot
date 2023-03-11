package com.ikt.t02.example_rest.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BankClientEntity {
	
	private Integer id;
	private String firstName, lastName, email, city;
	LocalDate birthDay;
	Character bonitet;

	public BankClientEntity() {
		super();
	}
	
	public BankClientEntity(Integer id, String firstName, String lastName, String email, String city, LocalDate birthDay) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.city = city;
		this.birthDay = birthDay;
		this.bonitet = (calculateYearsSpanFromNow(birthDay) < 65) ? 'P' : 'N'; 
	}
	
	public long calculateYearsSpanFromNow(LocalDate birthDay) {
		return java.time.temporal.ChronoUnit.YEARS.between(birthDay, LocalDateTime.now());
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public Character getBonitet() {
		return bonitet;
	}

	public void setBonitet(Character bonitet) {
		this.bonitet = bonitet;
	}
}
