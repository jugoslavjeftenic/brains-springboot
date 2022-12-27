package com.iktprekvalifikacija.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.project.entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

}
