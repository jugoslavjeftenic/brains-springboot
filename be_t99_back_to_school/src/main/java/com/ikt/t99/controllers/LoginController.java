package com.ikt.t99.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t99.services.LoginService;

@RestController
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	
	@PostMapping(path = "/api/v1/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		logger.info("Pozvana metoda login() sa korisničkim imenom: {}", username);
		return loginService.checkAndLoginUser(username, password, session);
	}

	// Napravi Zelenog
	@PostMapping(path = "/api/v1/zeleni")
	public ResponseEntity<?> napraviZelenog() {
		logger.info("Pozvana metoda napraviZelenog()");
	    return loginService.napraviZelenog();
	}
}
