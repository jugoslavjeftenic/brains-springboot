package rade;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import rade.data.DataHolder;
import rade.entities.DrzavaEntity;
import rade.entities.OpstinaEntity;

public class RADE extends DataHolder {

	/*
	 * R.A.D.E (Random Allocation Data Enhancer)
	 * by Jugoslav Jeftenic
	 */
	
	public static List<OpstinaEntity> listajSveOpstine() {
		List<OpstinaEntity> listaOpstina = new ArrayList<>();
		for (int i = 0; i < drzave.length; i++) {
			listaOpstina.add(generisiOpstinu(i));
		}
		return listaOpstina;
	}
	
	public static OpstinaEntity generisiOpstinu() {
		return generisiOpstinu(mrRobot(0, opstine.length));
	}

	public static OpstinaEntity generisiOpstinu(int i) {
		OpstinaEntity opstina = new OpstinaEntity();
		try {
			opstina.setSifra(Integer.parseInt(opstine[i][0]));
		} catch (Exception e) {
			opstina.setSifra(null);
		}
		opstina.setNaziv(opstine[i][1]);
		opstina.setPosta(opstine[i][2]);
		try {
			opstina.setDrzava(Integer.parseInt(opstine[i][3]));
		} catch (Exception e) {
			opstina.setDrzava(null);
		}
		return opstina;
	}
	
	public static List<DrzavaEntity> listajSveDrzave() {
		List<DrzavaEntity> listaZemalja = new ArrayList<>();
		for (int i = 0; i < drzave.length; i++) {
			listaZemalja.add(generisiDrzavu(i));
		}
		return listaZemalja;
	}
	
	public static DrzavaEntity generisiDrzavu() {
		return generisiDrzavu(mrRobot(0, drzave.length));
	}

