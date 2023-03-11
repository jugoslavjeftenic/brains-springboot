package com.ikt.t07.example_token_auth.security.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	private SecretKey secretKey;
	
	public WebSecurityConfig() {
		this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}
	
	@Bean
	SecretKey secretKey() {
		return this.secretKey;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf().disable()
			.addFilterAfter(new JWSAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
			.anyRequest().authenticated();
		return httpSecurity.build();
	}
}
