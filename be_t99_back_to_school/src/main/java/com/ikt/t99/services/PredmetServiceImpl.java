package com.ikt.t99.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.PredmetDTO;
import com.ikt.t99.entities.PredmetEntity;
import com.ikt.t99.repositories.PredmetRepository;

@Service
public class PredmetServiceImpl implements PredmetService {

	private final Logger logger = LoggerFactory.getLogger(PredmetServiceImpl.class);

	@Autowired
	private PredmetRepository predmetRepository;

	@Override
	public ResponseEntity<?> create(PredmetDTO predmetDTO) {
		
		logger.info("Pozvan je metod create() sa objektom PredmetDTO {}", predmetDTO);
		
		// Ispitujem da li predmet već postoji.
		if (predmetRepository.existsByNazivAndRazred(predmetDTO.getNaziv(), predmetDTO.getRazred())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Predmet je već registrovan.");
		}
		
		// Instanciram novi predmet i setujem vrednosti.
		// Za validnost polja se brine @Valid anotacija.
		PredmetEntity predmetEntity = new PredmetEntity();
		predmetEntity.setNaziv(predmetDTO.getNaziv());
		predmetEntity.setRazred(predmetDTO.getRazred());
		predmetEntity.setFondCasova(predmetDTO.getFondCasova());
		
		// Snimam u DB.
		try {
			predmetRepository.save(predmetEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(predmetEntity);
	}

	@Override
	public ResponseEntity<?> read(Long id) {
		
		logger.info("Pozvan je metod read() sa ID {}", id);

		// Instanciram predmetEntity ako postoji.
		PredmetEntity predmetEntity = predmetRepository.findById(id).orElse(null);
		if (predmetEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Predmet nije pronađen u DB-u.");
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(predmetEntity);
	}

	@Override
	public ResponseEntity<?> update(Long id, PredmetDTO predmetDTO) {

		logger.info("Pozvan je metod update() sa ID {} i objektom PredmetDTO {}", id, predmetDTO);
		
		// Instanciram predmetEntity ako postoji.
		PredmetEntity predmetEntity = predmetRepository.findById(id).orElse(null);
		if (predmetEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Predmet nije pronađen u DB-u.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		if (predmetDTO.getNaziv() != null && !predmetDTO.getNaziv().equals(predmetEntity.getNaziv())) {
			if (predmetDTO.getNaziv().length() < 4 || predmetDTO.getNaziv().length() > 100) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Naziv predmeta može imati najmanje 4 i naviše 100 karaktera.");
			}
			predmetEntity.setNaziv(predmetDTO.getNaziv());
		}
		if (predmetDTO.getRazred() != null && predmetDTO.getRazred() > 0 && predmetDTO.getRazred() < 9) {
			predmetEntity.setRazred(predmetDTO.getRazred());
		}
		if (predmetDTO.getFondCasova() != null && predmetDTO.getFondCasova() > 0) {
			predmetEntity.setFondCasova(predmetDTO.getFondCasova());
		}
		
		// Snimam u DB.
		try {
			predmetRepository.save(predmetEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(predmetEntity);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {

		logger.info("Pozvan je metod delete() sa ID-om {}.", id);

		// Ispitujem da li je predmet sa prosledjenim ID-om registrovan.
		PredmetEntity predmetEntity = predmetRepository.findById(id).orElse(null);
		if (predmetEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema predmeta sa prosleđenim ID-om.");
		}

	    // Brisem iz DB-a.
	    try {
	    	predmetRepository.delete(predmetEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Predmet je pronađen ali se dogodila neočekivana greška prilikom brisanja podataka u DB-u.");
		}

	    return ResponseEntity.status(HttpStatus.OK).body(predmetEntity);
	}
	// ----------------------------------------------------------------------------------------------------

	@Override
	public ResponseEntity<?> dummy() {
		
		logger.info("Pozvana metoda dummy()");

		String [][] predmeti = {
				{"Srpski jezik", "1", "5"},
				{"Strani jezik kao nematernji", "1", "2"},
				{"Matematika", "1", "5"},
				{"Svet oko nas", "1", "2"},
				{"Likovna kultura", "1", "1"},
				{"Muzicka kultura", "1", "1"},
				{"Fizicko i zdravstveno vaspitanje", "1", "3"},
				{"Digitalni svet", "1", "1"},
		        {"Verska nastava/građansko vaspitanje", "1", "1"},
				
				{"Srpski jezik", "2", "5"},
				{"Strani jezik kao nematernji", "2", "2"},
				{"Matematika", "2", "5"},
				{"Svet oko nas", "2", "2"},
				{"Likovna kultura", "2", "2"},
				{"Muzicka kultura", "2", "1"},
				{"Fizicko i zdravstveno vaspitanje", "2", "3"},
				{"Digitalni svet", "2", "1"},
		        {"Verska nastava/građansko vaspitanje", "2", "1"},
				
				{"Srpski jezik", "3", "5"},
				{"Strani jezik kao nematernji", "3", "2"},
				{"Matematika", "3", "5"},
				{"Priroda i društvo", "3", "2"},
				{"Likovna kultura", "3", "2"},
				{"Muzicka kultura", "3", "1"},
				{"Fizicko i zdravstveno vaspitanje", "3", "3"},
				{"Digitalni svet", "3", "1"},
		        {"Verska nastava/građansko vaspitanje", "3", "1"},
				
		        {"Srpski jezik", "4", "5"},
		        {"Strani jezik kao nematernji", "4", "2"},
		        {"Matematika", "4", "5"},
		        {"Priroda i društvo", "4", "2"},
		        {"Likovna kultura", "4", "2"},
		        {"Muzicka kultura", "4", "1"},
		        {"Fizicko i zdravstveno vaspitanje", "4", "3"},
		        {"Verska nastava/građansko vaspitanje", "4", "1"},
		        
				{"Srpski jezik", "5", "5"},
				{"Srpski jezik kao nematernji jezik", "5", "3"},
				{"Strani jezik", "5", "2"},
				{"Likovna kultura", "5", "2"},
				{"Muzička kultura", "5", "2"},
				{"Istorija", "5", "1"},
				{"Geografija", "5", "1"},
				{"Matematika", "5", "4"},
				{"Informatika i računarstvo", "5", "1"},
				{"Biologija", "5", "2"},
				{"Tehnika i tehnologija", "5", "2"},
				{"Fizičko i zdravstveno vaspitanje", "5", "2"},
		        {"Verska nastava/građansko vaspitanje", "5", "1"},
		        {"Maternji jezik sa elementima nacionalne kulture", "5", "2"},
				
		        {"Srpski jezik", "6", "4"},
		        {"Srpski jezik kao nematernji jezik", "6", "3"},
		        {"Strani jezik", "6", "2"},
		        {"Likovna kultura", "6", "1"},
		        {"Muzička kultura", "6", "1"},
		        {"Istorija", "6", "2"},
		        {"Geografija", "6", "2"},
		        {"Fizika", "6", "2"},
		        {"Matematika", "6", "4"},
		        {"Informatika i računarstvo", "6", "1"},
		        {"Biologija", "6", "2"},
		        {"Tehnika i tehnologija", "6", "2"},
		        {"Fizičko i zdravstveno vaspitanje", "6", "2"},
		        {"Verska nastava/građansko vaspitanje", "6", "1"},
		        {"Maternji jezik sa elementima nacionalne kulture", "6", "2"},
		        
		        {"Srpski jezik", "7", "4"},
		        {"Srpski jezik kao nematernji jezik", "7", "3"},
		        {"Strani jezik", "7", "2"},
		        {"Likovna kultura", "7", "1"},
		        {"Muzička kultura", "7", "1"},
		        {"Istorija", "7", "2"},
		        {"Geografija", "7", "2"},
		        {"Fizika", "7", "2"},
		        {"Matematika", "7", "4"},
		        {"Informatika i računarstvo", "7", "1"},
		        {"Biologija", "7", "2"},
		        {"Hemija", "7", "2"},
		        {"Tehnika i tehnologija", "7", "2"},
		        {"Fizičko i zdravstveno vaspitanje", "7", "3"},
		        {"Verska nastava/građansko vaspitanje", "7", "1"},
		        {"Maternji jezik sa elementima nacionalne kulture", "7", "2"},
		        
		        {"Srpski jezik", "8", "4"},
		        {"Srpski jezik kao nematernji jezik", "8", "2"},
		        {"Strani jezik", "8", "2"},
		        {"Likovna kultura", "8", "1"},
		        {"Muzička kultura", "8", "1"},
		        {"Istorija", "8", "2"},
		        {"Geografija", "8", "2"},
		        {"Fizika", "8", "2"},
		        {"Matematika", "8", "4"},
		        {"Informatika i računarstvo", "8", "1"},
		        {"Biologija", "8", "2"},
		        {"Hemija", "8", "2"},
		        {"Tehnika i tehnologija", "8", "2"},
		        {"Fizičko i zdravstveno vaspitanje", "8", "3"},
		        {"Verska nastava/građansko vaspitanje", "8", "1"},
		        {"Maternji jezik sa elementima nacionalne kulture", "8", "2"}
		};
		List<PredmetEntity> predmetList = new ArrayList<PredmetEntity>();
		for (int i = 0; i < predmeti.length; i++) {
			PredmetEntity predmetEntity = new PredmetEntity();
			if (predmetRepository.existsByNazivAndRazred(predmeti[i][0], Integer.valueOf(predmeti[i][1]))) {
				continue;
			}
			else {
				predmetEntity.setNaziv(predmeti[i][0]);
				predmetEntity.setRazred(Integer.valueOf(predmeti[i][1]));
				predmetEntity.setFondCasova(Integer.valueOf(predmeti[i][2]));
				predmetList.add(predmetEntity);
			}
		}
		try {
			predmetRepository.saveAll(predmetList);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB: {}", e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Dogodila se greška prilikom snimanja generisanih podataka u DB.");
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(predmetList);
	}
}
