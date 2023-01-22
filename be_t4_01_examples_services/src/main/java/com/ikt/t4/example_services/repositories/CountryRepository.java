package com.ikt.t4.example_services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t4.example_services.entities.CountryEntity;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
	List<CountryEntity> findByCountry(String country);
	List<CountryEntity> findByCountryInOrderByCountryAsc(List<String> countries);
}
