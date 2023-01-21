package com.ikt.t3.project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t3.project.entites.CategoryEntity;
import com.ikt.t3.project.entites.OfferEntity;
import com.ikt.t3.project.entites.UserEntity;

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
	List<OfferEntity> findByUser(UserEntity user);
	List<OfferEntity> findByCategory(CategoryEntity category);
}
