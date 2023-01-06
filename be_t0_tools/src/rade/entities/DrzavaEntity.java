package rade.entities;

public class DrzavaEntity {

	private Integer sifra;
	private String alfa;
	private String oznaka;
	private String naziv;
	private String kontinent;
	
	public DrzavaEntity() {
		super();
	}

	public DrzavaEntity(Integer sifra, String alfa, String oznaka, String naziv, String kontinent) {
		super();
		this.sifra = sifra;
		this.alfa = alfa;
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.kontinent = kontinent;
	}

	public Integer getSifra() {
		return sifra;
	}

	public void setSifra(Integer sifra) {
		this.sifra = sifra;
	}

	public String getAlfa() {
		return alfa;
	}

	public void setAlfa(String alfa) {
		this.alfa = alfa;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getKontinent() {
		return kontinent;
	}

	public void setKontinent(String kontinent) {
		this.kontinent = kontinent;
	}
}
