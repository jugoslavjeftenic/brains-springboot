package com.ikt.t99.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t99.services.DnevnikServiceUcenik;

@RestController
@Secured("ULOGA_UCENIK")
@RequestMapping(value = "/api/v1/dnevnici/ucenik")
@CrossOrigin(origins = "*") 
public class DnevnikUcenikController {

	private final Logger logger = LoggerFactory.getLogger(DnevnikUcenikController.class);
	
	@Autowired
	private DnevnikServiceUcenik dnevnikUcenikService;

	// List by params
	@GetMapping
	public ResponseEntity<?> listByParams(HttpServletRequest request,
			@RequestParam(required = false) String datum,
			@RequestParam(required = false) String datum_do,
			@RequestParam(required = false) Long predmet_id,
			@RequestParam(required = false) Long nastavnik_id,
			@RequestParam(required = false) Integer razred,
			@RequestParam(required = false) Integer polugodiste,
			@RequestParam(required = false) Integer ocena) {
		logger.info("Pozvana metoda listByParams() sa parametrima datum: {}, datum_do: {}, "
				+ "predmet_id: {}, nastavnik_id: {}, razred: {}, polugodiste: {}, ocena: {}",
				datum, datum_do, predmet_id, nastavnik_id, razred, polugodiste, ocena);
		return dnevnikUcenikService.listByParam(request, datum, datum_do, predmet_id, nastavnik_id,
				razred,	polugodiste, ocena);
	}
}
