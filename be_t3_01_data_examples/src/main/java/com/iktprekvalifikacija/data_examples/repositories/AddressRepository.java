package com.iktprekvalifikacija.data_examples.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.AddressEntity;
import com.iktprekvalifikacija.data_examples.entities.CityEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
	List<AddressEntity> findByStreet(String street);
	List<AddressEntity> findByCity(CityEntity city);
	// https://bushansirgur.in/spring-data-jpa-query-methods-in-with-examples/
	List<AddressEntity> findByCityIn(List<CityEntity> cities);
	List<AddressEntity> findByStreetAndCity(String street, CityEntity city);
}
