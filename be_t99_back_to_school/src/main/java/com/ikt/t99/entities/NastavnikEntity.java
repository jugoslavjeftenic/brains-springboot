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
@Table(name = "nastavnik")
public class NastavnikEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "nastavnik_generator")
	@SequenceGenerator(name = "nastavnik_generator", sequenceName = "nastavnik_sequence", allocationSize = 1)
	private Long nastavnik_id;

	@Column(nullable = false, length = 20, unique = true)
	private String korisnickoIme;
	
	@JsonIgnore
	@Column(nullable = false)
	private String lozinka;
	
	@JsonManagedReference
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(nullable = false, name = "korisnik")
	private KorisnikEntity korisnik;
	
	@JsonBackReference
	@OneToMany(mappedBy = "nastavnik", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private List<PredajeEntity> predaje = new ArrayList<PredajeEntity>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "nastavnik", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private List<DnevnikEntity> dnevnici = new ArrayList<DnevnikEntity>();
	
	@JsonIgnore
	@Version
	private Integer version;

	public NastavnikEntity() {
		super();
	}

	public NastavnikEntity(Long nastavnik_id, String korisnickoIme, String lozinka, KorisnikEntity korisnik,
			List<PredajeEntity> predaje, List<DnevnikEntity> dnevnici, Integer version) {
		super();
		this.nastavnik_id = nastavnik_id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.korisnik = korisnik;
		this.predaje = predaje;
		this.dnevnici = dnevnici;
		this.version = version;
	}

	public Long getNastavnik_id() {
		return nastavnik_id;
	}

	public void setNastavnik_id(Long nastavnik_id) {
		this.nastavnik_id = nastavnik_id;
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

	public List<PredajeEntity> getPredaje() {
		return predaje;
	}

	public void setPredaje(List<PredajeEntity> predaje) {
		this.predaje = predaje;
	}

	public List<DnevnikEntity> getDnevnici() {
		return dnevnici;
	}

	public void setDnevnici(List<DnevnikEntity> dnevnici) {
		this.dnevnici = dnevnici;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
