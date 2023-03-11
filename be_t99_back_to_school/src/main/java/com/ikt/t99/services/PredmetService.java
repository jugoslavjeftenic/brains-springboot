package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.PredmetDTO;

public interface PredmetService {

	public ResponseEntity<?> create(PredmetDTO predmetDTO);											// C
	public ResponseEntity<?> read(Long id);															// R
	public ResponseEntity<?> update(Long id, PredmetDTO predmetDTO);								// U
	public ResponseEntity<?> delete(Long id);														// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> dummy();
}
