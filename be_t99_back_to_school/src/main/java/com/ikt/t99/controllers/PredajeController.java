package com.ikt.t99.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t99.repositories.PredajeRepository;
import com.ikt.t99.services.PredajeService;

@RestController
@Secured("ULOGA_ADMINISTRATOR")
@RequestMapping(value = "/api/v1/predaju")
@CrossOrigin(origins = "*") 
public class PredajeController {

	private final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

	@Autowired
	private PredajeRepository predajeRepository;
	@Autowired
	private PredajeService predajeService;

	// Create
	@PostMapping
	public ResponseEntity<?> create(@RequestParam Long idNastavnik, @RequestParam Long idPredmet) {
		logger.info("Pozvana metoda create() sa idNastavnik: {} i idPredmet : {}", idNastavnik, idPredmet);
	    return predajeService.create(idNastavnik, idPredmet);
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		logger.info("Pozvana metoda read() sa id: {}", id);
	    return predajeService.read(id);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
			@RequestParam(required = false) Long idNastavnik, @RequestParam(required = false) Long idPredmet) {
		logger.info("Pozvana metoda update() sa idNastavnik: {} i idPredmet : {}", idNastavnik, idPredmet);
	    return predajeService.update(id, idNastavnik, idPredmet);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("Pozvana metoda delete() sa id: {}", id);
		return predajeService.delete(id);
	}
	// ----------------------------------------------------------------------------------------------------

	// List
	@GetMapping
	public ResponseEntity<?> list() {
		logger.info("Pozvana metoda list()");
	    return ResponseEntity.status(HttpStatus.OK).body(predajeRepository.findAll());
	}
	
	// Create dummy data
	@PostMapping("/dummy/{count}")
	public ResponseEntity<?> dummy(@PathVariable Integer count) {
		logger.info("Pozvana metoda dummy() sa brojem: {}", count);
	    return predajeService.dummy(count);
	}
}
