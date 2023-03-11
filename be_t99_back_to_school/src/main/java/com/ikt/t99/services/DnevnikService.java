package com.ikt.t99.services;

import org.springframework.http.ResponseEntity;

import com.ikt.t99.dtos.DnevnikDTO;

public interface DnevnikService {

	public ResponseEntity<?> create(DnevnikDTO dnevnikDTO);											// C
	public ResponseEntity<?> read(Long id);															// R
	public ResponseEntity<?> update(Long id, DnevnikDTO dnevnikDTO);								// U
	public ResponseEntity<?> delete(Long id);														// D
	// ----------------------------------------------------------------------------------------------------
	public ResponseEntity<?> listByParam(String datum, String datum_do,
			Long ucenik_id, Long predmet_id, Long nastavnik_id, Integer razred, Integer polugodiste, Integer ocena);
	public ResponseEntity<?> dummy(Integer count);
}
