package com.ikt.t3.example_data_layer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t3.example_data_layer.entities.CityEntity;
import com.ikt.t3.example_data_layer.entities.CountryEntity;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
	List<CityEntity> findByCity(String city);
	List<CityEntity> findByCountryIn(List<CountryEntity> countries);
	List<CityEntity> findByCityAndCountry(String city, CountryEntity country);
}
