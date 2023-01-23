package com.ikt.t4.project.services;

import com.ikt.t4.project.entities.UserEntity;

public interface UserDAOService {
	
	public Iterable<UserEntity> generateRandomUsers(Integer count);
}
