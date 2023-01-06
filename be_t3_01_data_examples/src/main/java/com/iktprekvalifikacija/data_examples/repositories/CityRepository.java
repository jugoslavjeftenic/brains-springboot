package com.iktprekvalifikacija.data_examples.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.CityEntity;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
	List<CityEntity> findByCity(String city);

}
