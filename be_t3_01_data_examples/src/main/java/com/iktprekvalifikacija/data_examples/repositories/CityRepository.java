package com.iktprekvalifikacija.data_examples.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.CityEntity;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {

}
