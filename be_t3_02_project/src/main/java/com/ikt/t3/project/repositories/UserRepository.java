package com.ikt.t3.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ikt.t3.project.entites.EUserRole;
import com.ikt.t3.project.entites.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	
	// T3 1.2
	/*
	 * U okviru kreiranog projekta napraviti novi paket com.iktpreobuka.project.repositories
	 * • za svaku od kreiranih klasa napraviti odgovarajuće interfejse
	 * • UserRepository, CategoryRepository i OfferRepository
	 */
	List<UserEntity> findByUserName(String userName);
	List<UserEntity> findByUserRole(EUserRole userRole);
	UserEntity findByIdAndUserRole(Integer id, EUserRole userRole);
	// https://springframework.guru/spring-data-jpa-query/
	@Query("SELECT u.id FROM UserEntity u WHERE u.userRole = ?1")
	List<Integer> findAllIdsByUserRole(EUserRole userRole);
}
