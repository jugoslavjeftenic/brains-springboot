package com.ikt.t99.entities;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "roditelj")
public class RoditeljEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "roditelj_generator")
	@SequenceGenerator(name = "roditelj_generator", sequenceName = "roditelj_sequence", allocationSize = 1)
	private Long roditelj_id;

	@Column(nullable = false, length = 20, unique = true)
	private String korisnickoIme;
	
	@JsonIgnore
	@Column(nullable = false)
	private String lozinka;
	
	@Column(nullable = false, length = 100)
	private String eposta;
	
	@JsonManagedReference
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(nullable = false, name = "korisnik")
	private KorisnikEntity korisnik;
	
	@JsonBackReference
	@OneToMany(mappedBy = "roditelj", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private List<UcenikEntity> deca = new ArrayList<UcenikEntity>();
	
	@JsonIgnore
	@Version
	private Integer version;

	public RoditeljEntity() {
		super();
	}

	public RoditeljEntity(Long roditelj_id, String korisnickoIme, String lozinka, String eposta,
			KorisnikEntity korisnik, List<UcenikEntity> deca, Integer version) {
		super();
		this.roditelj_id = roditelj_id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.eposta = eposta;
		this.korisnik = korisnik;
		this.deca = deca;
		this.version = version;
	}

	public Long getRoditelj_id() {
		return roditelj_id;
	}

	public void setRoditelj_id(Long roditelj_id) {
		this.roditelj_id = roditelj_id;
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

	public String getEposta() {
		return eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public KorisnikEntity getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(KorisnikEntity korisnik) {
		this.korisnik = korisnik;
	}

	public List<UcenikEntity> getDeca() {
		return deca;
	}

	public void setDeca(List<UcenikEntity> deca) {
		this.deca = deca;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
