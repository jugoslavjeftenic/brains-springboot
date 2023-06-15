package com.ikt.t99.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.entities.NastavnikEntity;
import com.ikt.t99.entities.PredajeEntity;
import com.ikt.t99.entities.PredmetEntity;
import com.ikt.t99.repositories.NastavnikRepository;
import com.ikt.t99.repositories.PredajeRepository;
import com.ikt.t99.repositories.PredmetRepository;

import rade.RADE;

@Service
public class PredajeServiceImpl implements PredajeService {

	private final Logger logger = LoggerFactory.getLogger(PredajeServiceImpl.class);

	@Autowired
	private PredajeRepository predajeRepository;
	@Autowired
	private NastavnikRepository nastavnikRepository;
	@Autowired
	private PredmetRepository predmetRepository;

	@Override
	public ResponseEntity<?> create(Long nastavnikId, Long predmetId) {
		
		logger.info("Pozvan je metod create() sa objektom nastavnikId {} i predmetId {}", nastavnikId, predmetId);

		// Instanciram nastavnika i ispitujem da li postoji.
		NastavnikEntity nastavnikEntity = nastavnikRepository.findById(nastavnikId).orElse(null);
		if (nastavnikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema nastavnika sa prosleđenim ID-om.");
		}
		
		// Instanciram predmet i ispitujem da li postoji.
		PredmetEntity predmetEntity = predmetRepository.findById(predmetId).orElse(null);
		if (predmetEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema predmeta sa prosleđenim ID-om.");
		}
		
		// Instanciram predajeEntity i setujem vrednosti.
		PredajeEntity predajeEntity = new PredajeEntity();
		predajeEntity.setNastavnik(nastavnikEntity);
		predajeEntity.setPredmet(predmetEntity);
		
		// Ispitujem da li nastavnik vec predaje predmet.
		if (predajeRepository.existsByNastavnikAndPredmet(nastavnikEntity, predmetEntity)) {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body("Nastavnik je već registrovan za prosleđeni predmet.");
		}
		
		// Snimam u DB.
		try {
			predajeRepository.save(predajeEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(predajeEntity);
	}

	@Override
	public ResponseEntity<?> read(Long id) {
		
		logger.info("Pozvan je metod read() sa ID {}", id);

		// Instanciram predajeEntity ako postoji.
		PredajeEntity predajeEntity = predajeRepository.findById(id).orElse(null);
		if (predajeEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Traženi ID nije pronađen u DB-u.");
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(predajeEntity);
	}

	@Override
	public ResponseEntity<?> update(Long id, Long nastavnikId, Long predmetId) {

		logger.info("Pozvan je metod update() sa objektom nastavnikId {} i predmetId {}", nastavnikId, predmetId);

		// Instanciram predajeEntity ako postoji.
		PredajeEntity predajeEntity = predajeRepository.findById(id).orElse(null);
		if (predajeEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Traženi ID nije pronađen u DB-u.");
		}
		
		// Validiram prosledjene parametre i setujem nove vrednosti.
		if (nastavnikId != null && nastavnikId > 0) {
			NastavnikEntity nastavnikEntity = nastavnikRepository.findById(nastavnikId).orElse(null);
			if (nastavnikEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema nastavnika sa prosleđenim ID-om.");
			}
			predajeEntity.setNastavnik(nastavnikEntity);
		}
		if (predmetId != null && predmetId > 0) {
			PredmetEntity predmetEntity = predmetRepository.findById(predmetId).orElse(null);
			if (predmetEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema predmeta sa prosleđenim ID-om.");
			}
			predajeEntity.setPredmet(predmetEntity);
		}
		if (nastavnikId != null && predmetId != null) {
			if (predajeRepository.existsByNastavnikAndPredmet(predajeEntity.getNastavnik(), predajeEntity.getPredmet())) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Nastavnik je već registrovan za prosleđeni predmet.");
			}
		}

		// Snimam u DB.
		try {
			predajeRepository.save(predajeEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(predajeEntity);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {

		logger.info("Pozvan je metod delete() sa ID-om {}.", id);

		// Instanciram predajeEntity ako postoji.
		PredajeEntity predajeEntity = predajeRepository.findById(id).orElse(null);
		if (predajeEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Traženi ID nije pronađen u DB-u.");
		}

	    // Brisem iz DB-a.
	    try {
	    	predajeRepository.delete(predajeEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Podatak je pronađen ali se dogodila greška prilikom brisanja podataka u DB-u.");
		}

	    return ResponseEntity.status(HttpStatus.OK).body(predajeEntity);
	}
	// ----------------------------------------------------------------------------------------------------

	@Override
	public ResponseEntity<?> dummy(Integer count) {
		
		logger.info("Pozvana metoda dummy() sa brojem: {}", count);

		if (count < 1) {
			count = 1;
		}
		if (count > 1000) {
			count = 1000;
		}
		List<PredajeEntity> predajeList = new ArrayList<PredajeEntity>();
		List<NastavnikEntity> nastavnikList = (List<NastavnikEntity>) nastavnikRepository.findAll();
		List<PredmetEntity> predmetList = (List<PredmetEntity>) predmetRepository.findAll();
		for (int i = 0; i < count; i++) {
			PredajeEntity predajeEntity = new PredajeEntity();
			predajeEntity.setNastavnik(nastavnikList.get(RADE.mrRobot(0, nastavnikList.size())));
			predajeEntity.setPredmet(predmetList.get(RADE.mrRobot(0, predmetList.size())));
			if (predajeRepository.existsByNastavnikAndPredmet
					(predajeEntity.getNastavnik(), predajeEntity.getPredmet())) {
				continue;
			}
			try {
				predajeRepository.save(predajeEntity);
				predajeList.add(predajeEntity);
			} catch (Exception e) {
				logger.error("Došlo je do greške prilikom upisa podataka u DB: {}", e.getMessage());
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Došlo je do greške prilikom upisa podataka u DB.");
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(predajeList);
	}
}
