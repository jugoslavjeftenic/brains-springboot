package com.ikt.t7.example_basic_auth.security.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void afterPropertiesSet() {
		this.setRealmName("DeveloperStack");
		super.afterPropertiesSet();
	}
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		// Set response realm header
		response.addHeader("WWW-Authenticate", "Basic realm = " + this.getRealmName());
		// Set response status
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		// Set response body
		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 - " + authException.getMessage());
	}
}
