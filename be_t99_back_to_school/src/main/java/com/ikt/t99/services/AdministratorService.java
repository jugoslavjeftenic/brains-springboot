package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.AdministratorDTO;

public interface AdministratorService {

	public ResponseEntity<?> create(AdministratorDTO administratorDTO);								// C
	public ResponseEntity<?> read(Long id);															// R
	public ResponseEntity<?> update(Long id, AdministratorDTO administratorDTO);					// U
	public ResponseEntity<?> delete(Long id);														// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> dummy(Integer count);
	public ResponseEntity<?> downloadLog();
}
