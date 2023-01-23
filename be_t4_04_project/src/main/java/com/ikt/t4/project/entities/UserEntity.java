package com.ikt.t4.project.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "user_sequence", allocationSize=1)
    private Long id;
	@Column(nullable=false, length=64)
    private String firstName;
	@Column(nullable=false, length=64)
    private String lastName;
	@Column(nullable=false, unique = true, length=64)
    private String username;
	@Column(nullable=false, length=64)
    private String password;
	@Column(nullable=false, length=128)
    private String email;
	@Column(nullable=false)
    private EUserRole userRole;
	@Version
	private Integer version;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<OfferEntity> offers = new ArrayList<OfferEntity>();
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<BillEntity> bills = new ArrayList<BillEntity>();
	
	public UserEntity() {
		super();
	}

	public UserEntity(Long id, String firstName, String lastName, String username, String password, String email,
			EUserRole userRole, Integer version, List<OfferEntity> offers, List<BillEntity> bills) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
		this.version = version;
		this.offers = offers;
		this.bills = bills;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EUserRole userRole) {
		this.userRole = userRole;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<OfferEntity> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferEntity> offers) {
		this.offers = offers;
	}

	public List<BillEntity> getBills() {
		return bills;
	}

	public void setBills(List<BillEntity> bills) {
		this.bills = bills;
	}
}
