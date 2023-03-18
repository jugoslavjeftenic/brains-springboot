package com.ikt.t10_02.example_data_testing.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t10_02.example_data_testing.entities.CountryEntity;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
	List<CountryEntity> findByCountry(String country);
	List<CountryEntity> findByCountryInOrderByCountryAsc(List<String> countries);
}
