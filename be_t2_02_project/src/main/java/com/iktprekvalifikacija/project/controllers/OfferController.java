package com.iktprekvalifikacija.project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	 * kreirati REST endpoint koja vraća listu svih ponuda • putanja /project/offers
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<OfferEntity> getAllOffers() {
		return getDB();
	}

}
