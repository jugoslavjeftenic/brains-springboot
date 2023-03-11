package com.ikt.t99.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t99.dtos.DnevnikDTO;
import com.ikt.t99.dtos.DnevnikDTONastavnik;
import com.ikt.t99.services.DnevnikServiceNastavnik;

@RestController
@Secured("ULOGA_NASTAVNIK")
@RequestMapping(value = "/api/v1/dnevnici/nastavnik")
public class DnevnikNastavnikController {

	private final Logger logger = LoggerFactory.getLogger(DnevnikNastavnikController.class);

	@Autowired
	private DnevnikServiceNastavnik dnevnikServiceNastavnik;

	// Create
	@PostMapping
	public ResponseEntity<?> create(HttpServletRequest request,
			@Valid @RequestBody DnevnikDTONastavnik dnevnikDTONastavnik) {
		logger.info("Pozvana metoda create() sa DnevnikDTONastavnik objektom: {}", dnevnikDTONastavnik);
	    return dnevnikServiceNastavnik.create(request, dnevnikDTONastavnik);
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read(HttpServletRequest request, @PathVariable Long id) {
		logger.info("Pozvana metoda read() sa id: {}", id);
	    return dnevnikServiceNastavnik.read(request, id);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(HttpServletRequest request,
			@PathVariable Long id, @RequestBody DnevnikDTO dnevnikDTO) {
		logger.info("Pozvana metoda update() sa id: {} i DnevnikDTO objektom: {}", id, dnevnikDTO);
	    return dnevnikServiceNastavnik.update(request, id, dnevnikDTO);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Long id) {
		logger.info("Pozvana metoda delete() sa id: {}", id);
		return dnevnikServiceNastavnik.delete(request, id);
	}
	// ----------------------------------------------------------------------------------------------------

	// List by params
	@GetMapping
	public ResponseEntity<?> listByParams(HttpServletRequest request,
			@RequestParam(required = false) String datum,
			@RequestParam(required = false) String datum_do,
			@RequestParam(required = false) Long ucenik_id,
			@RequestParam(required = false) Long predmet_id,
			@RequestParam(required = false) Integer razred,
			@RequestParam(required = false) Integer polugodiste,
			@RequestParam(required = false) Integer ocena) {
		logger.info("Pozvana metoda listByParams() sa parametrima datum: {}, datum_do: {}, "
				+ "ucenik_id: {}, predmet_id: {}, razred: {}, polugodiste: {}, ocena: {}",
				datum, datum_do, ucenik_id, predmet_id, razred, polugodiste, ocena);
		return dnevnikServiceNastavnik.listByParam(request, datum, datum_do, ucenik_id, predmet_id,
				razred, polugodiste, ocena);
	}
}
