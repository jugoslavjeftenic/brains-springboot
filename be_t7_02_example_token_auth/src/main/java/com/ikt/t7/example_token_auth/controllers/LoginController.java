package com.ikt.t7.example_token_auth.controllers;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

import com.ikt.t7.example_token_auth.dtos.LoginDTO;
import com.ikt.t7.example_token_auth.entities.UserEntity;
import com.ikt.t7.example_token_auth.repositories.UserRepository;
import com.ikt.t7.example_token_auth.security.util.Encryption;

import io.jsonwebtoken.Jwts;

@RestController
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecretKey secretKey;
	
	@Value("${spring.security.token-duration}")
	private Long tokenDuration;
	
	private String getJWToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities =
				AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());
		LocalDateTime currentTime = LocalDateTime.now();
		String token = Jwts.builder()
				.setId("softtekHWT")
				.setSubject(userEntity.getEmail())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(currentTime.plus(this.tokenDuration, ChronoUnit.MILLIS).atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(this.secretKey)
				.compact();
		return "Bearer " + token;
	}
	
	@PostMapping(path = "api/v1/login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
		UserEntity user = userRepository.findByEmail(email);
		if (user != null && Encryption.validatePassword(password, user.getPassword())) {
			// 1 create the token
			String token = getJWToken(user);
			// 2 create response with LoginDTO
			LoginDTO loginDTO = new LoginDTO(email, token);
			return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}
}
