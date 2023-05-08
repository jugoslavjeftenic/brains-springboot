package com.ikt.t99.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t99.dtos.PredmetDTO;
import com.ikt.t99.repositories.PredmetRepository;
import com.ikt.t99.services.PredmetService;

@RestController
//@Secured("ULOGA_ADMINISTRATOR")
@RequestMapping(value = "/api/v1/predmeti")
@CrossOrigin(origins = "http://localhost:3000") 
public class PredmetController {

	private final Logger logger = LoggerFactory.getLogger(PredmetController.class);

	@Autowired
	private PredmetRepository predmetRepository;
	@Autowired
	private PredmetService predmetService;

	// Create
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody PredmetDTO predmetDTO) {
		logger.info("Pozvana metoda create() sa PredmetDTO objektom: {}", predmetDTO);
	    return predmetService.create(predmetDTO);
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		logger.info("Pozvana metoda read() sa id: {}", id);
	    return predmetService.read(id);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PredmetDTO predmetDTO) {
		logger.info("Pozvana metoda update() sa id: {} i PredmetDTO objektom: {}", id, predmetDTO);
	    return predmetService.update(id, predmetDTO);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("Pozvana metoda delete() sa id: {}", id);
		return predmetService.delete(id);
	}
	// ----------------------------------------------------------------------------------------------------

	// List
	@GetMapping
	public ResponseEntity<?> list() {
		logger.info("Pozvana metoda list()");
	    return ResponseEntity.status(HttpStatus.OK).body(predmetRepository.findAll());
	}
	
	// List by Naziv
	@GetMapping("/by-naziv/{naziv}")
	public ResponseEntity<?> listByIme(@PathVariable String naziv) {
		logger.info("Pozvana metoda listByIme() sa nazivom predmeta: {}", naziv);
	    return ResponseEntity.status(HttpStatus.OK).body(predmetRepository.findByNazivStartsWith(naziv));
	}
	
	// Create dummy data
	@PostMapping("/dummy")
	public ResponseEntity<?> dummy() {
		logger.info("Pozvana metoda dummy()");
	    return predmetService.dummy();
	}
}
