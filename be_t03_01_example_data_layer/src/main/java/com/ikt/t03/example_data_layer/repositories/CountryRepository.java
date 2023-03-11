package com.ikt.t03.example_data_layer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t03.example_data_layer.entities.CountryEntity;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
	List<CountryEntity> findByCountry(String country);
	List<CountryEntity> findByCountryInOrderByCountryAsc(List<String> countries);
}
