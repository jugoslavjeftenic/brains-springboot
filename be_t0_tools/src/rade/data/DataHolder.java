package rade.data;

public class DataHolder {
	// TODO Podatke cuvati u #mapama?
	// TODO Adrese prilagoditi podacima sa linka https://data.gov.rs/sr/datasets/
	
	// http://www.devizni.gov.rs/PDF/PR10%20-%20Sifarnik%20zemalja%20-%20lat.pdf
	protected static String[][] drzave = {
			{"70", "BA", "BIH", "Bosna i Hercegovina", "Evropa"},
			{"191", "HR", "HRV", "Hrvatska", "Evropa"},
			{"499", "ME", "MNE", "Crna Gora", "Evropa"},
			{"533", "AW", "ABW", "Aruba", "South America"},
			{"688", "RS", "SRB", "Srbija", "Evropa"},
			{"705", "SI", "SVN", "Slovenija", "Evropa"},
			{"807", "MK", "MKD", "Republika Makedonija", "Evropa"},
			{"1000", "", "", "Uraatnia", ""},
			{"1001", "", "", "Azdrad", ""}
	};
	
	// https://poslovne.com/postanski-broj-2/?fbclid=IwAR0ui9u0VRkUon9XVyrXlsrTAju8StMVtX6QM9Lo1I3RNmQdfoxpduiS6O8
	// https://www.neobilten.com/sifarnik-naseljenih-mesta-u-republici-srbiji/
	// http://kodekssifara.minrzs.gov.rs/documents/Sifarnik_opstina_u_Republici_Srbiji.pdf
	// http://www.cekos.rs/%C5%A1ifarnik-op%C5%A1tina-u-republici-srbiji
	protected static String[][] opstine = {
			{"70149", "Beograd - Zvezdara", "", "688"},	
			{"70181", "Beograd - Novi Beograd", "", "688"},	
			{"70220", "Beograd - Savski venac", "", "688"},	
			{"80284", "Novi Sad", "21000", "688"},	
			{"71323", "Niš - Palilula", "", "688"},	
			{"71307", "Niš - Pantele", "", "688"},	
			{"70645", "Kragujevac", "", "688"},	
			{"80438", "Subotica", "24000", "688"},	
			{"70726", "Leskovac", "", "688"},	
			{"70670", "Kruševac", "", "688"},	
			{"70653", "Kraljevo", "", "688"},	
			{"80152", "Zrenjanin", "", "688"},	
			{"80314", "Pančevo", "", "688"},	
			{"71242", "Čačak", "", "688"},	
			{"71269", "Šabac", "", "688"},	
			{"70874", "Novi Pazar", "", "688"},	
			{"", "Sarajevo", "", "70"},	
			{"", "Zagreb", "", "191"},	
			{"", "Podgorica", "", "499"},	
			{"", "Ljubljana", "", "705"},	
			{"", "Skopje", "", "807"},	
			{"", "Oranjestad", "", "533"},	
			{"", "Uraatos", "", ""},	
			{"", "Nassau", "", ""}	
	};

	// https://data.gov.rs/sr/datasets/adresni-registar-shifarnik/
	// https://forum.openstreetmap.org/viewtopic.php?id=73217
	protected static String[] ulice = {
			"Programerska",
			"Eklipse",
			"Učenički šor",
			"Praktikantska",
			"Bulevar palih juniora",
			"Palata Seniorskih Vladara",
			"Regruterski kvart",
			"Bulevar Oslobođenja",
			"Kneza Miloša",
			"Kralja Petra",
			"Kralja Aleksandra",
			"Kralja Milana",
			"Narodnog fronta",
			"Nemanjina",
			"Maršala Tolbuhina",
			"Vojvode Stepe",
			"Jevrejska",
			"Strahinjića Bana",
			"Nikole Pašića",
			"Slobodana Jovanovića",
			"Žarka Zrenjanina",
			"Veljka Vlahovića",
			"Dušana Radovića",
			"Mihajla Pupina",
			"Dečanska",
			"Braće Jerković",
			"Makenzijeva",
			"Sarajevska",
			"Maršala Tita",
			"Grabovačka",
			"Zadnja"};
}