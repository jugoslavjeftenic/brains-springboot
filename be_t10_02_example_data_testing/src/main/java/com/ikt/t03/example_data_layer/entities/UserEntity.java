package com.ikt.t03.example_data_layer.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Table(name = "EMPLOYEE")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {

	// 2.1
	/*
	 * Unaprediti UserEntity tako da ima sledeća polja
	 * • datum rođenja,
	 *   broj telefona,
	 *   JMBG,
	 *   broj lične karte
	 * • odabrati odgovarajuće Hibernate anotacije i njihove parametre za svako od ovih polja
	 */
	@Id
	// https://stackoverflow.com/questions/2595124/how-does-the-jpa-sequencegenerator-annotation-work
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "user_sequence", allocationSize=1)
	private Integer id;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private LocalDate birthDate;
	@Column
	private String phoneNumber;
	@Column
	private String jmbg;
	@Column
	private String regBrLk;
	@Version
	private Integer version;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "address")
	private AddressEntity address;
	
	public UserEntity() {
		super();
	}

	public UserEntity(Integer id, String name, String email, LocalDate birthDate, String phoneNumber, String jmbg,
			String regBrLk, Integer version, AddressEntity address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.jmbg = jmbg;
		this.regBrLk = regBrLk;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getRegBrLk() {
		return regBrLk;
	}

	public void setRegBrLk(String regBrLk) {
		this.regBrLk = regBrLk;
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
