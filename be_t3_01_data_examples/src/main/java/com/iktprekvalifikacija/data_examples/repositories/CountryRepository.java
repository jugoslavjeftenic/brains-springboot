package com.iktprekvalifikacija.data_examples.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.CountryEntity;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
	List<CountryEntity> findByCountry(String country);
}
