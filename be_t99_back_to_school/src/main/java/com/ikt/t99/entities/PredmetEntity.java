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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "predmet")
public class PredmetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "predmet_generator")
	@SequenceGenerator(name = "predmet_generator", sequenceName = "predmet_sequence", allocationSize = 1)
	private Long predmet_id;

	@Column(nullable = false, length = 100)
	private String naziv;
	
	@Column(nullable = false, columnDefinition = "INT CHECK (razred BETWEEN 1 AND 8)")
	private Integer razred;
	
	@Column(nullable = false)
	private Integer fondCasova;
	
	@JsonBackReference
	@OneToMany(mappedBy = "predmet", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private List<PredajeEntity> predaju = new ArrayList<PredajeEntity>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "predmet", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	private List<DnevnikEntity> dnevnici = new ArrayList<DnevnikEntity>();

	@JsonIgnore
	@Version
	private Integer version;

	public PredmetEntity() {
		super();
	}

	public PredmetEntity(Long predmet_id, String naziv, Integer razred, Integer fondCasova, List<PredajeEntity> predaju,
			List<DnevnikEntity> dnevnici, Integer version) {
		super();
		this.predmet_id = predmet_id;
		this.naziv = naziv;
		this.razred = razred;
		this.fondCasova = fondCasova;
		this.predaju = predaju;
		this.dnevnici = dnevnici;
		this.version = version;
	}

	public Long getPredmet_id() {
		return predmet_id;
	}

	public void setPredmet_id(Long predmet_id) {
		this.predmet_id = predmet_id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Integer getRazred() {
		return razred;
	}

	public void setRazred(Integer razred) {
		this.razred = razred;
	}

	public Integer getFondCasova() {
		return fondCasova;
	}

	public void setFondCasova(Integer fondCasova) {
		this.fondCasova = fondCasova;
	}

	public List<PredajeEntity> getPredaju() {
		return predaju;
	}

	public void setPredaju(List<PredajeEntity> predaju) {
		this.predaju = predaju;
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
