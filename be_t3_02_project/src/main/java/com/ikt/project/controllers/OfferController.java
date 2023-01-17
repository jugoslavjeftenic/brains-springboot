package com.ikt.project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.project.entities.CategoryEntity;
import com.ikt.project.entities.EOfferEntity;
import com.ikt.project.entities.EUserRole;
import com.ikt.project.entities.OfferEntity;
import com.ikt.project.entities.UserEntity;
import com.ikt.project.repositories.CategoryRepository;
import com.ikt.project.repositories.OfferRepository;
import com.ikt.project.repositories.UserRepository;

import rade.RADE;

@RestController
@RequestMapping(value = "/api/v1/offers")
public class OfferController {

	// T3 1.3
	/*
	 * Izmeniti kontrolere kreirane na prethodnim časovima, tako da rade sa bazom
	 */
	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	// T2 3.3
	/*
	 * Kreirati REST endpoint koja vraća listu svih ponuda
	 * • putanja /project/offers
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<OfferEntity> getAll() {
		return offerRepository.findAll();
	}

	// T2 3.7
	/*
	 * Kreirati REST endpoint koji vraća ponudu po vrednosti prosleđenog ID-a
	 * • putanja /project/offers/{id}
	 * • u slučaju da ne postoji ponuda sa traženom vrednošću ID-a vratiti null
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public OfferEntity getById(@PathVariable Integer id) {
		OfferEntity offer;
		try {
			offer = offerRepository.findById(id).get();
			return offer;
		} catch (Exception e) {
			return null;
		}
	}

	// T2 3.4
	/*
	 * kreirati REST endpoint koji omogućava dodavanje nove ponude
	 * • putanja /project/offers
	 * • metoda treba da vrati dodatu ponudu
	 */
	// T3 2.3
	/*
	 * Omogućiti dodavanje kategorije i korisnika koji je kreirao ponudu
	 * • izmeniti prethodnu putanju za dodavanje ponude
	 * • putanja /project/offers/{categoryId}/seller/{sellerId}
	 * • NAPOMENA: samo korisnik sa ulogom ROLE_SELLER ima pravo da bude postavljen kao onaj ko je
	 *   kreirao/napravio ponudu (u suprotnom ne dozvoliti kreiranje ponude);
	 *   Kao datum kreiranja ponude postaviti trenutni datum i ponuda ističe za 10 dana od dana kreiranja
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/{categoryId}/seller/{sellerId}")
	public OfferEntity addOffer(@PathVariable Integer categoryId, @PathVariable Integer sellerId,
			@RequestBody OfferEntity newOffer) {
		try {
			CategoryEntity category = categoryRepository.findById(categoryId).get();
			UserEntity user = userRepository.findByIdAndUserRole(sellerId, EUserRole.ROLE_SELLER);
//			if (!(user.getUserRole() == EUserRole.ROLE_SELLER)) {
//				return null;
//			}
			newOffer.setOfferCreated(LocalDateTime.now().minusHours(RADE.mrRobot(0, 24)));
			newOffer.setOfferExpires(newOffer.getOfferCreated().plusDays(10));
			newOffer.setCategory(category);
			newOffer.setUser(user);
			return offerRepository.save(newOffer);
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<OfferEntity> populateTable(@PathVariable Integer count) {
		List<OfferEntity> offers = new ArrayList<>();
		String[][] offersData = {
				{"Stemovanje zidova", "Najbolji način da iznervirate komsije.", "500.00", "3000.00"},
				{"Sporski tipovi", "100% pogoci, analitična firma Zeleni i Kum.", "2000", "100000"},
				{"Kupon+ aplikacija", "Uštedite kupujući stvari koje vam nikada neće zatrebati.", "500", "1000"},
				{"Magazin Zdravo telo u zdravoj fotelji", "Zamenite vežbanje gledanjem slika. Uz svaki primerak magazina dobijate poster fotelje.", "500", "500"},
				{"Sredstvo za odmagljivanje", "Naučnici su dokazali da je naš prozvod 12% bolji od drugih proizvoda.", "2000", "3000"},
				{"Jučerašnji hleb", "Akcijski proizvodi pekare SZR Bred i Pita.", "40", "50"},
				{"Neželjena pošta", "Obradujte svoje najmilije neograničenom količinom reklama.", "500", "2000"},
				{"Smart toster", "sada sa još više funkcija koje nikada nećete koristiti!", "2000", "5000"},
				{"Antivirusni softver", "Uz svaki paket dobijate 10 besplatnih virusa!", "5000", "8000"},
				{"Lični podaci", "Prodajemo vaše podatke po povoljnoj ceni!", "100", "1000"},
				{"Živeti bez stresa", "Kupite našu novu knjigu o tome kako da se oslobodite stresa - i učinite se još više nervoznim!", "1000", "2000"},
				{"Čekanje u redu", "Povoljno čekamo u redu umesto Vas", "500", "5000"},
				{"Mršavko", "Kupite našu novu aplikaciju za mršavljenje - i još više se otežajte!", "500", "1000"},
				{"Beskorisni saveti", "Nudimo savetodavne usluge.", "1000", "5000"},
				{"Ti to možeš", "Kupite naše novo online predavanje o uspehu - i još više se osećajte kao neuspešni", "1000", "10000"},
				{"Kako se obogatiti", "Saveti o bogatstvu - po ceni koja će vas ostaviti bez para.", "100000", "500000"},
				{"Moje novo poglavlje", "Kupite našu novu knjigu o kreativnom pisanju - i otkrijte da ste i dalje bez ideja.", "10000", "15000"},
				{"I feel you", "Učlanite se u online zajednicu članova koji vas neće razumeti.", "500", "1000"},
				{"Iluzija sreće", "Privatni časovi po ceni koja će vas ostaviti razočaranim.", "2000", "3000"},
				{"Još samo minut", "Kupite našu novu aplikaciju za organizaciju vremena - i još više se osećajte pod stresom.", "1000", "5000"},
				{"U tvojoj blizini", "Usluga pronalaženja ljubavi - koja će vas ostaviti još više samima.", "5000", "10000"},
				{"Ostani isti", "Kupite našu novu knjigu o razvoju ličnosti - i saznajte da ste i dalje isti.", "3000", "5000"},
				{"Nesporazum", "Kurs poboljšanja komunikacije", "500", "1500"},
				{"Ha?", "Kurs pravopisa", "1000", "5000"},
				{"Odbijenica.SU", "Oglasite se na najboljem sajtu za pronalaženje posla!", "1000", "10000"}
		};
		if (count < 1) {
			count = offersData.length;
			for (int i = 0; i < count; i++) {
				offers.add(createOffer(offersData[i]));
			}
		}
		else {
			for (int i = 0; i < count; i++) {
				offers.add(createOffer(offersData[RADE.mrRobot(0, offersData.length)]));
			}
		}
		return offerRepository.saveAll(offers);
	}
	
	private OfferEntity createOffer(String[] offerData) {
		EOfferEntity[] offerStatus = EOfferEntity.values();
		OfferEntity offer = new OfferEntity();
		offer.setOfferName(offerData[0]);
		offer.setOfferDesc(offerData[1]);
		offer.setOfferCreated(LocalDateTime.now().minusHours(RADE.mrRobot(0, 24)));
		offer.setOfferExpires(offer.getOfferCreated().plusDays(10));
		Double rndPrice;
		try {
			rndPrice = RADE.mrRobot(Double.parseDouble(offerData[2]), Double.parseDouble(offerData[3]));
		} catch (Exception e) {
			rndPrice = 0.99;
		}
		offer.setRegularPrice(rndPrice);
		offer.setActionPrice(RADE.zaokruziNa99(rndPrice - (Math.round((rndPrice * RADE.mrRobot(0.05, 0.5))))));
		offer.setImagePath("");
		offer.setAvailableOffers(RADE.mrRobot(10, 1000));
		offer.setBoughtOffers(RADE.mrRobot(10, 1000));
		offer.setOfferStatus(offerStatus[RADE.mrRobot(0, offerStatus.length)]);
		return offer;
	}

	// T2 3.5
	/*
	 * Kreirati REST endpoint koji omogućava izmenu postojeće ponude
	 * • putanja /project/offers/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi treba da vrati null,
	 *   a u suprotnom vraća podatke ponude sa izmenjenim vrednostima
	 * • NAPOMENA: u okviru ove metode ne menjati vrednost atributa offer status
	 */
	// T3 2.4
	/*
	 * Omogućiti izmenu kategorije ponude
	 * • izmeniti prethodnu putanju za izmenu ponude
	 * • putanja /project/offers/{id}/category/{categoryId}
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/category/{categoryId}")
	public OfferEntity updateOffer(@PathVariable Integer id, @PathVariable Integer categoryId, 
			@RequestBody OfferEntity updatedOffer) {
		try {
			CategoryEntity category = categoryRepository.findById(categoryId).get();
			OfferEntity offer = offerRepository.findById(id).get();
			if (updatedOffer.getOfferName() != null) {
				offer.setOfferName(updatedOffer.getOfferName());
			}
			if (updatedOffer.getOfferDesc() != null) {
				offer.setOfferDesc(updatedOffer.getOfferDesc());
			}
			if (updatedOffer.getOfferCreated() != null) {
				offer.setOfferCreated(updatedOffer.getOfferCreated());
			}
//			// https://www.baeldung.com/java-comparing-dates
			if (updatedOffer.getOfferExpires() != null &&
					updatedOffer.getOfferExpires().isAfter(offer.getOfferCreated())) {
				offer.setOfferExpires(updatedOffer.getOfferExpires());
			}
			if (updatedOffer.getRegularPrice() != null) {
				offer.setRegularPrice(updatedOffer.getRegularPrice());
			}
			if (updatedOffer.getActionPrice() != null) {
				offer.setActionPrice(updatedOffer.getActionPrice());
			}
			if (updatedOffer.getImagePath() != null) {
				offer.setImagePath(updatedOffer.getImagePath());
			}
			if (updatedOffer.getAvailableOffers() != null) {
				offer.setAvailableOffers(updatedOffer.getAvailableOffers());
			}
			if (updatedOffer.getBoughtOffers() != null) {
				offer.setBoughtOffers(updatedOffer.getBoughtOffers());
			}
			offer.setCategory(category);
			return offerRepository.save(offer);
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 3.8
	/*
	 * Kreirati REST endpoint koji omogućava promenu vrednosti atributa offer status postojeće ponude
	 * • putanja /project/offers/changeOffer/{id}/status/{status}
	 * • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda treba da vrati null,
	 *   a u suprotnom vraća podatke o ponudi koja je obrisana
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "changeOffer/{id}/status/{status}")
	public OfferEntity updateUserRole(@PathVariable Integer id, @PathVariable EOfferEntity status) {
		try {
			OfferEntity offer = offerRepository.findById(id).get();
			offer.setOfferStatus(status);
			return offerRepository.save(offer);
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 3.6
	/*
	 * Kreirati REST endpoint koji omogućava brisanje postojeće ponude
	 * • putanja /project/offers/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda treba da vrati null,
	 *   a u suprotnom vraća podatke o ponudi koja je obrisana
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public OfferEntity deleteOffer(@PathVariable Integer id) {
		try {
			OfferEntity offer = offerRepository.findById(id).get();
			offerRepository.delete(offer);
			return offer;
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 3.9
	/*
	 * Kreirati REST endpoint koji omogućava pronalazak svih ponuda čija se akcijska cena
	 * nalazi u odgovarajućem rasponu
	 * • putanja /project/offers/findByPrice/{lowerPrice}/and/{upperPrice}
	 */
	@RequestMapping(method = RequestMethod.GET, path = "findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> getOfferByPriceRange(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		if (lowerPrice <= upperPrice) {
			return offerRepository.findByActionPriceBetween(lowerPrice, upperPrice);
		}
		return null;
	}
}
