package com.ikt.t7.example_basic_auth.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptionUtil {

	public static String getEncryptedPassword(String password) {
		BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();
		return bcEncoder.encode(password);
	}
	
	public static void main(String[] args) {
		System.out.println(getEncryptedPassword("lozinka"));
	}
}
