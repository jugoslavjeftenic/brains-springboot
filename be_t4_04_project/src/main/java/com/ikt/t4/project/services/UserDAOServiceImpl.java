package com.ikt.t4.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikt.t4.project.entities.EUserRole;
import com.ikt.t4.project.entities.UserEntity;
import com.ikt.t4.project.repositories.UserRepository;

import rade.RADE;
import rade.entities.OsobaEntity;

@Service
public class UserDAOServiceImpl implements UserDAOService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<UserEntity> generateRandomUsers(Integer count) {
		List<UserEntity> users = new ArrayList<>();
		EUserRole[] roles = EUserRole.values();
		for (int i = 0; i < count; i++) {
			OsobaEntity osoba = RADE.generisiOsobu();
			UserEntity user = new UserEntity();
			user.setFirstName(osoba.getIme());
			user.setLastName(osoba.getPrezime());
			user.setUsername(osoba.getUsername());
			if (userRepository.existsByUsername(user.getUsername())) {
				user.setUsername(changeUsername(user.getUsername()));
			}
			user.setPassword("1234");
			user.setEmail(user.getUsername() + "@ikt.rs");
			user.setUserRole(roles[RADE.mrRobot(0, roles.length)]);
			userRepository.save(user);
			users.add(user);
		}
		return users;
	}
	
	private String changeUsername(String user) {
		int i = 1;
		String userName = user + i;
		while (userRepository.existsByUsername(userName)) {
			i++;
			userName = user + i;
		}
		return userName;
	}
}
