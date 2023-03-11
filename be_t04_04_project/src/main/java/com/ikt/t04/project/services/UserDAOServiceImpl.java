package com.ikt.t04.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikt.t04.project.entities.EUserRole;
import com.ikt.t04.project.entities.UserEntity;
import com.ikt.t04.project.repositories.UserRepository;

import rade.RADE;
import rade.entities.OsobaEntity;

@Service
public class UserDAOServiceImpl implements UserDAOService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<UserEntity> generateListOfUsers(Integer count) {
		List<UserEntity> users = new ArrayList<>();
		EUserRole[] userRole = EUserRole.values();
		for (int i = 0; i < count; i++) {
			OsobaEntity osoba = RADE.generisiOsobu();
			UserEntity user = new UserEntity();
			user.setFirstName(osoba.getIme());
			user.setLastName(osoba.getPrezime());
			user.setUserName(osoba.getUserName());
			if (userRepository.existsByUserName(user.getUserName())) {
				user.setUserName(checkAndChangeUsername(user.getUserName()));
			}
			user.setPassword("1234");
			user.setEmail(user.getUserName() + "@ikt.rs");
			user.setUserRole(userRole[RADE.mrRobot(0, userRole.length)]);
			userRepository.save(user);
			users.add(user);
			System.out.println("row:" + i + ", " + user.getUserName());
		}
		return users;
	}

	@Override
	public UserEntity checkAndChangeUserData(UserEntity userToCheck) {
		UserEntity userToReturn = new UserEntity();
		if (userToCheck.getId() != null) {
			try {
				userToReturn = userRepository.findById(userToCheck.getId()).get();
			} catch (Exception e) {
				// TODO Vratiti gresku da nema korisnika u bazi.
				return null;
			}
		}
		if (userToCheck.getFirstName() != null) {
			userToReturn.setFirstName(userToCheck.getFirstName());
		}
		if (userToCheck.getLastName() != null) {
			userToReturn.setLastName(userToCheck.getLastName());
		}
		if (userToCheck.getUserName() != null) {
			userToReturn.setUserName(userToCheck.getUserName());
			if (userRepository.existsByUserName(userToReturn.getUserName())) {
				userToReturn.setUserName(checkAndChangeUsername(userToReturn.getUserName()));
			}
		}
		if (userToCheck.getPassword() != null) {
			userToReturn.setPassword(userToCheck.getPassword());
		}
		if (userToCheck.getEmail() != null) {
			userToReturn.setEmail(userToCheck.getEmail());
		}
		if (userToCheck.getUserRole() != null) {
			userToReturn.setUserRole(userToCheck.getUserRole());
		}
		return userToReturn;
	}
	
	private String checkAndChangeUsername(String user) {
		int i = 1;
		String userName = user + i;
		while (userRepository.existsByUserName(userName)) {
			i++;
			userName = user + i;
		}
		return userName;
	}
}
