package com.ikt.t4.project.services;

import com.ikt.t4.project.entities.OfferEntity;

public interface OfferDAOService {

	public Iterable<OfferEntity> generateListOfOffers(Integer count);
	public OfferEntity checkAndChangeOfferData(Long categoryId,  Long sellerId, OfferEntity offer);
}
