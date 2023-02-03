package com.ikt.t7.example_basic_auth.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

	@Value("${spring.queries.users-query}")
	private String usersQuery;
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests().anyRequest().authenticated().and().httpBasic().authenticationEntryPoint(authenticationEntryPoint);
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
//		auth.inMemoryAuthentication().withUser("testuser").password("{noop}testpass").roles("admin");
		auth
			.jdbcAuthentication()
			.usersByUsernameQuery(this.usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(this.dataSource)
			.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
		return auth.build();
	}
}
