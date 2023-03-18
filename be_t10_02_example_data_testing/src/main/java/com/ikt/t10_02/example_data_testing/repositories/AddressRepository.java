package com.ikt.t10_02.example_data_testing.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t10_02.example_data_testing.entities.AddressEntity;
import com.ikt.t10_02.example_data_testing.entities.CityEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
	List<AddressEntity> findByStreet(String street);
	List<AddressEntity> findByCity(CityEntity city);
	// https://bushansirgur.in/spring-data-jpa-query-methods-in-with-examples/
	List<AddressEntity> findByCityIn(List<CityEntity> cities);
	List<AddressEntity> findByStreetAndCity(String street, CityEntity city);
}
