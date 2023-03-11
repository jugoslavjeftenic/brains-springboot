package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.KorisnikDTO;

public interface KorisnikService {


	public ResponseEntity<?> create(KorisnikDTO korisnikDTO);											// C
	public ResponseEntity<?> read(Long id);																// R
	public ResponseEntity<?> update(Long id, KorisnikDTO korisnikDTO);									// U
	public ResponseEntity<?> delete(Long id);															// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> dummy(Integer count);
}
