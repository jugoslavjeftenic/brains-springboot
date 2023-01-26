package com.ikt.t4.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t4.project.entities.OfferEntity;
import com.ikt.t4.project.repositories.OfferRepository;
import com.ikt.t4.project.services.OfferDAOService;

@RestController
@RequestMapping(value = "/api/v1/offers")
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private OfferDAOService offerService;

	// Create
	@RequestMapping(method = RequestMethod.POST, path = "/{categoryId}/seller/{sellerId}")
	public OfferEntity create(@PathVariable Long categoryId, @PathVariable Long sellerId,
			@RequestBody OfferEntity offer) {
	    return offerRepository.save(offerService.checkAndChangeOfferData(categoryId, sellerId, offer));
	}

	// Read
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public OfferEntity read(@PathVariable Long id) {
	    return offerRepository.findById(id).orElse(null);
	}

	// Update
	@RequestMapping(method = RequestMethod.PUT, value = {"/{id}", "/{id}/category/{categoryId}"})
	public OfferEntity update(@PathVariable Long id, @PathVariable(required = false) Long categoryId,
			@RequestBody OfferEntity offer) {
	    offer.setId(id);
		// TODO Vratiti odgovarajucu gresku kada id-a nema u bazi.
	    return offerRepository.save(offerService.checkAndChangeOfferData(categoryId, null, offer));
	}

	// Soft Delete
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public OfferEntity deleteSoft(@PathVariable Long id) {
		OfferEntity offerToSoftDelete = offerRepository.findById(id).get();
		// TODO Vratiti odgovarajucu gresku ako nema usera sa id-om (NoSuchElementException)
		offerRepository.deleteById(id);
		offerToSoftDelete.setDeleted(true);
	    return offerToSoftDelete;
	}

	// List all
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<OfferEntity> list() {
		// TODO Resiti problem prikaza kada je korisnik ili kategorija pobrisan.
	    return offerRepository.findAll();
	}
}
