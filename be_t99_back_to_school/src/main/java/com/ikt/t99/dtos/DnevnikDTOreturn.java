package com.ikt.t99.dtos;

import java.time.LocalDate;

public class DnevnikDTOreturn {

	private Long dnevnik_id;
	private LocalDate datum;
	private Long ucenikId;
	private Long predmetId;
	private Long nastavnikId;
	private Long roditeljId;
	private Integer razred;
	private Integer polugodiste;
	private Integer ocena;

	public DnevnikDTOreturn() {
		super();
	}

	public DnevnikDTOreturn(Long dnevnik_id, LocalDate datum, Long ucenikId, Long predmetId, Long nastavnikId,
			Long roditeljId, Integer razred, Integer polugodiste, Integer ocena) {
		super();
		this.dnevnik_id = dnevnik_id;
		this.datum = datum;
		this.ucenikId = ucenikId;
		this.predmetId = predmetId;
		this.nastavnikId = nastavnikId;
		this.roditeljId = roditeljId;
		this.razred = razred;
		this.polugodiste = polugodiste;
		this.ocena = ocena;
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

	public Long getUcenikId() {
		return ucenikId;
	}

	public void setUcenikId(Long ucenikId) {
		this.ucenikId = ucenikId;
	}

	public Long getPredmetId() {
		return predmetId;
	}

	public void setPredmetId(Long predmetId) {
		this.predmetId = predmetId;
	}

	public Long getNastavnikId() {
		return nastavnikId;
	}

	public void setNastavnikId(Long nastavnikId) {
		this.nastavnikId = nastavnikId;
	}

	public Long getRoditeljId() {
		return roditeljId;
	}

	public void setRoditeljId(Long roditeljId) {
		this.roditeljId = roditeljId;
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
}
