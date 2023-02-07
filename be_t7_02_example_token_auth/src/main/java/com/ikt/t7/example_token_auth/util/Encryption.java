package com.ikt.t7.example_token_auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

	public static String getEncodedPassword(String password) {
		BCryptPasswordEncoder bce = new BCryptPasswordEncoder();
		return bce.encode(password);
	}
	
	public static boolean validatePassword(String password, String encodedPassword) {
		BCryptPasswordEncoder bce = new BCryptPasswordEncoder();
		return bce.matches(password, encodedPassword);
	}
	
	public static void main(String[] args) {
		System.out.println(validatePassword("vladimir", "$2a$10$iZfUFktDF1CXpPqEC9AcsupjRERyeLOqCbf9.bdod0WxijxXAuAMy"));
//		System.out.println(getEncodedPassword("vladimir"));
		// $2a$10$iZfUFktDF1CXpPqEC9AcsupjRERyeLOqCbf9.bdod0WxijxXAuAMy
	}
}
