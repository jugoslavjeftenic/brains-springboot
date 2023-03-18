package com.ikt.t10_02.example_data_testing.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t10_02.example_data_testing.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	List<UserEntity> findByEmail(String email);
	List<UserEntity> findByNameOrderByEmailAsc(String name);
	List<UserEntity> findByBirthDateOrderByBirthDateAsc(LocalDate date);
	// https://www.baeldung.com/spring-jpa-like-queries
	List<UserEntity> findByNameStartsWith(String firstLetter);
}
