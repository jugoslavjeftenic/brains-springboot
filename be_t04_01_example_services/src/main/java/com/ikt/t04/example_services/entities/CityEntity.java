package com.ikt.t04.example_services.entities;

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
public class CityEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "city_generator")
	@SequenceGenerator(name="city_generator", sequenceName = "city_sequence", allocationSize=1)
	private Integer id;
	@Column(nullable = false)
	private String city;
	@Version
	private Integer version;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "country")
	private CountryEntity country;
	@JsonIgnore
	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<AddressEntity> addresses = new ArrayList<AddressEntity>();

	public CityEntity() {
		super();
	}

	public CityEntity(Integer id, String city, Integer version, CountryEntity country,
			List<AddressEntity> addresses) {
		super();
		this.id = id;
		this.city = city;
		this.version = version;
		this.country = country;
		this.addresses = addresses;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public List<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}
}
