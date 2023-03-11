package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.RoditeljDTO;

public interface RoditeljService {

	public ResponseEntity<?> create(RoditeljDTO roditeljDTO);										// C
	public ResponseEntity<?> read(Long id);															// R
	public ResponseEntity<?> update(Long id, RoditeljDTO roditeljDTO);								// U
	public ResponseEntity<?> delete(Long id);														// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> dummy(Integer count);
}
