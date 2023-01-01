package com.iktprekvalifikacija.data_examples.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	List<UserEntity> findByEmailIgnoreCase(String email);
	List<UserEntity> findByNameIgnoreCaseOrderByEmailAsc(String name);
	List<UserEntity> findByBirthDateOrderByBirthDateAsc(LocalDate date);
}
