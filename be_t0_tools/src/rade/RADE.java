package rade;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import rade.data.DataHolder;
import rade.entities.AdresaEntity;
import rade.entities.DrzavaEntity;
import rade.entities.OpstinaEntity;
import rade.entities.OsobaEntity;

public class RADE extends DataHolder {

	/*
	 * R.A.D.E (Random Allocation Data Enhancer)
	 * by Jugoslav Jeftenic
	 */
	
	public static OsobaEntity generisiOsobu() {
		return generisiOsobu(generisiDatumRodjenja(), generisiPol());
	}
	
	public static OsobaEntity generisiOsobu(int pol) {
		return generisiOsobu(generisiDatumRodjenja(), pol);
	}
	
	public static OsobaEntity generisiOsobu(String kategorija, int pol) {
		return generisiOsobu(generisiDatumRodjenja(kategorija), pol);
	}

	public static OsobaEntity generisiOsobu(int minGodina, int maxGodina, int pol) {
		return generisiOsobu(generisiDatumRodjenja(minGodina, maxGodina), pol);
	}

	public static OsobaEntity generisiOsobu(LocalDate dob, int pol) {
		OsobaEntity osoba = new OsobaEntity();
		osoba.setPol(pol);
		osoba.setDatumRodjenja(dob);
		osoba.setIme(generisiIme(osoba.getPol()));
		osoba.setPrezime(generisiPrezime());
		osoba.setJmbg(generisiJMBG(osoba.getDatumRodjenja(), osoba.getPol()));
		osoba.setAdresaRodjenja(generisiAdresu());
		osoba.setAdresaStanovanja(generisiAdresu());
		return osoba;
	}
	
	public static String generisiIme() {
		if (generisiPol() == 1) {
			return generisiZenskoIme();
		}
		return generisiMuskoIme();
	}
	
	public static String generisiIme(int pol) {
		if (pol == 1) {
			return generisiZenskoIme();
		}
		return generisiMuskoIme();
	}
	
	public static String generisiZenskoIme() {
		return dobaviZenskoIme(mrRobot(1, imenaZ.length - 1));
	}
	
	public static String dobaviZenskoIme(int i) {
		if (i < 1 || i > imenaZ.length - 1) {
			i = 0;
		}
		return imenaZ[i];
	}
	
	public static String generisiMuskoIme() {
		return dobaviMuskoIme(mrRobot(1, imenaM.length - 1));
	}

	public static String dobaviMuskoIme(int i) {
		if (i < 1 || i > imenaM.length - 1) {
			i = 0;
		}
		return imenaM[i];
	}

	public static String generisiPrezime() {
		return dobaviPrezime(mrRobot(1, prezimena.length - 1));
	}
	
	public static String dobaviPrezime(int i) {
		if (i < 1 || i > prezimena.length - 1) {
			i = 0;
		}
		return prezimena[i];
	}
	
	public static AdresaEntity generisiAdresu() {
		AdresaEntity adresa = new AdresaEntity();
		adresa.setUlica(generisiUlicu());
		adresa.setBroj(generisiBrojUlice());
		adresa.setOpstina(generisiOpstinu());
		try {
			adresa.setDrzava(dobaviDrzavu(adresa.getOpstina().getDrzava()));
		} catch (Exception e) {
			adresa.setDrzava(generisiDrzavu());
		}
		return adresa;
	}
	
	public static String generisiUlicu() {
		return dobaviUlicu(mrRobot(1, ulice.length - 1));
	}
	
	public static String dobaviUlicu(int i) {
		if (i < 1 || i > ulice.length - 1) {
			i = 0;
		}
		return ulice[i];
	}
	
	public static String generisiBrojUlice() {
		String[] brojUlice = {"bb.",
				String.valueOf(mrRobot(1, 200)),
				String.valueOf(mrRobot(1, 100)) + Character.toString((char) (mrRobot(97, 103)))
		};
		return brojUlice[mrRobot(0, brojUlice.length - 1)];
	}
	
	public static OpstinaEntity generisiOpstinu() {
		return dobaviOpstinu(mrRobot(1, opstine.length - 1));
	}

	public static List<OpstinaEntity> dobaviSveOpstine() {
		List<OpstinaEntity> listaOpstina = new ArrayList<>();
		for (int i = 1; i < opstine.length; i++) {
			listaOpstina.add(dobaviOpstinu(i));
		}
		return listaOpstina;
	}
	
