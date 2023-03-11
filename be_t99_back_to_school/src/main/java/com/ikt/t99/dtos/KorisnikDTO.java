package com.ikt.t99.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class KorisnikDTO {

	@NotNull(message = "Potrebno je upisati korisnikov JMBG koji ima 13 karaktera.")
	@Size(min = 13, max = 13, message = "JMBG mora da ima tačno 13 karaktera.")
	private String jmbg;

	@NotNull(message = "Potrebno je upisati ime korisnika koje mora da ima najmanje 2 karaktera.")
	@Size(min = 2, max = 150, message = "Ime korisnika može imati najmanje {min} i naviše {max} karaktera.")
	private String ime;
	
	@NotNull(message = "Potrebno je upisati prezime korisnika koje mora da ima najmanje 2 karaktera.")
	@Size(min = 2, max = 100, message = "Prezime korisnika može imati najmanje {min} i naviše {max} karaktera.")
	private String prezime;

	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(
			@NotNull(message = "Potrebno je upisati korisnikov JMBG koji ima 13 karaktera.") @Size(min = 13, max = 13, message = "JMBG mora da ima tačno 13 karaktera.") String jmbg,
			@NotNull(message = "Potrebno je upisati ime korisnika koje mora da ima najmanje 2 karaktera.") @Size(min = 2, max = 150, message = "Ime korisnika može imati najmanje {min} i naviše {max} karaktera.") String ime,
			@NotNull(message = "Potrebno je upisati prezime korisnika koje mora da ima najmanje 2 karaktera.") @Size(min = 2, max = 150, message = "Prezime korisnika može imati najmanje {min} i naviše {max} karaktera.") String prezime) {
		super();
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
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
}
