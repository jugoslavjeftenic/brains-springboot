package com.ikt.t04.example_services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t04.example_services.entities.CityEntity;
import com.ikt.t04.example_services.entities.CountryEntity;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
	List<CityEntity> findByCity(String city);
	List<CityEntity> findByCountryIn(List<CountryEntity> countries);
	List<CityEntity> findByCityAndCountry(String city, CountryEntity country);
}