	public static OpstinaEntity dobaviOpstinu(int i) {
		if (i < 1 || i > opstine.length - 1) {
			i = 0;
		}
		OpstinaEntity opstina = new OpstinaEntity();
		try {
			opstina.setSifra(Integer.parseInt(opstine[i][0]));
		} catch (Exception e) {
			opstina.setSifra(0);
		}
		opstina.setNaziv(opstine[i][1]);
		opstina.setPosta(opstine[i][2]);
		try {
			opstina.setDrzava(Integer.parseInt(opstine[i][3]));
		} catch (Exception e) {
			opstina.setDrzava(0);
		}
		return opstina;
	}
	
	public static DrzavaEntity generisiDrzavu() {
		return dobaviDrzavu(mrRobot(1, drzave.length - 1));
	}
	
	public static List<DrzavaEntity> dobaviSveDrzave() {
		List<DrzavaEntity> listaZemalja = new ArrayList<>();
		for (int i = 1; i < drzave.length; i++) {
			listaZemalja.add(dobaviDrzavu(i));
		}
		return listaZemalja;
	}

	public static DrzavaEntity dobaviDrzavu(int i) {
		if (i < 1 || i > drzave.length - 1) {
			i = 0;
		}
		DrzavaEntity drzava = new DrzavaEntity();
		try {
			drzava.setSifra(Integer.parseInt(drzave[i][0]));
		} catch (Exception e) {
			drzava.setSifra(0);
		}
		drzava.setAlfa(drzave[i][1]);
		drzava.setOznaka(drzave[i][2]);
		drzava.setNaziv(drzave[i][3]);
		drzava.setKontinent(drzave[i][4]);
		return drzava;
	}
	
	public static LocalDate generisiDatumRodjenja() {
		return generisiDatumRodjenja("");
	}

	public static LocalDate generisiDatumRodjenja(String kategorija) {
		int minGodina = 12;
		int maxGodina = 99;
		switch (kategorija) {
			case "dete":
				minGodina = 3;
				maxGodina = 12;
				break;
			case "student":
				minGodina = 17;
				maxGodina = 30;
				break;
			case "zaposlen":
				minGodina = 16;
				maxGodina = 65;
				break;
			case "penzioner":
				minGodina = 65;
				maxGodina = 111;
				break;
		}
		return generisiDatumRodjenja(minGodina, maxGodina);
	}
	
	public static LocalDate generisiDatumRodjenja(int minGodina, int maxGodina) {
		int godina = LocalDate.now().minusYears((long) mrRobot(minGodina, maxGodina)).getYear();
		int mesec = mrRobot(1, 12);
		int dan = mrRobot(1, YearMonth.of(godina, mesec).lengthOfMonth());
		LocalDate datumRodjenja = LocalDate.of(godina, mesec, dan); 
		return datumRodjenja;
	}
	
	public static int generisiPol() {
		return mrRobot(1, 2);
	}
	
	public static String generisiJMBG() {
		return generisiJMBG(generisiDatumRodjenja(), generisiPol());
	}
	
	public static String generisiJMBG(int pol) {
		return generisiJMBG(generisiDatumRodjenja(), pol);
	}
	
	public static String generisiJMBG(String kategorija, int pol) {
		return generisiJMBG(generisiDatumRodjenja(kategorija), pol);
	}
	
	public static String generisiJMBG(int minGodina, int maxGodina, int pol) {
		return generisiJMBG(generisiDatumRodjenja(minGodina, maxGodina), pol);
	}
	
	public static String generisiJMBG(LocalDate dob, int pol) {
		// https://www.telegraf.rs/vesti/1096931-otkrivamo-misteriju-evo-sta-jmbg-i-6-njegovih-brojeva-govore-o-vama
		String jmbg = "";
		LocalDate datumRodjenja = dob;
		// https://www.baeldung.com/java-datetimeformatter
		// https://stackoverflow.com/questions/33238082/last-digit-of-year-with-datetimeformatter
		DateTimeFormatter jmbgDateFormat = new DateTimeFormatterBuilder()
				.appendPattern("ddMM")
				.appendValueReduced(ChronoField.YEAR, 3, 3, 0)
				.toFormatter();
		jmbg  = datumRodjenja.format(jmbgDateFormat);
		jmbg += generisiPolitickuRegiju();
		jmbg += generisiJedinstveniBroj(pol);
		jmbg += modul11(jmbg);
		return jmbg;
	}
	
