package com.ikt.t03.example_data_layer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t03.example_data_layer.entities.AddressEntity;
import com.ikt.t03.example_data_layer.entities.CityEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
	List<AddressEntity> findByStreet(String street);
	List<AddressEntity> findByCity(CityEntity city);
	// https://bushansirgur.in/spring-data-jpa-query-methods-in-with-examples/
	List<AddressEntity> findByCityIn(List<CityEntity> cities);
	List<AddressEntity> findByStreetAndCity(String street, CityEntity city);
}
