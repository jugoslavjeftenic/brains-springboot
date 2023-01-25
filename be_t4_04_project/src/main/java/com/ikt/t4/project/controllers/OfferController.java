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

	// List all
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<OfferEntity> list() {
		// TODO Resiti problem prikaza kada je korisnik ili kategorija pobrisan.
	    return offerRepository.findAll();
	}
}
