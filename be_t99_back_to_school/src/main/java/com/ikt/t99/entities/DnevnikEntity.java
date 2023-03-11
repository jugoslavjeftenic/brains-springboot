package com.ikt.t99.entities;

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
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "dnevnik")
public class DnevnikEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dnevnik_generator")
	@SequenceGenerator(name = "dnevnik_generator", sequenceName = "dnevnik_sequence", allocationSize = 1)
	private Long dnevnik_id;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(nullable = false)
	private LocalDate datum;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "ucenik", nullable = false)
	private UcenikEntity ucenik;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "predmet", nullable = false)
	private PredmetEntity predmet;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "nastavnik", nullable = false)
	private NastavnikEntity nastavnik;
	
	@Column(nullable = false, columnDefinition = "INT CHECK (razred BETWEEN 1 AND 8)")
	private Integer razred;
	
	@Column(nullable = false, columnDefinition = "INT CHECK (polugodiste BETWEEN 1 AND 2)")
	private Integer polugodiste;
	
	@Column(nullable = false, columnDefinition = "INT CHECK (ocena BETWEEN 1 AND 5)")
	private Integer ocena;
	
	@JsonIgnore
	@Version
	private Integer version;

	public DnevnikEntity() {
		super();
	}

	public DnevnikEntity(Long dnevnik_id, LocalDate datum, UcenikEntity ucenik, PredmetEntity predmet,
			NastavnikEntity nastavnik, Integer razred, Integer polugodiste, Integer ocena, Integer version) {
		super();
		this.dnevnik_id = dnevnik_id;
		this.datum = datum;
		this.ucenik = ucenik;
		this.predmet = predmet;
		this.nastavnik = nastavnik;
		this.razred = razred;
		this.polugodiste = polugodiste;
		this.ocena = ocena;
		this.version = version;
	}

	public Long getDnevnik_id() {
		return dnevnik_id;
	}

	public void setDnevnik_id(Long dnevnik_id) {
		this.dnevnik_id = dnevnik_id;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public UcenikEntity getUcenik() {
		return ucenik;
	}

	public void setUcenik(UcenikEntity ucenik) {
		this.ucenik = ucenik;
	}

	public PredmetEntity getPredmet() {
		return predmet;
	}

	public void setPredmet(PredmetEntity predmet) {
		this.predmet = predmet;
	}

	public NastavnikEntity getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(NastavnikEntity nastavnik) {
		this.nastavnik = nastavnik;
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

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
