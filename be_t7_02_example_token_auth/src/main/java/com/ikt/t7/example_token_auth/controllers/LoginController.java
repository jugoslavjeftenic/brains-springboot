package com.ikt.t7.example_token_auth.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t7.example_token_auth.dto.UserDTO;
import com.ikt.t7.example_token_auth.entities.UserEntity;
import com.ikt.t7.example_token_auth.repositories.UserRepository;
import com.ikt.t7.example_token_auth.util.Encryption;

import io.jsonwebtoken.Jwts;

@RestController
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	
	@Autowired
	private SecretKey secretKey;
	
	private String getJWTToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities =
				AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());
		String token =
				Jwts.builder().setId("softtekJWT").setSubject(userEntity.getEmail())
				.claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList())).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenDuration))
				.signWith(secretKey).compact();
		return "Bearer " + token;
	}
	
	@PostMapping("api/v1//login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
		UserEntity user = userRepository.findByEmail(email);
		if (user != null && Encryption.validatePassword(password, user.getPassword())) {
			// 1 create token
			String token = getJWTToken(user);
			// 2 create response with UserDTO
			UserDTO retVal = new UserDTO(password, token);
			return new ResponseEntity<UserDTO>(retVal, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}
}
