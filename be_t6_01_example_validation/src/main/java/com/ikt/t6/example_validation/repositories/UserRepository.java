package com.ikt.t6.example_validation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t6.example_validation.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
