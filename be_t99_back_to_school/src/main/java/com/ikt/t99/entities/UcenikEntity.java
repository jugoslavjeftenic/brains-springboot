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
import javax.persistence.ManyToOne;
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
@Table(name = "ucenik")
public class UcenikEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ucenik_generator")
	@SequenceGenerator(name = "ucenik_generator", sequenceName = "ucenik_sequence", allocationSize = 1)
	private Long ucenik_id;

	@Column(nullable = false, length = 20, unique = true)
	private String korisnickoIme;

	@JsonIgnore
	@Column(nullable = false)
	private String lozinka;

	@JsonManagedReference
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(nullable = false, name = "korisnik")
	private KorisnikEntity korisnik;
	
	@Column(nullable = false, columnDefinition = "INT CHECK (razred BETWEEN 1 AND 8)")
	private Integer razred;
	
	@Column(nullable = false, columnDefinition = "INT CHECK (polugodiste BETWEEN 1 AND 2)")
	private Integer polugodiste;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "roditelj", nullable = false)
	private RoditeljEntity roditelj;
	
	@JsonBackReference
	@OneToMany(mappedBy = "ucenik", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private List<DnevnikEntity> dnevnici = new ArrayList<DnevnikEntity>();
	
	@JsonIgnore
	@Version
	private Integer version;

	public UcenikEntity() {
		super();
	}

	public UcenikEntity(Long ucenik_id, String korisnickoIme, String lozinka, KorisnikEntity korisnik, Integer razred,
			Integer polugodiste, RoditeljEntity roditelj, List<DnevnikEntity> dnevnici, Integer version) {
		super();
		this.ucenik_id = ucenik_id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.korisnik = korisnik;
		this.razred = razred;
		this.polugodiste = polugodiste;
		this.roditelj = roditelj;
		this.dnevnici = dnevnici;
		this.version = version;
	}

	public Long getUcenik_id() {
		return ucenik_id;
	}

	public void setUcenik_id(Long ucenik_id) {
		this.ucenik_id = ucenik_id;
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

	public Integer getRazred() {
		return razred;
	}

	public void setRazred(Integer razred) {
		this.razred = razred;
	}

	public Integer getPolugodiste() {
		return polugodiste;
	}

	public void setPolugodiste(Integer polugodiste) {
		this.polugodiste = polugodiste;
	}

	public RoditeljEntity getRoditelj() {
		return roditelj;
	}

	public void setRoditelj(RoditeljEntity roditelj) {
		this.roditelj = roditelj;
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
