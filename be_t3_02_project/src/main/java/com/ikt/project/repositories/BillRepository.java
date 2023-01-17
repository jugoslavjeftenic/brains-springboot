package com.ikt.project.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.project.entities.BillEntity;
import com.ikt.project.entities.OfferEntity;
import com.ikt.project.entities.UserEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	// T3 3.2
	/*
	 * U paketu com.iktpreobuka.project.repositories napraviti interfejs BillRepository
	 */
	List<BillEntity> findByUser(UserEntity user);
	List<BillEntity> findByOfferIn(List<OfferEntity> offers);
	List<BillEntity> findByBillCreatedBetween(LocalDateTime start, LocalDateTime end);
}
