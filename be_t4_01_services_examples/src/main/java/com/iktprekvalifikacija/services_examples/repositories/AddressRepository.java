package com.iktprekvalifikacija.services_examples.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.services_examples.entities.AddressEntity;
import com.iktprekvalifikacija.services_examples.entities.CityEntity;
import com.iktprekvalifikacija.services_examples.entities.UserEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
	List<AddressEntity> findByStreet(String street);
	List<AddressEntity> findByCity(CityEntity city);
	// https://bushansirgur.in/spring-data-jpa-query-methods-in-with-examples/
	List<AddressEntity> findByCityIn(List<CityEntity> cities);
	List<AddressEntity> findByStreetAndCity(String street, CityEntity city);
	List<AddressEntity> findDistinctByUsersIn(List<UserEntity> users);
}
