package com.ikt.project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.project.entities.OfferEntity;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer> {

	// T3 1.2
	/*
	 * U okviru kreiranog projekta napraviti novi paket com.iktpreobuka.project.repositories
	 * • za svaku od kreiranih klasa napraviti odgovarajuće interfejse
	 * • UserRepository, CategoryRepository i OfferRepository
	 */
	// https://www.baeldung.com/spring-data-derived-queries
	List<OfferEntity> findByOfferName(String offerName);
	List<OfferEntity> findByActionPriceBetween(Double start, Double end);
}
