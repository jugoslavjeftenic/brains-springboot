package com.ikt.t99.security.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	public static final String AUTHORITIES = "authorities";
	
	private SecretKey secretKey;
	
	public JWTAuthorizationFilter(SecretKey secretKey) {
		super();
		this.secretKey = secretKey;
	}

	private boolean checkIfJWTokenExist(HttpServletRequest request) {
		String authorizationHeaderValue = request.getHeader(HEADER);
		if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith(PREFIX)) {
			return false;
		}
		return true;
	}
	
	private Claims validateJWToken(HttpServletRequest request) {
		String JWToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(JWToken).getBody();
	}
	
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get(AUTHORITIES);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				claims.getSubject(),
				null,
				authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList())
		);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (checkIfJWTokenExist(request)) {
				Claims claims = validateJWToken(request);
				if (claims.get(AUTHORITIES) != null) {
					setUpSpringAuthentication(claims);
				}
				else {
					SecurityContextHolder.clearContext();
				}
			}
			else {
				SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}
}
