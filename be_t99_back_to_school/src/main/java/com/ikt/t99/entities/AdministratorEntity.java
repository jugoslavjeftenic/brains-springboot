package com.ikt.t99.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "administrator")
public class AdministratorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "administrator_generator")
	@SequenceGenerator(name = "administrator_generator", sequenceName = "administrator_sequence", allocationSize = 1)
	private Long administrator_id;

	@Column(nullable = false, length = 20, unique = true)
	private String korisnickoIme;
	
	@JsonIgnore
	@Column(nullable = false)
	private String lozinka;
	
	@JsonManagedReference
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(nullable = false, name = "korisnik")
	private KorisnikEntity korisnik;
	
	@JsonIgnore
	@Version
	private Integer version;

	public AdministratorEntity() {
		super();
	}

	public AdministratorEntity(Long administrator_id, String korisnickoIme, String lozinka, KorisnikEntity korisnik,
			Integer version) {
		super();
		this.administrator_id = administrator_id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.korisnik = korisnik;
		this.version = version;
	}

	public Long getAdministrator_id() {
		return administrator_id;
	}

	public void setAdministrator_id(Long administrator_id) {
		this.administrator_id = administrator_id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public KorisnikEntity getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(KorisnikEntity korisnik) {
		this.korisnik = korisnik;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
