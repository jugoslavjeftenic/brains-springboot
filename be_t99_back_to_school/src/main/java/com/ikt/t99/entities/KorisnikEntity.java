package com.ikt.t99.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "korisnik")
public class KorisnikEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "korisnik_generator")
	@SequenceGenerator(name = "korisnik_generator", sequenceName = "korisnik_sequence", allocationSize = 1)
	private Long korisnik_id;

	@Column(nullable = false, length = 13, unique = true)
	private String jmbg;
	
	@Column(nullable = false, length = 150)
	private String ime;
	
	@Column(nullable = false, length = 100)
	private String prezime;
	
	@JsonBackReference
	@OneToOne(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private AdministratorEntity administrator;
	
	@JsonBackReference
	@OneToOne(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private NastavnikEntity nastavnik;
	
	@JsonBackReference
	@OneToOne(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private RoditeljEntity roditelj;
	
	@JsonBackReference
	@OneToOne(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private UcenikEntity ucenik;
	
	@JsonIgnore
	@Version
	private Integer version;

	public KorisnikEntity() {
		super();
	}

	public KorisnikEntity(Long korisnik_id, String jmbg, String ime, String prezime, AdministratorEntity administrator,
			NastavnikEntity nastavnik, RoditeljEntity roditelj, UcenikEntity ucenik, Integer version) {
		super();
		this.korisnik_id = korisnik_id;
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.administrator = administrator;
		this.nastavnik = nastavnik;
		this.roditelj = roditelj;
		this.ucenik = ucenik;
		this.version = version;
	}

	public Long getKorisnik_id() {
		return korisnik_id;
	}

	public void setKorisnik_id(Long korisnik_id) {
		this.korisnik_id = korisnik_id;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public AdministratorEntity getAdministrator() {
		return administrator;
	}

	public void setAdministrator(AdministratorEntity administrator) {
		this.administrator = administrator;
	}

	public NastavnikEntity getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(NastavnikEntity nastavnik) {
		this.nastavnik = nastavnik;
	}

	public RoditeljEntity getRoditelj() {
		return roditelj;
	}

	public void setRoditelj(RoditeljEntity roditelj) {
		this.roditelj = roditelj;
	}

	public UcenikEntity getUcenik() {
		return ucenik;
	}

	public void setUcenik(UcenikEntity ucenik) {
		this.ucenik = ucenik;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
