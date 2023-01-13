package rade.entities;

import java.time.LocalDate;

public class OsobaEntity {

	private String ime;
	private String prezime;
	private Integer pol;
	private LocalDate datumRodjenja;
	private String jmbg;
	private AdresaEntity adresaRodjenja;
	private AdresaEntity adresaStanovanja;
	
	public OsobaEntity() {
		super();
	}

	public OsobaEntity(String ime, String prezime, Integer pol, LocalDate datumRodjenja, String jmbg,
			AdresaEntity adresaRodjenja, AdresaEntity adresaStanovanja) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.jmbg = jmbg;
		this.adresaRodjenja = adresaRodjenja;
		this.adresaStanovanja = adresaStanovanja;
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

	public Integer getPol() {
		return pol;
	}

	public void setPol(Integer pol) {
		this.pol = pol;
	}

	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public AdresaEntity getAdresaRodjenja() {
		return adresaRodjenja;
	}

	public void setAdresaRodjenja(AdresaEntity adresaRodjenja) {
		this.adresaRodjenja = adresaRodjenja;
	}

	public AdresaEntity getAdresaStanovanja() {
		return adresaStanovanja;
	}

	public void setAdresaStanovanja(AdresaEntity adresaStanovanja) {
		this.adresaStanovanja = adresaStanovanja;
	}
}
