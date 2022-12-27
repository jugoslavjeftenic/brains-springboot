package com.iktprekvalifikacija.data_examples.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {

}
