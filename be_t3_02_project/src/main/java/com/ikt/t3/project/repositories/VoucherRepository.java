package com.ikt.t3.project.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t3.project.entites.OfferEntity;
import com.ikt.t3.project.entites.UserEntity;
import com.ikt.t3.project.entites.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {

	// T3 4.2
	/*
	 * U paketu com.iktpreobuka.project.repositories napraviti interfejs VoucherRepository
	 */
	List<VoucherEntity> findByUser(UserEntity user);
	List<VoucherEntity> findByOffer(OfferEntity offer);
	List<VoucherEntity> findByExpirationDateGreaterThan(LocalDateTime date);
}
