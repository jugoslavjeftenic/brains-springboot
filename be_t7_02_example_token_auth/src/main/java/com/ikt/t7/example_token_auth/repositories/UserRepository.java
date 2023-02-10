package com.ikt.t7.example_token_auth.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t7.example_token_auth.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	public UserEntity findByEmail(String email);
}
