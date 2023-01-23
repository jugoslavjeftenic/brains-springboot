package com.ikt.t4.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t4.project.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
