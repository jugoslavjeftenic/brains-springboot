package com.iktprekvalifikacija.services_examples.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.services_examples.entities.CountryEntity;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
	List<CountryEntity> findByCountry(String country);
	List<CountryEntity> findByCountryInOrderByCountryAsc(List<String> countries);
}
