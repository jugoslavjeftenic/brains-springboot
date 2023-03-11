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

import com.ikt.t99.dtos.AdministratorDTO;
import com.ikt.t99.repositories.AdministratorRepository;
import com.ikt.t99.services.AdministratorService;

@RestController
@Secured("ULOGA_ADMINISTRATOR")
@RequestMapping(value = "/api/v1/administratori")
public class AdministratorController {

	private final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private AdministratorService administratorService;

	// Create
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody AdministratorDTO administratorDTO) {
		logger.info("Pozvana metoda create() sa AdministratorDTO objektom: {}", administratorDTO);
		return administratorService.create(administratorDTO);
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		logger.info("Pozvana metoda read() sa id: {}", id);
	    return administratorService.read(id);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AdministratorDTO administratorDTO) {
		logger.info("Pozvana metoda update() sa id: {} i AdministratorDTO objektom: {}", id, administratorDTO);
	    return administratorService.update(id, administratorDTO);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("Pozvana metoda delete() sa id: {}", id);
		return administratorService.delete(id);
	}
	// ----------------------------------------------------------------------------------------------------

	// List
	@GetMapping
	public ResponseEntity<?> list() {
		logger.info("Pozvana metoda list()");
	    return ResponseEntity.status(HttpStatus.OK).body(administratorRepository.findAll());
	}
	
	// List by Korisnicko Ime
	@GetMapping("/by-korisnicko-ime/{ime}")
	public ResponseEntity<?> listByUsername(@PathVariable String ime) {
		logger.info("Pozvana metoda listByUsername() sa korisničkim imenom: {}", ime);
	    return ResponseEntity.status(HttpStatus.OK).body(administratorRepository.findByKorisnickoImeStartsWith(ime));
	}
	
	// Create dummy data
	@PostMapping("/dummy/{count}")
	public ResponseEntity<?> dummy(@PathVariable Integer count) {
		logger.info("Pozvana metoda dummy() sa brojem: {}", count);
	    return administratorService.dummy(count);
	}
	
	// Download Log
	@GetMapping("/log")
	public ResponseEntity<?> downloadLog() {
		logger.info("Pozvana metoda downloadLog()");
	    return administratorService.downloadLog();
	}
}
