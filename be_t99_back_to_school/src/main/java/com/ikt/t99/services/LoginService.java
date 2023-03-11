package com.ikt.t99.services;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

public interface LoginService {

	public ResponseEntity<?> checkAndLoginUser(String username, String password, HttpSession session);
	public ResponseEntity<?> napraviZelenog();
}
