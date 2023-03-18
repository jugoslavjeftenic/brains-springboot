package com.ikt.t10_02.example_data_testing.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t10_02.example_data_testing.entities.CityEntity;
import com.ikt.t10_02.example_data_testing.entities.CountryEntity;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
	List<CityEntity> findByCity(String city);
	List<CityEntity> findByCountryIn(List<CountryEntity> countries);
	List<CityEntity> findByCityAndCountry(String city, CountryEntity country);
}
