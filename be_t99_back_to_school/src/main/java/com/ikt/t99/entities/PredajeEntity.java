package com.ikt.t99.entities;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "predaje")
public class PredajeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "predaje_generator")
	@SequenceGenerator(name = "predaje_generator", sequenceName = "predaje_sequence", allocationSize = 1)
	private Long predaje_id;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "nastavnik", nullable = false)
	private NastavnikEntity nastavnik;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "predmet", nullable = false)
	private PredmetEntity predmet;
	
	@JsonIgnore
	@Version
	private Integer version;

	public PredajeEntity() {
		super();
	}

	public PredajeEntity(Long predaje_id, NastavnikEntity nastavnik, PredmetEntity predmet, Integer version) {
		super();
		this.predaje_id = predaje_id;
		this.nastavnik = nastavnik;
		this.predmet = predmet;
		this.version = version;
	}

	public Long getPredaje_id() {
		return predaje_id;
	}

	public void setPredaje_id(Long predaje_id) {
		this.predaje_id = predaje_id;
	}

	public NastavnikEntity getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(NastavnikEntity nastavnik) {
		this.nastavnik = nastavnik;
	}

	public PredmetEntity getPredmet() {
		return predmet;
	}

	public void setPredmet(PredmetEntity predmet) {
		this.predmet = predmet;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
