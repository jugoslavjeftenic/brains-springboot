package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.UcenikDTO;

public interface UcenikService {

	public ResponseEntity<?> create(UcenikDTO ucenikDTO);											// C
	public ResponseEntity<?> read(Long id);															// R
	public ResponseEntity<?> update(Long id, UcenikDTO ucenikDTO);									// U
	public ResponseEntity<?> delete(Long id);														// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> dummy(Integer count);
}
