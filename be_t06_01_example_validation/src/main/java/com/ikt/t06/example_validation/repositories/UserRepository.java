package com.ikt.t06.example_validation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t06.example_validation.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
