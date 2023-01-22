package com.ikt.t2.example_rest.entities;

public class BankClientsEntityNames {

	private String name, lastName;

	public BankClientsEntityNames() {
		super();
	}

	public BankClientsEntityNames(String name, String lastName) {
		super();
		this.name = name;
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
