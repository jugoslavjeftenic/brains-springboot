package com.iktprekvalifikacija.data_examples.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.data_examples.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
