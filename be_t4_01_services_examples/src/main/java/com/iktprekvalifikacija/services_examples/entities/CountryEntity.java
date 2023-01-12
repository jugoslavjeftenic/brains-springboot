package com.iktprekvalifikacija.services_examples.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CountryEntity {

	@Id
	private Integer id;
	@Column(nullable = false)
	private String country;
	@Version
	private Integer version;
	@JsonIgnore
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<CityEntity> cities = new ArrayList<CityEntity>();
	
	public CountryEntity() {
		super();
	}

	public CountryEntity(Integer id, String country, Integer version, List<CityEntity> cities) {
		super();
		this.id = id;
		this.country = country;
		this.version = version;
		this.cities = cities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<CityEntity> getCities() {
		return cities;
	}

	public void setCities(List<CityEntity> cities) {
		this.cities = cities;
	}
}
