package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

public interface PredajeService {

	public ResponseEntity<?> create(Long nastavnikId, Long predmetId);								// C
	public ResponseEntity<?> read(Long id);															// R
	public ResponseEntity<?> update(Long id, Long nastavnikId, Long predmetId);						// U
	public ResponseEntity<?> delete(Long id);														// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> dummy(Integer count);
}
