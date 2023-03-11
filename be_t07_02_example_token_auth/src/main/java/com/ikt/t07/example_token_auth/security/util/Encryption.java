package com.ikt.t07.example_token_auth.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

	public static String getEncodedPassword(String password) {
		BCryptPasswordEncoder bce = new BCryptPasswordEncoder();
		return bce.encode(password);
	}
	
	public static boolean validatePassword(String password, String encodedPassword) {
		BCryptPasswordEncoder bce = new BCryptPasswordEncoder();
		encodedPassword = encodedPassword.replace("{bcrypt}", "");
		return bce.matches(password, encodedPassword);
	}
	
	public static void main(String[] args) {
		System.out.println(getEncodedPassword("lozinka"));
		System.out.println(validatePassword("lozinka", "{bcrypt}$2a$10$qBEgqy2ecxf28I2J48c5cu6Ln4xrrNH.7ihSfMQEwy9T68qcUSb36"));
		// $2a$10$qBEgqy2ecxf28I2J48c5cu6Ln4xrrNH.7ihSfMQEwy9T68qcUSb36
	}
}
