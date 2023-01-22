package com.ikt.t1.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t1.entities.MinMax;

@RestController
public class MyFirstController {

	// 2.0
	@RequestMapping("/")
	public String hello() {
		return "Moja prva aplikacija";
	}

	// 2.1
	@RequestMapping("/greetings/{name}")
	public String helloYourName(@PathVariable(name = "name") String yourName) {
		return "Zdravo " + yourName;
	}
	
	// 2.2
	@RequestMapping("/date")
	public String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
		LocalDateTime now = LocalDateTime.now();
		return "Trenutno vreme/datum je: " + dtf.format(now);
	}
	
	// 2.3
	@RequestMapping("/family")
	public List<String> getFamilyList() {
		List<String> family = new ArrayList<String>();
		family.add("Mita Pauk");
		family.add("Zippy");
		return family;
	}
	
	// 2.4
	@RequestMapping("/myclass")
	public String getMyClass() {
		String html;
		html = "<!DOCTYPE html>";
		html += "<html lang=\"en\">";
		html += "<meta charset=\"UTF-8\">";
		html += "<title>Moja mala grupa mucenika</title>";
		html += "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">";
		html += "<link rel=\"stylesheet\" href=\"\">";
		html += "<body>";
		html += "<h2>Moja mala grupa mucenika</h2>";
		html += "<table>";
		html += "<tr><th>Rb.</th><th align = left>Polaznik</th></tr>";
		html += "<tr><td>1</td><td>Bernard Bujak</td></tr>";
		html += "<tr><td>2</td><td>Eržebet Čengeri</td></tr>";
		html += "<tr><td>3</td><td>Ivan cikaric</td></tr>";
		html += "<tr><td>4</td><td>Aleksandar Dikic</td></tr>";
		html += "<tr><td>5</td><td>Sanja Hodžić</td></tr>";
		html += "<tr><td>6</td><td>bojan janjic</td></tr>";
		html += "<tr><td>7</td><td>Mileva Jelic</td></tr>";
		html += "<tr><td>8</td><td>Obrad Kerkez</td></tr>";
		html += "<tr><td>9</td><td>Miladin Kovacevic</td></tr>";
		html += "<tr><td>10</td><td>Nikola Kovacevic</td></tr>";
		html += "<tr><td>11</td><td>Lenka Maravić</td></tr>";
		html += "<tr><td>12</td><td>Vanja Milenov</td></tr>";
		html += "<tr><td>13</td><td>Nemanja Pajcin</td></tr>";
		html += "<tr><td>14</td><td>Marina Palalić</td></tr>";
		html += "<tr><td>15</td><td>Aleksandra Pavlovic</td></tr>";
		html += "<tr><td>16</td><td>Luka Rakočević</td></tr>";
		html += "<tr><td>17</td><td>Bobana Simikic</td></tr>";
		html += "<tr><td>18</td><td>Marko Stojic</td></tr>";
		html += "<tr><td>19</td><td>Tanja Tosakovic</td></tr>";
		html += "<tr><td>20</td><td>Jugoslav Jeftenic</td></tr>";
		html += "</table>";
		html += "</body>";
		html += "</html>";
		return html;
	}
	
	// 3.1
	@RequestMapping("/array")
	public int[] getArray() {
		int[] niz = new int[10];
		for(int i = 0; i < niz.length; i++) {
			niz[i] = (int) ((Math.random() * (100 - 1)) + 1);
		}
		return niz;
	}
	
	// 3.2
	@RequestMapping("/sortarray")
	public int[] getSortedArray() {
		int[] niz = new int[10];
		for(int i = 0; i < niz.length; i++) {
			niz[i] = (int) ((Math.random() * (100 - 1)) + 1);
		}
		Arrays.sort(niz);
		return niz;
	}
	
	// 3.3.1
	@RequestMapping("/minmax")
	public String getMinMaxArray() {
		int[] niz = new int[10];
		for(int i = 0; i < niz.length; i++) {
			niz[i] = (int) ((Math.random() * (100 - 1)) + 1);
		}
		Arrays.sort(niz);
		return "Minimalna vrednost niza: " + niz[0] + ", maksimalna vrednost niza: " + niz[niz.length - 1];
	}
	
	// 3.3.2
	@RequestMapping("/minmaxclass")
	public MinMax getMinMaxArrayClass() {
		int[] niz = new int[10];
		for(int i = 0; i < niz.length; i++) {
			niz[i] = (int) ((Math.random() * (100 - 1)) + 1);
		}
		Arrays.sort(niz);
		MinMax minmax = new MinMax(niz[0], niz[niz.length - 1]);
		return minmax;
	}
	
	// 3.4
	@RequestMapping("/sumNiza/{n}")
	public int getSumNiza(@PathVariable Integer n) {
		int[] niz = new int[n * 2];
		for(int i = 0; i < niz.length; i++) {
			niz[i] = (int) ((Math.random() * (100 - 1)) + 1);
		}
		return Arrays.stream(niz).limit(n).sum();
	}

	// 3.5.1
	@RequestMapping(method = RequestMethod.GET, path = "/recnik")
	public String getPrevodParam(@RequestParam (value = "trazena_rec", required = false) String rec) {

		// https://www.javatpoint.com/java-hashmap
		HashMap<String, String> recnik = new HashMap<String, String>();
		recnik.put("aback", "unazad, zapanjen");
		recnik.put("abandon", "napustiti");
		recnik.put("abase", "spustiti, sniziti");
		recnik.put("abasement", "sniženje");
		recnik.put("abash", "zbuniti, osramotiti");
		recnik.put("abate", "umiriti, utišati");
		recnik.put("abbey", "manastir, samostan");
		recnik.put("abbot", "iguman, opat (prvi monah)");
		recnik.put("abbreviate", "skratiti");
		
		if (rec == null || rec == "") {
			return "Nedostaje parametar";
		}
		else if(recnik.get(rec) != null) {
			return recnik.get(rec);
		}
		else {
			return "Rec \"" + rec + "\" ne postoji u recniku.";
		}
	}
	
	// 3.5.2
	@RequestMapping(method = RequestMethod.GET, path = "/recnik/{trazena_rec}")
	public String getPrevodPath(@PathVariable (value = "trazena_rec", required = false) String rec) {
		
		// https://www.javatpoint.com/java-hashmap
		HashMap<String, String> recnik = new HashMap<String, String>();
		recnik.put("aback", "unazad, zapanjen");
		recnik.put("abandon", "napustiti");
		recnik.put("abase", "spustiti, sniziti");
		recnik.put("abasement", "sniženje");
		recnik.put("abash", "zbuniti, osramotiti");
		recnik.put("abate", "umiriti, utišati");
		recnik.put("abbey", "manastir, samostan");
		recnik.put("abbot", "iguman, opat (prvi monah)");
		recnik.put("abbreviate", "skratiti");
		
		if (rec == null || rec == "") {
			return "Nedostaje parametar";
		}
		else if(recnik.get(rec) != null) {
			return recnik.get(rec);
		}
		else {
			return "Rec \"" + rec + "\" ne postoji u recniku.";
		}
	}
}
