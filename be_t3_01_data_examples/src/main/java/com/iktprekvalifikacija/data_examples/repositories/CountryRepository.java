package com.iktprekvalifikacija.data_examples.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.CountryEntity;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {

}
