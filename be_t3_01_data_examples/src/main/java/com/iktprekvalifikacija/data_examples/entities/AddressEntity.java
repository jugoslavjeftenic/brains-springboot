package com.iktprekvalifikacija.data_examples.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddressEntity {

	@JsonIgnore
	@OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<UserEntity> users = new ArrayList<UserEntity>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "city")
	private CityEntity city;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "address_generator")
	@SequenceGenerator(name="address_generator", sequenceName = "address_sequence", allocationSize=1)
	private Integer id;
	@Column(nullable = false)
	private String street;
//	@Column(nullable = false)
//	private String city;
//	@Column(nullable = false)
//	private String country;
	@Version
	private Integer version;
	
	public AddressEntity() {
		super();
	}
	
//	public AddressEntity(Integer id, String street, String city, String country, Integer version,
//			List<UserEntity> users) {
	public AddressEntity(Integer id, String street, Integer version, List<UserEntity> users, CityEntity city) {
		super();
		this.id = id;
		this.street = street;
		this.version = version;
		this.users = users;
		this.city = city;
//		this.country = country;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
//	public String getCountry() {
//		return country;
//	}
//	
//	public void setCountry(String country) {
//		this.country = country;
//	}
	
	public CityEntity getCity() {
		return city;
	}
	
	public void setCity(CityEntity city) {
		this.city = city;
	}

	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
}
