package rade.entities;

public class OpstinaEntity {

	private Integer sifra;
	private String naziv;
	private String posta;
	private Integer drzava;
	
	public OpstinaEntity() {
		super();
	}

	public OpstinaEntity(Integer sifra, String naziv, String posta, Integer drzava) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.posta = posta;
		this.drzava = drzava;
	}

	public Integer getSifra() {
		return sifra;
	}

	public void setSifra(Integer sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPosta() {
		return posta;
	}

	public void setPosta(String posta) {
		this.posta = posta;
	}

	public Integer getDrzava() {
		return drzava;
	}

	public void setDrzava(Integer drzava) {
		this.drzava = drzava;
	}
}
