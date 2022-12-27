package com.iktprekvalifikacija.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktprekvalifikacija.project.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
