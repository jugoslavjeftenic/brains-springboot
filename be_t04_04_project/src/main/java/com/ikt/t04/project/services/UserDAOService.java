package com.ikt.t04.project.services;

import com.ikt.t04.project.entities.UserEntity;

public interface UserDAOService {
	
	public Iterable<UserEntity> generateListOfUsers(Integer count);
	public UserEntity checkAndChangeUserData(UserEntity user);
}
