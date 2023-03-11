package com.ikt.t04.project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ikt.t04.project.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	Boolean existsByUserName(String userName);
	@Query("SELECT u.id FROM UserEntity u")
	Long[] findAllIds();
}
