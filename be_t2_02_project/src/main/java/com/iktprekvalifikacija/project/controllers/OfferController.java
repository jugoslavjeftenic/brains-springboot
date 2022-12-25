package com.iktprekvalifikacija.project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.iktprekvalifikacija.project.entities.CategoryEntity;
import com.iktprekvalifikacija.project.entities.OfferEntity;

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
			String[][] offDesc = {
					{"Stemovanje zidova u stanovima", "Nepropustite sansu da iznervirate komsije"},
					{"Automobili", "Brednirajte Vaš službeni automobil ili pronađite agencije koje se bave iznajmljivanjem vozila."}
			};
			for (int i = 0; i < offDesc.length; i++) {
				offers.add(new OfferEntity(++oid, offDesc[i][0], offDesc[i][1], null, null, null, null, null, i, i, null));
			}
		}
		return offers;
	}

}
