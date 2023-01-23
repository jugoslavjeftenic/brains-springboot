package com.ikt.t4.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t4.project.entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

}
