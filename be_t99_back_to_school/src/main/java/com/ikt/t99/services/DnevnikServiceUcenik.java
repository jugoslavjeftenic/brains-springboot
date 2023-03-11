package com.ikt.t99.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface DnevnikServiceUcenik {

	public ResponseEntity<?> listByParam(HttpServletRequest request, String datum, String datum_do,
			Long predmet_id, Long nastavnik_id, Integer razred, Integer polugodiste, Integer ocena);
}
