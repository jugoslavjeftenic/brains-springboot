package com.ikt.t04.example_services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t04.example_services.entities.AddressEntity;
import com.ikt.t04.example_services.entities.CityEntity;
import com.ikt.t04.example_services.entities.UserEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
	List<AddressEntity> findByStreet(String street);
	List<AddressEntity> findByCity(CityEntity city);
	// https://bushansirgur.in/spring-data-jpa-query-methods-in-with-examples/
	List<AddressEntity> findByCityIn(List<CityEntity> cities);
	List<AddressEntity> findByStreetAndCity(String street, CityEntity city);
	List<AddressEntity> findDistinctByUsersIn(List<UserEntity> users);
}
