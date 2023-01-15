package com.ikt.project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.project.entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

	// T3 1.2
	/*
	 * U okviru kreiranog projekta napraviti novi paket com.iktpreobuka.project.repositories
	 * • za svaku od kreiranih klasa napraviti odgovarajuće interfejse
	 * • UserRepository, CategoryRepository i OfferRepository
	 */
	List<CategoryEntity> findByCategoryName(String categoryName);
}
