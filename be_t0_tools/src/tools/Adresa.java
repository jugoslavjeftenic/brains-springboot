package tools;

public class Adresa {

	private String drzava;
	private String grad;
	private String ulica;
	private String broj;
	
	public Adresa() {
		super();
	}

	public Adresa(String drzava, String grad, String ulica, String broj) {
		super();
		this.drzava = drzava;
		this.grad = grad;
		this.ulica = ulica;
		this.broj = broj;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
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
}
