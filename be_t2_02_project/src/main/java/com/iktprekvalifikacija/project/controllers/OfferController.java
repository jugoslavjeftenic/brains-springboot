package com.iktprekvalifikacija.project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.project.entities.EOfferEntity;
import com.iktprekvalifikacija.project.entities.OfferEntity;

import tools.RADE;

@RestController
@RequestMapping(value = "/project/offers")
public class OfferController {

	// 3.2
	/*
	 * U paketu com.iktpreobuka.project.controllers napraviti klasu OfferController
	 * sa metodom get DB() koja vraća listu svih ponuda
	 */
	static int oid = 0;
	List<OfferEntity> offers = new ArrayList<>();

	private List<OfferEntity> getDB() {
		if(offers.size() == 0) {
			oid = 0;
			// TODO Umesto string matrice napraviti listu objekata
			String[][] offProperties = {
					{"Stemovanje zidova", "Najbolji način da iznervirate komsije.", "500.00", "3000.00"},
					{"Sportski tipovi", "100% pogoci, analitička firma Zeleni i Kum.", "2000", "100000"},
					{"Kupon+ aplikacija", "Uštedite sada kupujući stvari koje će vam jednog dana sigurno zatrebati.", "999.99", "999.99"},
					{"Magazin Zdravo telo u zdravoj fotelji", "Zamenite vežbanje gledanjem slika. Uz svaki primerak magazina dobijate poster fotelje.", "500", "500"},
					{"Sredstvo za odmagljivanje", "Naučnici su dokazali da je naš prozvod 12% bolji od nekih drugih proizvoda.", "2599.99", "2599.99"},
					{"Jučerašnji hleb", "Akcijski proizvodi pekare SZR Bred i Pita.", "50", "50"}
			};
			for (int i = 0; i < offProperties.length; i++) {
				LocalDateTime rndOfferStart = LocalDateTime.now().minusHours(RADE.mrRobot(0, 24));
				Float rndPrice;
				try {
					rndPrice = (float) RADE.mrRobot(Float.parseFloat(offProperties[i][2]), Float.parseFloat(offProperties[i][3]));
				} catch (Exception e) {
					rndPrice = 1F;
				}
				EOfferEntity offerStatus = EOfferEntity.values()[RADE.mrRobot(0, EOfferEntity.values().length - 1)];
				offers.add(new OfferEntity(++oid, offProperties[i][0], offProperties[i][1],
						rndOfferStart, rndOfferStart.plusDays(RADE.mrRobot(7, 30)),
						rndPrice, (float) (rndPrice - (Math.round((rndPrice * RADE.mrRobot(0.05, 0.5))))),
						"", RADE.mrRobot(10, 1000), RADE.mrRobot(10, 1000), offerStatus));
			}
		}
		return offers;
	}

	// 3.3
	/*
	 * Kreirati REST endpoint koja vraća listu svih ponuda
	 * • putanja /project/offers
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<OfferEntity> getAllOffers() {
		return getDB();
	}

	// 3.4
	/*
	 * kreirati REST endpoint koji omogućava dodavanje nove ponude
	 * • putanja /project/offers
	 * • metoda treba da vrati dodatu ponudu
	 */
	@RequestMapping(method = RequestMethod.POST)
	public OfferEntity saveOffer(@RequestBody OfferEntity newOffer) {
		getDB();
		newOffer.setId(++oid);
		offers.add(newOffer);
		return newOffer;
	}
	
	// 3.5
	/*
	 * Kreirati REST endpoint koji omogućava izmenu postojeće ponude
	 * • putanja /project/offers/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi treba da vrati null,
	 *   a u suprotnom vraća podatke ponude sa izmenjenim vrednostima
	 * • NAPOMENA: u okviru ove metode ne menjati vrednost atributa offer status
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public OfferEntity updateOffer(@PathVariable Integer id, @RequestBody OfferEntity updatedOffer) {
		for (OfferEntity offers : getDB()) {
			if (offers.getId().equals(id)) {
				if (updatedOffer.getOfferName() != null) {
					offers.setOfferName(updatedOffer.getOfferName());
				}
				if (updatedOffer.getOfferDesc() != null) {
					offers.setOfferDesc(updatedOffer.getOfferDesc());
				}
				if (updatedOffer.getOfferCreated() != null) {
					offers.setOfferCreated(updatedOffer.getOfferCreated());
				}
				if (updatedOffer.getOfferExpires() != null) {
					offers.setOfferExpires(updatedOffer.getOfferExpires());
				}
				if (updatedOffer.getRegularPrice() != null) {
					offers.setRegularPrice(updatedOffer.getRegularPrice());
				}
				if (updatedOffer.getActionPrice() != null) {
					offers.setActionPrice(updatedOffer.getActionPrice());
				}
				if (updatedOffer.getImagePath() != null) {
					offers.setImagePath(updatedOffer.getImagePath());
				}
				if (updatedOffer.getAvailableOffers() != null) {
					offers.setAvailableOffers(updatedOffer.getAvailableOffers());
				}
				if (updatedOffer.getBoughtOffers() != null) {
					offers.setBoughtOffers(updatedOffer.getBoughtOffers());
				}
				return offers;
			}
		}
		return null;
	}
	
	// 3.6
	/*
	 * Kreirati REST endpoint koji omogućava brisanje postojeće ponude
	 * • putanja /project/offers/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda treba da vrati null,
	 *   a u suprotnom vraća podatke o ponudi koja je obrisana
	 */
	

}
