package com.ikt.t99.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DnevnikDTONastavnik {

	@NotNull(message = "Potrebno je upisati ID učenika.")
	@Min(value = 0, message = "ID učenika mora da bude ceo broj veći od 0.")
	private Long ucenikId;
	
	@NotNull(message = "Potrebno je upisati ID predmeta.")
	@Min(value = 0, message = "ID predmeta mora da bude ceo broj veći od 0.")
	private Long predmetId;
	
	@NotNull(message = "Potrebno je upisati ocenu. Dozvoljene vrednosti su brojevi 1 - 5.")
	@Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 5.")
	@Max(value = 5, message = "Dozvoljene vrednosti su brojevi 1 - 5.")
	private Integer ocena;

	public DnevnikDTONastavnik() {
		super();
	}

	public DnevnikDTONastavnik(
			@NotNull(message = "Potrebno je upisati ID učenika.") @Min(value = 0, message = "ID učenika mora da bude ceo broj veći od 0.") Long ucenikId,
			@NotNull(message = "Potrebno je upisati ID predmeta.") @Min(value = 0, message = "ID predmeta mora da bude ceo broj veći od 0.") Long predmetId,
			@NotNull(message = "Potrebno je upisati ocenu. Dozvoljene vrednosti su brojevi 1 - 5.") @Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 5.") @Max(value = 5, message = "Dozvoljene vrednosti su brojevi 1 - 5.") Integer ocena) {
		super();
		this.ucenikId = ucenikId;
		this.predmetId = predmetId;
		this.ocena = ocena;
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

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}
}
