package com.ikt.project.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.project.entities.BillEntity;
import com.ikt.project.entities.CategoryEntity;
import com.ikt.project.entities.EUserRole;
import com.ikt.project.entities.OfferEntity;
import com.ikt.project.entities.UserEntity;
import com.ikt.project.repositories.BillRepository;
import com.ikt.project.repositories.OfferRepository;
import com.ikt.project.repositories.UserRepository;

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
			@RequestBody BillEntity newBill) {
		try {
			newBill.setBillCreated(LocalDateTime.now());
			OfferEntity offer = offerRepository.findById(offerId).get();
			newBill.setOffer(offer);
			UserEntity user = userRepository.findByIdAndUserRole(buyerId, EUserRole.ROLE_CUSTOMER);
			newBill.setUser(user);
			return billRepository.save(newBill);
		} catch (Exception e) {
			return null;
		}
	}

	// TODO ovde sam stao
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public BillEntity updateBill(@PathVariable Integer id, @RequestBody BillEntity updatedBill) {
		try {
			OfferEntity offer = offerRepository.findById(updatedBill.getOffer().getId()).get();
			UserEntity user = userRepository.findById(updatedBill.getUser().getId()).get();
			BillEntity bill = billRepository.findById(id).get();
			
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
//				// https://www.baeldung.com/java-comparing-dates
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
		} catch (Exception e) {
			return null;
		}
	}
}
