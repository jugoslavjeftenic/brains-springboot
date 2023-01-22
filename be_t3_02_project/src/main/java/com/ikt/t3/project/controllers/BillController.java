package com.ikt.t3.project.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t3.project.entites.BillEntity;
import com.ikt.t3.project.entites.CategoryEntity;
import com.ikt.t3.project.entites.OfferEntity;
import com.ikt.t3.project.repositories.BillRepository;
import com.ikt.t3.project.repositories.CategoryRepository;
import com.ikt.t3.project.repositories.OfferRepository;
import com.ikt.t3.project.repositories.UserRepository;

import rade.RADE;

@RestController
@RequestMapping(value = "/api/v1/bills")
public class BillController {

	// T3 3.3
	/*
	 * U paketu com.iktpreobuka.project.controllers napraviti klasu BillController
	 * sa REST endpoint-om koji vraća listu svih računa
	 * • putanja /project/bills
	 */
	@Autowired
	private BillRepository billRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<BillEntity> getAll() {
		return billRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public BillEntity getById(@PathVariable Integer id) {
		BillEntity bill;
		try {
			bill = billRepository.findById(id).get();
			return bill;
		} catch (Exception e) {
			return null;
		}
	}
	// T3 3.6
	/*
	 * Kreirati REST endpoint-ove za dodavanje, izmenu i brisanje računa
	 * • putanja /project/bills/{offerId}/buyer/{buyerId} (dodavanje)
	 * • putanja /project/bills/{id} (izmena i brisanje)
	 */
	// T3 5.1
	/*
	 * Proširiti metodu za dodavanje računa tako da se smanji broj dostupnih ponuda ponude sa računa,
	 * odnosno poveća broj kupljenih
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/{offerId}/buyer/{buyerId}")
	public BillEntity addBill(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@RequestParam Boolean paymentMade, @RequestParam Boolean paymentCanceled) {
		if (paymentMade == false || paymentCanceled == true) {
			return null;
		}
		BillEntity newBill = new BillEntity();
		newBill.setPaymentMade(paymentMade);
		newBill.setPaymentCanceled(paymentCanceled);
		newBill.setBillCreated(LocalDateTime.now());
		OfferEntity offer;
		try {
			offer = offerRepository.findById(offerId).get();
		} catch (Exception e) {
			return null;
		}
		if (offer.getAvailableOffers() > 0) {
			offer.setAvailableOffers(offer.getAvailableOffers() - 1);
			offer.setBoughtOffers(offer.getBoughtOffers() + 1);
		}
		else {
			return null;
		}
		newBill.setOffer(offer);
		try {
			newBill.setUser(userRepository.findById(buyerId).get());
		} catch (Exception e) {
			return null;
		}
		return billRepository.save(newBill);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<BillEntity> populateTable(@PathVariable Integer count) {
		// TODO Treba napraviti da vraca ID-ove rekorda a ne count.
		int offersCount = (int) offerRepository.count();
		int usersCount = (int) userRepository.count();
		List<BillEntity> bills = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			BillEntity bill = new BillEntity();
			bill.setPaymentMade(RADE.mrRobot());
			bill.setPaymentCanceled(RADE.mrRobot());
			bill.setBillCreated(LocalDateTime.now().minusDays(5).plusDays(RADE.mrRobot(1, 10)));
			try {
				bill.setOffer(offerRepository.findById(RADE.mrRobot(1, offersCount)).get());
			} catch (Exception e) {
				bill.setOffer(null);
			}
			try {
				bill.setUser(userRepository.findById(RADE.mrRobot(1, usersCount)).get());
			} catch (Exception e) {
				bill.setUser(null);
			}
			bills.add(bill);
		}
		return billRepository.saveAll(bills);
	}
	
	// T3 5.2
	/*
	 * Proširiti metodu za izmenu računa tako da ukoliko se račun proglašava otkazanim tada treba
	 * povećati broj dostupnih ponuda ponude sa računa, odnosno smanjiti broj kupljenih
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public BillEntity updateBill(@PathVariable Integer id, @RequestBody BillEntity updatedBill) {
		BillEntity bill;
		try {
			bill = billRepository.findById(id).get();
		} catch (Exception e) {
			return null;
		}
		OfferEntity offer;
		if (updatedBill.getPaymentCanceled()) {
			try {
				if (updatedBill.getOffer() != null) {
					offer = offerRepository.findById(updatedBill.getOffer().getId()).get();
				}
				else {
					offer = offerRepository.findById(bill.getOffer().getId()).get();
				}
				offer.setAvailableOffers(offer.getAvailableOffers() + 1);
				offer.setBoughtOffers(offer.getBoughtOffers() - 1);
			} catch (Exception e) {
				return null;
			}
		}
		try {
			if (updatedBill.getPaymentMade() != null) {
				bill.setPaymentMade(updatedBill.getPaymentMade());
			}
			if (updatedBill.getPaymentCanceled() != null) {
				bill.setPaymentCanceled(updatedBill.getPaymentCanceled());
			}
			if (updatedBill.getBillCreated() != null) {
				bill.setBillCreated(updatedBill.getBillCreated());
			}
			if (updatedBill.getOffer() != null) {
				try {
					bill.setOffer(offerRepository.findById(updatedBill.getOffer().getId()).get());
				} catch (Exception e) {
					bill.setOffer(null);
				}
			}
			if (updatedBill.getUser() != null) {
				try {
					bill.setUser(userRepository.findById(updatedBill.getUser().getId()).get());
				} catch (Exception e) {
					bill.setUser(null);
				}
			}
			return billRepository.save(updatedBill);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public BillEntity deleteBill(@PathVariable Integer id) {
		try {
			BillEntity bill = billRepository.findById(id).get();
			billRepository.delete(bill);
			return bill;
		} catch (Exception e) {
			return null;
		}
	}
	
	// T3 3.7
	/*
	 * Kreirati REST endpoint za pronalazak svih računa određenog kupca
	 * • putanja /project/bills/findByBuyer/{buyerId}
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/findByBuyer/{buyerId}")
	private Iterable<BillEntity> getBillsByUser(@PathVariable Integer buyerId) {
		try {
			return billRepository.findByUser(userRepository.findById(buyerId).get());
		} catch (Exception e) {
			return null;
		}
	}
	
	// T3 3.8
	/*
	 * Kreirati REST endpoint za pronalazak svih računa određene kategorije
	 * • putanja /project/bills/findByCategory/{categoryId}
	 */	
	@RequestMapping(method = RequestMethod.GET, path = "/findByCategory/{categoryId}")
	private Iterable<BillEntity> getBillsByCategory(@PathVariable Integer categoryId) {
		CategoryEntity category;
		try {
			category = categoryRepository.findById(categoryId).get();
		} catch (Exception e) {
			return null;
		}
		List<OfferEntity> offers = offerRepository.findByCategory(category);
		List<BillEntity> bills = billRepository.findByOfferIn(offers);
		return bills;
	}
	
	// T3 3.9
	/*
	 * Kreirati REST endpoint za pronalazak svih računa koji su kreirani u odgovarajućem vremenskom periodu
	 * • putanja /project/bills/findByDate/{startDate}/and/{endDate}
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/findByDate/{startDate}/and/{endDate}")
	private Iterable<BillEntity> getBillsByDate(@PathVariable String startDate,	@PathVariable String endDate) {
		// https://www.geeksforgeeks.org/localdatetime-parse-method-in-java-with-examples/
		// https://www.baeldung.com/java-datetimeformatter
		// TODO Smandrljano. Napraviti bolju logiku za parsiranje datuma.
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate sDate;
		LocalDate eDate;
		try {
			sDate = LocalDate.parse(startDate, formatter);
			eDate = LocalDate.parse(endDate, formatter);
		} catch (Exception e) {
			try {
				formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				sDate = LocalDate.parse(startDate, formatter);
				eDate = LocalDate.parse(endDate, formatter);
			} catch (Exception e2) {
				return null;
			}
		}
		try {
			return billRepository.findByBillCreatedBetween(sDate.atStartOfDay(), eDate.atStartOfDay().plusDays(1));
		} catch (Exception e) {
			return null;
		}
	}
}
