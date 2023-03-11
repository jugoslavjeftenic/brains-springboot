package com.ikt.t04.project.services;

import com.ikt.t04.project.entities.OfferEntity;

public interface OfferDAOService {

	public Iterable<OfferEntity> generateListOfOffers(Integer count);
	public OfferEntity checkAndChangeOfferData(Long categoryId,  Long sellerId, OfferEntity offer);
}
