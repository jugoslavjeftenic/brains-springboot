package com.ikt.t99.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.DnevnikDTO;
import com.ikt.t99.dtos.DnevnikDTONastavnik;

public interface DnevnikServiceNastavnik {

	public ResponseEntity<?> create(HttpServletRequest request,
			DnevnikDTONastavnik dnevnikDTONastavnik);												// C
	public ResponseEntity<?> read(HttpServletRequest request, Long id);								// R
	public ResponseEntity<?> update(HttpServletRequest request, Long id, DnevnikDTO dnevnikDTO);	// U
	public ResponseEntity<?> delete(HttpServletRequest request, Long id);							// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> listByParam(HttpServletRequest request, String datum, String datum_do,
			Long ucenik_id, Long predmet_id, Integer razred, Integer polugodiste, Integer ocena);
}
