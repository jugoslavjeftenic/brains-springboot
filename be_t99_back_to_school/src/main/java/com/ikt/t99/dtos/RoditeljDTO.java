package com.ikt.t99.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoditeljDTO {

	private String korisnickoIme;
	
	@NotNull(message = "Potrebno je upisati korisničku lozinku koja mora da ima najmanje 6 karaktera.")
	@Size(min = 6, max = 20, message = "Korisnička lozinka može imati najmanje {min} i naviše {max} karaktera.")
	private String lozinka;
	
	@NotNull(message = "Potrebno je potvrditi korisničku lozinku koja mora da ima najmanje 6 karaktera.")
	@Size(min = 6, max = 20, message = "Potvrda korisničke lozinke može imati najmanje {min} i naviše {max} karaktera.")
	private String lozinkaPotvrda;

	@NotNull(message = "Potrebno je upisati validnu adresu elektronske pošte.")
	@Size(min = 6, max = 100, message = "Adresa elektronske pošte može imati najmanje {min} i naviše {max} karaktera.")
	@Email(regexp = ".+@.+\\..+", message = "Upisana adresa elektronske pošte nije validna.")
	private String eposta;

	@NotNull(message = "Potrebno je upisati ID korisnika.")
	@Min(value = 0, message = "Korisnikov ID mora da bude ceo broj veći od 0.")
	private Long korisnikId;

	public RoditeljDTO() {
		super();
	}

	public RoditeljDTO(String korisnickoIme,
			@NotNull(message = "Potrebno je upisati korisničku lozinku koja mora da ima najmanje 6 karaktera.") @Size(min = 6, max = 20, message = "Korisnička lozinka može imati najmanje {min} i naviše {max} karaktera.") String lozinka,
			@NotNull(message = "Potrebno je potvrditi korisničku lozinku koja mora da ima najmanje 6 karaktera.") @Size(min = 6, max = 20, message = "Potvrda korisničke lozinke može imati najmanje {min} i naviše {max} karaktera.") String lozinkaPotvrda,
			@NotNull(message = "Potrebno je upisati validnu adresu elektronske pošte.") @Size(min = 6, max = 100, message = "Adresa elektronske pošte može imati najmanje {min} i naviše {max} karaktera.") @Email(regexp = ".+@.+\\..+", message = "Upisana adresa elektronske pošte nije validna.") String eposta,
			@NotNull(message = "Potrebno je upisati ID korisnika.") @Min(value = 0, message = "Korisnikov ID mora da bude ceo broj veći od 0.") Long korisnikId) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.lozinkaPotvrda = lozinkaPotvrda;
		this.eposta = eposta;
		this.korisnikId = korisnikId;
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

	public String getEposta() {
		return eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public Long getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(Long korisnikId) {
		this.korisnikId = korisnikId;
	}
}
