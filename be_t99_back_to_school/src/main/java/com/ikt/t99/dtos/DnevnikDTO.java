package com.ikt.t99.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class DnevnikDTO {

	@NotNull(message = "Potrebno je upisati datum dobijanja ocene.")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate datum;

	@NotNull(message = "Potrebno je upisati ID učenika.")
	@Min(value = 0, message = "ID učenika mora da bude ceo broj veći od 0.")
	private Long ucenikId;
	
	@NotNull(message = "Potrebno je upisati ID predmeta.")
	@Min(value = 0, message = "ID predmeta mora da bude ceo broj veći od 0.")
	private Long predmetId;
	
	@NotNull(message = "Potrebno je upisati ID nastavnika.")
	@Min(value = 0, message = "ID nastavnika mora da bude ceo broj veći od 0.")
	private Long nastavnikId;

	@NotNull(message = "Potrebno je upisati polugodište. Dozvoljene vrednosti su brojevi 1 i 2.")
	@Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 i 2.")
	@Max(value = 2, message = "Dozvoljene vrednosti su brojevi 1 i 2.")
	private Integer polugodiste;
	
	@NotNull(message = "Potrebno je upisati ocenu. Dozvoljene vrednosti su brojevi 1 - 5.")
	@Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 5.")
	@Max(value = 5, message = "Dozvoljene vrednosti su brojevi 1 - 5.")
	private Integer ocena;

	public DnevnikDTO() {
		super();
	}

	public DnevnikDTO(@NotNull(message = "Potrebno je upisati datum dobijanja ocene.") LocalDate datum,
			@NotNull(message = "Potrebno je upisati ID učenika.") @Min(value = 0, message = "ID učenika mora da bude ceo broj veći od 0.") Long ucenikId,
			@NotNull(message = "Potrebno je upisati ID predmeta.") @Min(value = 0, message = "ID predmeta mora da bude ceo broj veći od 0.") Long predmetId,
			@NotNull(message = "Potrebno je upisati ID nastavnika.") @Min(value = 0, message = "ID nastavnika mora da bude ceo broj veći od 0.") Long nastavnikId,
			@NotNull(message = "Potrebno je upisati polugodište. Dozvoljene vrednosti su brojevi 1 i 2.") @Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 i 2.") @Max(value = 2, message = "Dozvoljene vrednosti su brojevi 1 i 2.") Integer polugodiste,
			@NotNull(message = "Potrebno je upisati ocenu. Dozvoljene vrednosti su brojevi 1 - 5.") @Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 5.") @Max(value = 5, message = "Dozvoljene vrednosti su brojevi 1 - 5.") Integer ocena) {
		super();
		this.datum = datum;
		this.ucenikId = ucenikId;
		this.predmetId = predmetId;
		this.nastavnikId = nastavnikId;
		this.polugodiste = polugodiste;
		this.ocena = ocena;
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