	private static String generisiPolitickuRegiju() {
		// TODO Napraviti sa realnim podacima i postaviti na public
		int regija = mrRobot(10, 99);
		if (regija > 59 && regija < 70) {
			// https://www.w3schools.com/java/java_recursion.asp
			return generisiPolitickuRegiju();
		}
		return String.format("%2s", regija).replace(" ", "0");
	}
	
	private static String generisiJedinstveniBroj(int pol) {
		int jb = mrRobot(0, 499);;
		if (pol == 1) {
			jb = mrRobot(500, 999);
		}
		return String.format("%3s", jb).replace(" ", "0");
	}
	
	private static String modul11(String jmbg) {
		// https://bug.rs/2013/05/korisne-javascript-biblioteke-za-poslovni-software-u-nas-srba/
		// https://www.elitesecurity.org/t483659-algoritam-za-proveru-JMBG-ili-Java-source
		Integer modul11 = 11 -((
				(7 * (Character.getNumericValue(jmbg.charAt(0)) + Character.getNumericValue(jmbg.charAt( 6)))) +
				(6 * (Character.getNumericValue(jmbg.charAt(1)) + Character.getNumericValue(jmbg.charAt( 7)))) +
				(5 * (Character.getNumericValue(jmbg.charAt(2)) + Character.getNumericValue(jmbg.charAt( 8)))) +
				(4 * (Character.getNumericValue(jmbg.charAt(3)) + Character.getNumericValue(jmbg.charAt( 9)))) +
				(3 * (Character.getNumericValue(jmbg.charAt(4)) + Character.getNumericValue(jmbg.charAt(10)))) +
				(2 * (Character.getNumericValue(jmbg.charAt(5)) + Character.getNumericValue(jmbg.charAt(11))))
				) % 11);
		return (modul11 > 9) ? "0" : modul11.toString();
	}
	
	@Deprecated  // Koristiti generisiOpstinu()
	public static String generisiGrad() {
		String[] gradovi = {"Beograd", "Novi Sad", "Nis", "Pristina", "Kragujevac", "Subotica",
				"Leskovac", "Krusevac", "Kraljevo", "Zrenjanin", "Pancevo", "Cacak", "Sabac", "Novi Pazar"};
		return gradovi[mrRobot(0, gradovi.length - 1)];
	}
	
	// TODO Prepraviti sve generatore ispod ove linije
	/** @param zanimanje 1-akademska karijera */
	/** @param zanimanje 9-neakademska zanimanja */
	public static String generisiZanimanje(int karijera) {
		
		String[] boranija = {"higijenicar", "lozac", "portir", "operater", "sekretar", "IT tehnicar"};
		String[] akademskaKarijera = {"DOCENT", "VANREDNI PROFESOR", "REDOVNI PROFESOR"};

		switch (karijera) {
		case 1:
			return akademskaKarijera[mrRobot(0, akademskaKarijera.length - 1)];
		case 9:
			return boranija[mrRobot(0, boranija.length - 1)];
		default:
			String[] zanimanja = new String[boranija.length + akademskaKarijera.length];
			int i = 0, j = 0;
			while (i < boranija.length) {
				zanimanja[i] = boranija[i];
				i++;
			}
			while (i < boranija.length + akademskaKarijera.length) {
				zanimanja[i] = akademskaKarijera[j];
				i++;
				j++;
			}
			return zanimanja[mrRobot(0, zanimanja.length - 1)];
		}
	}

	public static String generisiBoju() {
		String[] boja = {"Crvena", "Zelena", "Zuta", "Ljubicasta", "Narandzasta", "Roze", "Bela", "Crna", "Cijan", "Plava"};
		return boja[mrRobot(0, boja.length - 1)];
	}
	
	public static String generisiTim() {
		String[] tim = {"Sokoli", "Orlovi", "Vrabci", "Tigrovi", "Vukovi", "Gavrani", "Pobednici", "Bednici", "Lavovi", "Alkoholicari", "Nindze"};
		return tim[mrRobot(0, tim.length - 1)];
	}
	
	public static int mrRobot(int min, int max) {
		return (int) (min + Math.random() * (max - min));
//		return (int) ((Math.random() * (max - min + 1)) + min);
	}
	
	public static double mrRobot(double min, double max) {
		return min + Math.random() * (max - min);
//		return ((Math.random() * (max - min + 1)) + min);
	}
}
