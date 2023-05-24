package com.ikt.t99.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.ikt.t99.repositories.DnevnikRepository;
import com.ikt.t99.services.DnevnikService;

@RestController
@Secured("ULOGA_ADMINISTRATOR")
@RequestMapping(value = "/api/v1/dnevnici")
public class DnevnikController {

	private final Logger logger = LoggerFactory.getLogger(DnevnikController.class);

	@Autowired
	private DnevnikRepository dnevnikRepository;
	@Autowired
	private DnevnikService dnevnikService;

	// Create
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody DnevnikDTO dnevnikDTO) {
		logger.info("Pozvana metoda create() sa DnevnikDTO objektom: {}", dnevnikDTO);
	    return dnevnikService.create(dnevnikDTO);
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		logger.info("Pozvana metoda read() sa id: {}", id);
	    return dnevnikService.read(id);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DnevnikDTO dnevnikDTO) {
		logger.info("Pozvana metoda update() sa id: {} i DnevnikDTO objektom: {}", id, dnevnikDTO);
	    return dnevnikService.update(id, dnevnikDTO);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("Pozvana metoda delete() sa id: {}", id);
		return dnevnikService.delete(id);
	}
	// ----------------------------------------------------------------------------------------------------

	// List
	@GetMapping
	public ResponseEntity<?> list() {
		logger.info("Pozvana metoda list()");
	    return ResponseEntity.status(HttpStatus.OK).body(dnevnikRepository.findAll());
	}

	// List by params
	@GetMapping("/by-params")
	public ResponseEntity<?> listByParams(
			@RequestParam(required = false) String datum,
			@RequestParam(required = false) String datum_do,
			@RequestParam(required = false) Long ucenik_id,
			@RequestParam(required = false) Long predmet_id,
			@RequestParam(required = false) Long nastavnik_id,
			@RequestParam(required = false) Integer razred,
			@RequestParam(required = false) Integer polugodiste,
			@RequestParam(required = false) Integer ocena) {
		logger.info("Pozvana metoda listByParams() sa parametrima datum: {}, datum_do: {}, "
				+ "ucenik_id: {}, predmet_id: {}, nastavnik_id: {}, razred: {}, polugodiste: {}, ocena: {}",
				datum, datum_do, ucenik_id, predmet_id, nastavnik_id, razred, polugodiste, ocena);
		return dnevnikService.listByParam(datum, datum_do, ucenik_id, predmet_id, nastavnik_id,
				razred, polugodiste, ocena);
	}
	
	// Create dummy data
	@PostMapping("/dummy/{count}")
	public ResponseEntity<?> dummy(@PathVariable Integer count) {
		logger.info("Pozvana metoda dummy() sa brojem: {}", count);
	    return dnevnikService.dummy(count);
	}
}
