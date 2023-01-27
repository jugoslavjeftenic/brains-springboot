package com.ikt.t4.project.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikt.t4.project.entities.CategoryEntity;
import com.ikt.t4.project.entities.EOfferStatus;
import com.ikt.t4.project.entities.EUserRole;
import com.ikt.t4.project.entities.OfferEntity;
import com.ikt.t4.project.entities.UserEntity;
import com.ikt.t4.project.repositories.CategoryRepository;
import com.ikt.t4.project.repositories.OfferRepository;
import com.ikt.t4.project.repositories.UserRepository;

import rade.RADE;

@Service
public class OfferDAOServiceImpl implements OfferDAOService {

	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<OfferEntity> generateListOfOffers(Integer count) {
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
		EOfferStatus[] offerStatus = EOfferStatus.values();
		Long[] idxCategories = categoryRepository.findAllIds();
		Long[] idxUsers = userRepository.findAllIds();
		for (int i = 0; i < count; i++) {
			OfferEntity offer = new OfferEntity();
			int rndOffer = RADE.mrRobot(0, offersData.length);
			offer.setOfferName(offersData[rndOffer][0]);
			offer.setOfferDesc(offersData[rndOffer][1]);
			offer.setOfferCreated(LocalDateTime.now().minusHours(RADE.mrRobot(0, 24)));
			offer.setOfferExpires(offer.getOfferCreated().plusDays(10));
			offer.setRegularPrice(Math.ceil(RADE.mrRobot(Double.parseDouble(offersData[rndOffer][2]), Double.parseDouble(offersData[rndOffer][3]))));
			offer.setActionPrice(RADE.zaokruziNa99(offer.getRegularPrice() - (Math.round((offer.getRegularPrice() * RADE.mrRobot(0.05, 0.5))))));
			offer.setImagePath("");
			offer.setAvailableOffers(RADE.mrRobot(10, 1000));
			offer.setBoughtOffers(RADE.mrRobot(10, 1000));
			offer.setOfferStatus(offerStatus[RADE.mrRobot(0, offerStatus.length)]);
			offer.setCategory(categoryRepository.findById(RADE.mrRobot(idxCategories[0], idxCategories.length)).get());
			offer.setUser(userRepository.findById(RADE.mrRobot(idxUsers[0], idxUsers.length)).get());
			offerRepository.save(offer);
			offers.add(offer);
			System.out.println("row:" + i + ", " + offer.getOfferName());
		}
		return offers;
	}

	@Override
	public OfferEntity checkAndChangeOfferData(Long categoryId, Long sellerId, OfferEntity offerToCheck) {
		OfferEntity offerToReturn = new OfferEntity();
		if (offerToCheck.getId() != null) {
			try {
				offerToReturn = offerRepository.findById(offerToCheck.getId()).get();
			} catch (Exception e) {
				// TODO Vratiti gresku da nema ponude u bazi.
				return null;
			}
		}
		if (offerToCheck.getOfferName() != null) {
			offerToReturn.setOfferName(offerToCheck.getOfferName());
		}
		if (offerToCheck.getOfferDesc() != null) {
			offerToReturn.setOfferDesc(offerToCheck.getOfferDesc());
		}
		if (offerToCheck.getId() == null) {
			offerToReturn.setOfferCreated(LocalDateTime.now());
			offerToReturn.setOfferExpires(offerToReturn.getOfferCreated().plusDays(10));
		}
		else {
			if (offerToCheck.getOfferCreated() != null) {
				offerToReturn.setOfferCreated(offerToCheck.getOfferCreated());
				offerToReturn.setOfferExpires(offerToReturn.getOfferCreated().plusDays(10));
			}
		}
		if (offerToCheck.getRegularPrice() != null) {
			offerToReturn.setRegularPrice(offerToCheck.getRegularPrice());
		}
		if (offerToCheck.getActionPrice() != null) {
			offerToReturn.setActionPrice(offerToCheck.getActionPrice());
		}
		if (offerToCheck.getImagePath() != null) {
			offerToReturn.setImagePath(offerToCheck.getImagePath());
		}
		if (offerToCheck.getAvailableOffers() != null) {
			offerToReturn.setAvailableOffers(offerToCheck.getAvailableOffers());
		}
		if (offerToCheck.getId() == null) {
			offerToReturn.setBoughtOffers(0);
			offerToReturn.setOfferStatus(EOfferStatus.WAIT_FOR_APPROVING);
		}
		if (categoryId != null) {
			CategoryEntity category = new CategoryEntity();
			try {
				category = categoryRepository.findById(categoryId).get();
			} catch (Exception e) {
				// TODO Vratiti gresku da nema kategorije u bazi.
				return null;
			}
			offerToReturn.setCategory(category);
		}
		if (sellerId != null) {
			UserEntity user = new UserEntity();
			try {
				user = userRepository.findById(sellerId).get();
				if (user.getUserRole() != EUserRole.ROLE_SELLER) {
					// TODO Vratiti gresku da korisnik nije prodavac.
					return null;
				}
			} catch (Exception e) {
				// TODO Vratiti gresku da nema prodavca u bazi.
				return null;
			}
			offerToReturn.setUser(user);
		}
		return offerToReturn;
	}
}
