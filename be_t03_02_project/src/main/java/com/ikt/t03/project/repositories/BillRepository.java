package com.ikt.t03.project.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t03.project.entites.BillEntity;
import com.ikt.t03.project.entites.OfferEntity;
import com.ikt.t03.project.entites.UserEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	// T3 3.2
	/*
	 * U paketu com.iktpreobuka.project.repositories napraviti interfejs BillRepository
	 */
	List<BillEntity> findByUser(UserEntity user);
	List<BillEntity> findByOffer(OfferEntity offer);
	List<BillEntity> findByOfferIn(List<OfferEntity> offers);
	List<BillEntity> findByBillCreatedBetween(LocalDateTime start, LocalDateTime end);
}
