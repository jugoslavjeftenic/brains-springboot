package com.ikt.t4.project.services;

import com.ikt.t4.project.entities.UserEntity;

public interface UserDAOService {
	
	public Iterable<UserEntity> generateListOfUsers(Integer count);
	public UserEntity checkAndChangeUserData(UserEntity user);
	public UserEntity prepareToDelete(UserEntity user);
}
