package rade.entities;

public class AdresaEntity {

	private String ulica;
	private String broj;
	private OpstinaEntity opstina;
	private DrzavaEntity drzava;
	
	public AdresaEntity() {
		super();
	}

	public AdresaEntity(String ulica, String broj, OpstinaEntity opstina, DrzavaEntity drzava) {
		super();
		this.ulica = ulica;
		this.broj = broj;
		this.opstina = opstina;
		this.drzava = drzava;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public OpstinaEntity getOpstina() {
		return opstina;
	}

	public void setOpstina(OpstinaEntity opstina) {
		this.opstina = opstina;
	}

	public DrzavaEntity getDrzava() {
		return drzava;
	}

	public void setDrzava(DrzavaEntity drzava) {
		this.drzava = drzava;
	}
}
