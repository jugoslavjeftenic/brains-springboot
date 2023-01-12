package com.iktprekvalifikacija.services_examples.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.services_examples.entities.CityEntity;
import com.iktprekvalifikacija.services_examples.entities.CountryEntity;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
	List<CityEntity> findByCity(String city);
	List<CityEntity> findByCountryIn(List<CountryEntity> countries);
	List<CityEntity> findByCityAndCountry(String city, CountryEntity country);
}
