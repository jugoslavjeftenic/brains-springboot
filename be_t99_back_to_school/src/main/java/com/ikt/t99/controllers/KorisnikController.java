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

import com.ikt.t99.dtos.KorisnikDTO;
import com.ikt.t99.repositories.KorisnikRepository;
import com.ikt.t99.services.KorisnikService;

@RestController
@Secured("ULOGA_ADMINISTRATOR")
@RequestMapping(value = "/api/v1/korisnici")
public class KorisnikController {

	private final Logger logger = LoggerFactory.getLogger(KorisnikController.class);

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private KorisnikService korisnikService;

	// Create
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody KorisnikDTO korisnikDTO) {
		logger.info("Pozvana metoda create() sa KorisnikDTO objektom: {}", korisnikDTO);
	    return korisnikService.create(korisnikDTO);
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		logger.info("Pozvana metoda read() sa id: {}", id);
	    return korisnikService.read(id);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody KorisnikDTO korisnikDTO) {
		logger.info("Pozvana metoda update() sa id: {} i KorisnikDTO objektom: {}", id, korisnikDTO);
	    return korisnikService.update(id, korisnikDTO);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("Pozvana metoda delete() sa id: {}", id);
		return korisnikService.delete(id);
	}
	// ----------------------------------------------------------------------------------------------------

	// List
	@GetMapping
	public ResponseEntity<?> list() {
		logger.info("Pozvana metoda list()");
	    return ResponseEntity.status(HttpStatus.OK).body(korisnikRepository.findAll());
	}
	
	// List by JMBG
	@GetMapping("/by-jmbg/{jmbg}")
	public ResponseEntity<?> listByJmbg(@PathVariable String jmbg) {
		logger.info("Pozvana metoda listByJmbg() sa korisničkim jmbg-om: {}", jmbg);
	    return ResponseEntity.status(HttpStatus.OK).body(korisnikRepository.findByJmbgStartsWith(jmbg));
	}
	
	// List by Ime
	@GetMapping("/by-ime/{ime}")
	public ResponseEntity<?> listByIme(@PathVariable String ime) {
		logger.info("Pozvana metoda listByIme() sa imenom: {}", ime);
	    return ResponseEntity.status(HttpStatus.OK).body(korisnikRepository.findByImeStartsWith(ime));
	}
	
	// List by Prezime
	@GetMapping("/by-prezime/{prezime}")
	public ResponseEntity<?> listByPrezime(@PathVariable String prezime) {
		logger.info("Pozvana metoda listByPrezime() sa prezimenom: {}", prezime);
		return ResponseEntity.status(HttpStatus.OK).body(korisnikRepository.findByPrezimeStartsWith(prezime));
	}

	// List users with no role
	@GetMapping("/norole")
	public ResponseEntity<?> listNoRole() {
		logger.info("Pozvana metoda listNoRole()");
	    return ResponseEntity.status(HttpStatus.OK).body(korisnikRepository.findNoRole());
	}
	
	// Create dummy data
	@PostMapping("/dummy/{count}")
	public ResponseEntity<?> dummy(@PathVariable Integer count) {
		logger.info("Pozvana metoda dummy() sa brojem: {}", count);
	    return korisnikService.dummy(count);
	}
}
