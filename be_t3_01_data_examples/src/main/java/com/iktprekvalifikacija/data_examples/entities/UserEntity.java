package com.iktprekvalifikacija.data_examples.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserEntity {

	@Id
	@GeneratedValue
	private Integer ID;
	@Column
	private String name;
	@Column
	private String email;

	public UserEntity(Integer iD, String name, String email) {
		super();
		ID = iD;
		this.name = name;
		this.email = email;
	}

	public UserEntity() {
		super();
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
