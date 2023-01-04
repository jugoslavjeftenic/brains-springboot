package com.iktprekvalifikacija.data_examples.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
	List<AddressEntity> findByCity(String city);
	List<AddressEntity> findByCountryOrderByCountryAsc(String country);
	List<AddressEntity> findByStreetAndCityAndCountry(String street, String city, String country);
}
