package com.ikt.t4.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ikt.t4.project.entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

	Boolean existsByCategoryName(String categoryName);
	List<CategoryEntity> findByCategoryName(String categoryName);
	@Query("SELECT c.id FROM CategoryEntity c")
	Long[] findAllIds();
}
