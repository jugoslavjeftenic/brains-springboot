package com.iktprekvalifikacija.restexamples.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.restexamples.entities.BankClientEntity;
import com.iktprekvalifikacija.restexamples.entities.BankClientsEntityNames;

import tools.*;

@RestController
@RequestMapping("/bankclients")
public class BankClientRestController {

	static int id = 0;

	List<BankClientEntity> clients = new ArrayList<BankClientEntity>();

	private List<BankClientEntity> getDB() {
		if(this.clients.size() == 0) {
			id = 0;
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
			clients.add(generateClient());
		}
		return clients;
	}

	private BankClientEntity generateClient() {
		String firstName = RADE.generisiIme(0);
		String lastName = RADE.generisiPrezime();
		BankClientEntity client = new BankClientEntity(++id, firstName, lastName,
				firstName.toLowerCase() + "." + lastName.substring(0, 1).toLowerCase() + "@bomba.net",
				RADE.generisiGrad(), RADE.generisiDatumRodjenja(16, 100));
		return client;
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<BankClientEntity> getAllClients() {
		return getDB();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{clientID}")
	public BankClientEntity getClientById(@PathVariable Integer clientID) {
		for (BankClientEntity client : getDB()) {
			if(client.getId().equals(clientID)) {
				return client;
			}
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public BankClientEntity addClient(@RequestBody BankClientEntity newClient) {
		getDB();
		newClient.setId(++id);
		clients.add(newClient);
		return newClient;
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{clientID}")
	public BankClientEntity updateClient(@PathVariable Integer clientID, @RequestBody BankClientEntity updatedClient) {
		for (BankClientEntity client : getDB()) {
			if (client.getId().equals(clientID)) {
				if (updatedClient.getFirstName() != null) {
					client.setFirstName(updatedClient.getFirstName());
				}
				if (updatedClient.getLastName() != null) {
					client.setLastName(updatedClient.getLastName());
				}
				if (updatedClient.getEmail() != null) {
					client.setEmail(updatedClient.getEmail());
				}
				if (updatedClient.getBirthDay() != null) {
					client.setBirthDay(updatedClient.getBirthDay());
				}
				if (updatedClient.getBonitet() != null) {
					client.setBonitet(updatedClient.getBonitet());
				}
				return client;
			}
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{clientID}")
	public BankClientEntity deleteClient(@PathVariable Integer clientID) {
		for (BankClientEntity client : getDB()) {
			getDB().remove(client);
			return client;
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/search")
	public List<BankClientEntity> searchByName(@RequestParam String name) {
		List<BankClientEntity> retVal = new ArrayList<BankClientEntity>();
		for (BankClientEntity client : getDB()) {
			if (client.getFirstName().equalsIgnoreCase(name)) {
				retVal.add(client);
			}
		}
		return retVal;
	}
	
	// 1.1
	@RequestMapping(method = RequestMethod.GET, path = "/emails")
	public List<String> getEmails() {
		List<String> retVal = new ArrayList<String>();
		for (BankClientEntity clients : getDB()) {
			retVal.add(clients.getEmail());
		}
		return retVal;
	}

	// 1.2.1
	@RequestMapping(method = RequestMethod.GET, path = "/clients/{firstLetter}")
	public List<String> searchByFirstLetterName(@PathVariable String firstLetter) {
		List<String> retVal = new ArrayList<String>();
		for (BankClientEntity client : getDB()) {
			if (StringUtils.startsWithIgnoreCase(client.getFirstName(), firstLetter)) {
				retVal.add(client.getFirstName());
			}
		}
		return retVal;
	}

	// 1.2.2
	@RequestMapping(method = RequestMethod.GET, path = "/clients/regex/{firstLetter}")
	public List<String> searchByFirstLetterNameRegEx(@PathVariable String firstLetter) {
		List<String> retVal = new ArrayList<String>();
		for (BankClientEntity client : getDB()) {
			if (Pattern.compile("^" + firstLetter, Pattern.CASE_INSENSITIVE).matcher(client.getFirstName()).find()) {
				retVal.add(client.getFirstName());
			}
		}
		return retVal;
	}

	// 1.3
	@RequestMapping(method = RequestMethod.GET, path = "/clients/firstLetters")
	public List<BankClientsEntityNames> searchByFirstLetterNameLastName(@RequestParam String firstLetterName,
			@RequestParam String firstLetterLastName) {
		List<BankClientsEntityNames> retVal = new ArrayList<BankClientsEntityNames>();
		for (BankClientEntity client : getDB()) {
			if (StringUtils.startsWithIgnoreCase(client.getFirstName(), firstLetterName) &&
					StringUtils.startsWithIgnoreCase(client.getLastName(), firstLetterLastName)) {
				BankClientsEntityNames clientNames = new BankClientsEntityNames(client.getFirstName(), client.getLastName());
				retVal.add(clientNames);
			}
		}
		return retVal;
	}

	// 1.4
	@RequestMapping(method = RequestMethod.GET, path = "/clients/sort/{order}")
	public List<String> sortByOrder(@PathVariable String order) {
		List<String> retVal = new ArrayList<String>();
		for (BankClientEntity client : getDB()) {
			retVal.add(client.getFirstName());
		}
		if (order.equalsIgnoreCase("asc")) {
			retVal.sort(Comparator.naturalOrder());
		}
		else if (order.equalsIgnoreCase("desc")) {
			retVal.sort(Comparator.reverseOrder());
		}
		return retVal;
	}

	// 2.1
	@RequestMapping(method = RequestMethod.GET, path = "/clients/bonitet")
	public Integer updateBonitet() {
		int counter = 0;
		for (BankClientEntity client : getDB()) {
//			client.setBonitet((client.calculateYearsSpanFromNow(client.getBirthDay()) < 65) ? 'P' : 'N');
			if (client.calculateYearsSpanFromNow(client.getBirthDay()) < 65 && !client.getBonitet().equals('P')) {
				client.setBonitet('P');
				counter++;
			}
			else if (client.calculateYearsSpanFromNow(client.getBirthDay()) >= 65 && !client.getBonitet().equals('N')) {
				client.setBonitet('N');
				counter++;
			}
		}
		return counter;
	}
	
	//2.2
	@RequestMapping(method = RequestMethod.GET, path = "/clients/delete")
	public List<BankClientEntity> deleteBadClient() {
		List<BankClientEntity> badClient = new ArrayList<BankClientEntity>();
		Iterator<BankClientEntity> iterator = getDB().iterator();
		while (iterator.hasNext()) {
			BankClientEntity client = iterator.next();
			if (client.getFirstName() == null || client.getFirstName() == "" || client.getLastName() == null ||
			client.getLastName() == "" || client.getEmail() == null || client.getEmail() == "") {
				badClient.add(client);
				iterator.remove();
			}
		}
		return badClient;
	}
	
	// 2.3
	@RequestMapping(method = RequestMethod.GET, path = "/clients/countLess/{years}")
	public Integer countLess(@PathVariable Integer years) {
		Integer counter = 0;
		for (BankClientEntity client : getDB()) {
			if (client.calculateYearsSpanFromNow(client.getBirthDay()) < years) {
				counter++;
			}
		}
		return counter;
	}
	
	// 2.4
	@RequestMapping(method = RequestMethod.GET, path = "/clients/averageYears")
	public Double getAverageYears() {
		Double sum = 0.0;
		for (BankClientEntity client : getDB()) {
			sum += client.calculateYearsSpanFromNow(client.getBirthDay());
		}
		return sum / clients.size();
	}
	
	// 3.1
	@RequestMapping(method = RequestMethod.PUT, path = "/clients/changelocation/{clientID}")
	public BankClientEntity updateCity(@PathVariable Integer clientID, @RequestParam String city) {
		for (BankClientEntity client : getDB()) {
			if (client.getId().equals(clientID)) {
				if (city != null) {
					client.setCity(city);
				}
				return client;
			}
		}
		return null;
	}
	
	// 3.2
	@RequestMapping(method = RequestMethod.GET, path = "/clients/from/{city}")
	public List<BankClientEntity> getClientByCity(@PathVariable String city) {
		List<BankClientEntity> retVal = new ArrayList<BankClientEntity>();
		for (BankClientEntity client : getDB()) {
			if (client.getCity().equalsIgnoreCase(city)) {
				retVal.add(client);
			}
		}
		return retVal;
	}
	
	// 3.3
	@RequestMapping(method = RequestMethod.GET, path = "/clients/findByCityAndAge")
	public List<BankClientEntity> getClientByCityAndAge(@RequestParam String city, @RequestParam Integer age) {
		List<BankClientEntity> retVal = new ArrayList<BankClientEntity>();
		for (BankClientEntity client : getDB()) {
			if (client.getCity().equalsIgnoreCase(city) &&
					client.calculateYearsSpanFromNow(client.getBirthDay()) < age) {
				retVal.add(client);
			}
		}
		return retVal;
	}
}
