package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.NastavnikDTO;

public interface NastavnikService {

	public ResponseEntity<?> create(NastavnikDTO nastavnikDTO);										// C
	public ResponseEntity<?> read(Long id);															// R
	public ResponseEntity<?> update(Long id, NastavnikDTO nastavnikDTO);							// U
	public ResponseEntity<?> delete(Long id);														// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> dummy(Integer count);
}
