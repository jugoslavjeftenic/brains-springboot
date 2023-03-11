package com.ikt.t99.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PredmetDTO {

	@NotNull(message = "Potrebno je upisati naziv predmeta koji mora da ima najmanje 4 karaktera.")
	@Size(min = 4, max = 100, message = "Korisničko ime može imati najmanje {min} i naviše {max} karaktera.")
	private String naziv;

	@NotNull(message = "Potrebno je upisati razred. Dozvoljene vrednosti su brojevi 1 - 8.")
	@Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 8.")
	@Max(value = 8, message = "Dozvoljene vrednosti su brojevi 1 - 8.")
	private Integer razred;

	@NotNull(message = "Potrebno je upisati fond časova.")
	@Min(value = 0, message = "Fond časova mora da bude ceo broj veći od 0.")
	private Integer fondCasova;

	public PredmetDTO(
			@NotNull(message = "Potrebno je naziv predmeta koji mora da ima najmanje 4 karaktera.") @Size(min = 4, max = 100, message = "Korisničko ime može imati najmanje {min} i naviše {max} karaktera.") String naziv,
			@NotNull(message = "Potrebno je upisati razred. Dozvoljene vrednosti su brojevi 1 - 8.") @Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 8.") @Max(value = 8, message = "Dozvoljene vrednosti su brojevi 1 - 8.") Integer razred,
			@NotNull(message = "Potrebno je upisati fond časova.") @Min(value = 0, message = "Fond časova mora da bude ceo broj veći od 0.") Integer fondCasova) {
		super();
		this.naziv = naziv;
		this.razred = razred;
		this.fondCasova = fondCasova;
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
}
