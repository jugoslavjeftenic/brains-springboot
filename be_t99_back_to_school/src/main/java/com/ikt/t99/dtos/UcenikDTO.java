package com.ikt.t99.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UcenikDTO {

	private String korisnickoIme;
	
	@NotNull(message = "Potrebno je upisati korisničku lozinku koja mora da ima najmanje 6 karaktera.")
	@Size(min = 6, max = 20, message = "Korisnička lozinka može imati najmanje {min} i naviše {max} karaktera.")
	private String lozinka;
	
	@NotNull(message = "Potrebno je potvrditi korisničku lozinku koja mora da ima najmanje 6 karaktera.")
	@Size(min = 6, max = 20, message = "Potvrda korisničke lozinke može imati najmanje {min} i naviše {max} karaktera.")
	private String lozinkaPotvrda;

	@NotNull(message = "Potrebno je upisati ID korisnika.")
	@Min(value = 0, message = "Korisnikov ID mora da bude ceo broj veći od 0.")
	private Long korisnikId;

	@NotNull(message = "Potrebno je upisati razred. Dozvoljene vrednosti su brojevi 1 - 8.")
	@Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 8.")
	@Max(value = 8, message = "Dozvoljene vrednosti su brojevi 1 - 8.")
	private Integer razred;

	@NotNull(message = "Potrebno je upisati polugodište. Dozvoljene vrednosti su brojevi 1 i 2.")
	@Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 i 2.")
	@Max(value = 2, message = "Dozvoljene vrednosti su brojevi 1 i 2.")
	private Integer polugodiste;

	@NotNull(message = "Potrebno je upisati ID roditelja.")
	@Min(value = 0, message = "Roditeljov ID mora da bude ceo broj veći od 0.")
	private Long roditeljId;

	public UcenikDTO() {
		super();
	}

	public UcenikDTO(String korisnickoIme,
			@NotNull(message = "Potrebno je upisati korisničku lozinku koja mora da ima najmanje 6 karaktera.") @Size(min = 6, max = 20, message = "Korisnička lozinka može imati najmanje {min} i naviše {max} karaktera.") String lozinka,
			@NotNull(message = "Potrebno je potvrditi korisničku lozinku koja mora da ima najmanje 6 karaktera.") @Size(min = 6, max = 20, message = "Potvrda korisničke lozinke može imati najmanje {min} i naviše {max} karaktera.") String lozinkaPotvrda,
			@NotNull(message = "Potrebno je upisati ID korisnika.") @Min(value = 0, message = "Korisnikov ID mora da bude ceo broj veći od 0.") Long korisnikId,
			@NotNull(message = "Potrebno je upisati razred. Dozvoljene vrednosti su brojevi 1 - 8.") @Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 - 8.") @Max(value = 8, message = "Dozvoljene vrednosti su brojevi 1 - 8.") Integer razred,
			@NotNull(message = "Potrebno je upisati polugodište. Dozvoljene vrednosti su brojevi 1 i 2.") @Min(value = 1, message = "Dozvoljene vrednosti su brojevi 1 i 2.") @Max(value = 2, message = "Dozvoljene vrednosti su brojevi 1 i 2.") Integer polugodiste,
			@NotNull(message = "Potrebno je upisati ID roditelja.") @Min(value = 0, message = "Roditeljov ID mora da bude ceo broj veći od 0.") Long roditeljId) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.lozinkaPotvrda = lozinkaPotvrda;
		this.korisnikId = korisnikId;
		this.razred = razred;
		this.polugodiste = polugodiste;
		this.roditeljId = roditeljId;
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

	public String getLozinkaPotvrda() {
		return lozinkaPotvrda;
	}

	public void setLozinkaPotvrda(String lozinkaPotvrda) {
		this.lozinkaPotvrda = lozinkaPotvrda;
	}

	public Long getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(Long korisnikId) {
		this.korisnikId = korisnikId;
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

	public Long getRoditeljId() {
		return roditeljId;
	}

	public void setRoditeljId(Long roditeljId) {
		this.roditeljId = roditeljId;
	}
}
