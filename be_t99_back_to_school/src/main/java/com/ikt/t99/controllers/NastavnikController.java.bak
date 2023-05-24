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
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t99.dtos.NastavnikDTO;
import com.ikt.t99.repositories.NastavnikRepository;
import com.ikt.t99.services.NastavnikService;

@RestController
@Secured("ULOGA_ADMINISTRATOR")
@RequestMapping(value = "/api/v1/nastavnici")
public class NastavnikController {

	private final Logger logger = LoggerFactory.getLogger(NastavnikController.class);

	@Autowired
	private NastavnikRepository nastavnikRepository;
	@Autowired
	private NastavnikService nastavnikService;

	// Create
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody NastavnikDTO nastavnikDTO) {
		logger.info("Pozvana metoda create() sa NastavnikDTO objektom: {}", nastavnikDTO);
	    return nastavnikService.create(nastavnikDTO);
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		logger.info("Pozvana metoda read() sa id: {}", id);
	    return nastavnikService.read(id);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody NastavnikDTO nastavnikDTO) {
		logger.info("Pozvana metoda update() sa id: {} i NastavnikDTO objektom: {}", id, nastavnikDTO);
	    return nastavnikService.update(id, nastavnikDTO);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("Pozvana metoda delete() sa id: {}", id);
		return nastavnikService.delete(id);
	}
	// ----------------------------------------------------------------------------------------------------

	// List
	@GetMapping
	public ResponseEntity<?> list() {
		logger.info("Pozvana metoda list()");
	    return ResponseEntity.status(HttpStatus.OK).body(nastavnikRepository.findAll());
	}
	
	// List by Korisnicko Ime
	@GetMapping("/by-korisnicko-ime/{ime}")
	public ResponseEntity<?> listByUsername(@PathVariable String ime) {
		logger.info("Pozvana metoda listByUsername() sa korisničkim imenom: {}", ime);
	    return ResponseEntity.status(HttpStatus.OK).body(nastavnikRepository.findByKorisnickoImeStartsWith(ime));
	}
	
	// Create dummy data
	@PostMapping("/dummy/{count}")
	public ResponseEntity<?> dummy(@PathVariable Integer count) {
		logger.info("Pozvana metoda dummy() sa brojem: {}", count);
	    return nastavnikService.dummy(count);
	}
}
