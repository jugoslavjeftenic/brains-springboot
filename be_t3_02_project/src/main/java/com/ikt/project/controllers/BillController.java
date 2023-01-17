package com.ikt.project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.project.entities.BillEntity;
import com.ikt.project.repositories.BillRepository;
import com.ikt.project.repositories.OfferRepository;
import com.ikt.project.repositories.UserRepository;

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

	@RequestMapping(method = RequestMethod.POST, path = "/{offerId}/buyer/{buyerId}")
	public BillEntity addBill(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@RequestParam Boolean paymentMade, @RequestParam Boolean paymentCanceled) {
		try {
			BillEntity newBill = new BillEntity();
			newBill.setPaymentMade(paymentMade);
			newBill.setPaymentCanceled(paymentCanceled);
			newBill.setBillCreated(LocalDateTime.now());
			newBill.setOffer(offerRepository.findById(offerId).get());
			newBill.setUser(userRepository.findById(buyerId).get());
			return billRepository.save(newBill);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<BillEntity> populateTable(@PathVariable Integer count) {
		// TODO Treba prepraviti da vraca ID-ove rekorda a ne count.
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
	
	// TODO ovde sam stao
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public BillEntity updateBill(@PathVariable Integer id, @RequestBody BillEntity updatedBill) {
		if (!offerRepository.existsById(id)) {
			return null;
		}
		if (!offerRepository.existsById(updatedBill.getOffer().getId())) {
			return null;
		}
		if (!userRepository.existsById(updatedBill.getUser().getId())) {
			return null;
		}
		
		return billRepository.save(updatedBill);
	}
}
