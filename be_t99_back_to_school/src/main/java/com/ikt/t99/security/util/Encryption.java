package com.ikt.t99.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

	public static String getEncodedPassword(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}
	
	public static boolean validatePassword(String password, String encodedPassword) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(password, encodedPassword.replace("{bcrypt}", ""));
	}
	
	public static void main(String[] args) {
		System.out.println("{bcrypt}" + getEncodedPassword("lozinka"));
//		{bcrypt}$2a$10$eJI/iLQvwJYzs15YrQEtGuXZ9HnmfZ1woBQJsEGUAr2wtkTMsqRMK
	}
}