	public static DrzavaEntity generisiDrzavu(int i) {
		DrzavaEntity drzava = new DrzavaEntity();
		try {
			drzava.setSifra(Integer.parseInt(drzave[i][0]));
		} catch (Exception e) {
			drzava.setSifra(null);
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
	
	public static String generisiJMBG() {
		return generisiJMBG(generisiDatumRodjenja());
	}
	
	public static String generisiJMBG(String kategorija) {
		return generisiJMBG(generisiDatumRodjenja(kategorija));
	}
	
	public static String generisiJMBG(int minGodina, int maxGodina) {
		return generisiJMBG(generisiDatumRodjenja(minGodina, maxGodina));
	}
	
	public static String generisiJMBG(LocalDate dob) {
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
		jmbg += generisiJedinstveniBroj();
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
	
	private static String generisiJedinstveniBroj() {
		int jb = mrRobot(0, 999);
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
	
//	public static String generisiJMBG() {
//		String JMBG = "";
//		switch (mrRobot(1, 12)) {
//		case 1:
//			JMBG += String.format("%2s", mrRobot(1, 31)).replace(" ", "0") + "01";
//			break;
//		case 2:
//			JMBG += String.format("%2s", mrRobot(1, 28)).replace(" ", "0") + "02";
//			break;
//		case 3:
//			JMBG += String.format("%2s", mrRobot(1, 31)).replace(" ", "0") + "03";
//			break;
//		case 4:
//			JMBG += String.format("%2s", mrRobot(1, 30)).replace(" ", "0") + "04";
//			break;
//		case 5:
//			JMBG += String.format("%2s", mrRobot(1, 31)).replace(" ", "0") + "05";
//			break;
//		case 6:
//			JMBG += String.format("%2s", mrRobot(1, 30)).replace(" ", "0") + "06";
//			break;
//		case 7:
//			JMBG += String.format("%2s", mrRobot(1, 31)).replace(" ", "0") + "07";
//			break;
//		case 8:
//			JMBG += String.format("%2s", mrRobot(1, 31)).replace(" ", "0") + "08";
//			break;
//		case 9:
//			JMBG += String.format("%2s", mrRobot(1, 30)).replace(" ", "0") + "09";
//			break;
//		case 10:
//			JMBG += String.format("%2s", mrRobot(1, 31)).replace(" ", "0") + "10";
//			break;
//		case 11:
//			JMBG += String.format("%2s", mrRobot(1, 30)).replace(" ", "0") + "11";
//			break;
//		case 12:
//			JMBG += String.format("%2s", mrRobot(1, 31)).replace(" ", "0") + "12";
//			break;
//		}
//		JMBG += String.valueOf(mrRobot(LocalDate.now().getYear() - 80, LocalDate.now().getYear() - 16)).substring(1);
//		// https://www.telegraf.rs/vesti/1096931-otkrivamo-misteriju-evo-sta-jmbg-i-6-njegovih-brojeva-govore-o-vama
//		// https://bug.rs/2013/05/korisne-javascript-biblioteke-za-poslovni-software-u-nas-srba/
//		// https://www.elitesecurity.org/t483659-algoritam-za-proveru-JMBG-ili-Java-source
//		JMBG += String.format("%2s", mrRobot(1, 99)).replace(" ", "0");
//		JMBG += String.format("%3s", mrRobot(0, 999)).replace(" ", "0");
//		int modul11 = 11 -
//				(((7 * (Character.getNumericValue(JMBG.charAt(0)) + Character.getNumericValue(JMBG.charAt(6)))) +
//						(6 * (Character.getNumericValue(JMBG.charAt(1)) + Character.getNumericValue(JMBG.charAt(7)))) +
//						(5 * (Character.getNumericValue(JMBG.charAt(2)) + Character.getNumericValue(JMBG.charAt(8)))) +
//						(4 * (Character.getNumericValue(JMBG.charAt(3)) + Character.getNumericValue(JMBG.charAt(9)))) +
//						(3 * (Character.getNumericValue(JMBG.charAt(4)) + Character.getNumericValue(JMBG.charAt(10)))) +
//						(2 * (Character.getNumericValue(JMBG.charAt(5)) + Character.getNumericValue(JMBG.charAt(11))))) % 11);
//		JMBG += (modul11 > 9) ? "0" : modul11;
//		return JMBG;
//	}
	
	/** @param rod 1-zensko 2-musko */
	public static String generisiIme(int rod) {
		String[] imenaZenska = {"Mia", "Tea", "Lea", "Mila", "Una"};
		String[] imenaMuska = {"Marko", "Petar", "Ivan", "Sreten", "Neven"};

		switch (rod) {
		case 1:
			return imenaZenska[mrRobot(0, imenaZenska.length - 1)];
		case 2:
			return imenaMuska[mrRobot(0, imenaMuska.length - 1)];
		default:
			String[] imena = new String[imenaZenska.length + imenaMuska.length];
			int i = 0, j = 0;
			while (i < imenaZenska.length) {
				imena[i] = imenaZenska[i];
				i++;
			}
			while (i < imenaZenska.length + imenaMuska.length) {
				imena[i] = imenaMuska[j];
				i++;
				j++;
			}
			return imena[mrRobot(0, imena.length - 1)];
		}
	}

	public static String generisiPrezime() {
		String[] prezimena = {"Markovic", "Kraljevic", "Petrovic", "Ivanovic", "Sretenovic", "Jovanovic", "Djordjevic", "Nikolic", "Popadic", "Stojanovic"};
		return prezimena[mrRobot(0, prezimena.length - 1)];
	}
	
	public static String generisiGrad() {
		String[] gradovi = {"Beograd", "Novi Sad", "Nis", "Pristina", "Kragujevac", "Subotica",
				"Leskovac", "Krusevac", "Kraljevo", "Zrenjanin", "Pancevo", "Cacak", "Sabac", "Novi Pazar"};
		return gradovi[mrRobot(0, gradovi.length - 1)];
	}
	
	public static String generisiUlicu() {
		String[] ulice = {"Programerska", "Bulevar palih juniora", "Regruterski kvart", "Eklipse",
				"Palata Seniorskih Vladara", "Ulica praktikanata", "Učenički šor"};
		return ulice[mrRobot(0, ulice.length - 1)];
	}
	
	public static String generisiBrojUlice() {
		String[] brojUlice = {"bb.", String.valueOf(mrRobot(1, 200)), String.valueOf(mrRobot(1, 100)) + Character.toString((char) (mrRobot(97, 103)))};
		return brojUlice[mrRobot(0, brojUlice.length - 1)];
	}
	
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
