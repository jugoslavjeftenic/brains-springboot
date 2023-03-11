package com.ikt.t99.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.KorisnikDTO;
import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.repositories.KorisnikRepository;

import rade.RADE;
import rade.entities.OsobaEntity;

@Service
public class KorisnikServiceImpl implements KorisnikService {

	private final Logger logger = LoggerFactory.getLogger(KorisnikServiceImpl.class);

	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public ResponseEntity<?> create(KorisnikDTO korisnikDTO) {
		
		logger.info("Pozvan je metod create() sa objektom KorisnikDTO {}", korisnikDTO);

		// Ispitujem da li vec postoji registrovan korisnik sa prosledjenim JMBG.
		List<KorisnikEntity> korisniciList = korisnikRepository.findByJmbg(korisnikDTO.getJmbg());
		if (korisniciList.size() > 0) {
			logger.info("Korisnik sa JMBG {} je već registrovan.", korisnikDTO.getJmbg());
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Korisnik sa prosleđenim JMBG je već registrovan.");
		}
		
		// Instanciram novog korisnika i setujem vrednosti.
		// Za validnost polja se brine @Valid anotacija.
		KorisnikEntity korisnikEntity = new KorisnikEntity();
		korisnikEntity.setJmbg(korisnikDTO.getJmbg());
		korisnikEntity.setIme(korisnikDTO.getIme());
		korisnikEntity.setPrezime(korisnikDTO.getPrezime());

		// Snimam u DB.
		try {
			korisnikRepository.save(korisnikEntity);
			logger.info("Korisnik je uspešno kreiran: {}", korisnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(korisnikEntity);
	}

	@Override
	public ResponseEntity<?> read(Long id) {
		
		logger.info("Pozvan je metod read() sa ID {}", id);

		// Instanciram korisnikEntity ako postoji.
		KorisnikEntity korisnikEntity = korisnikRepository.findById(id).orElse(null);
		if (korisnikEntity == null) {
			logger.warn("Korisnik sa ID {} nije pronađen u DB-u.", id);
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik nije pronađen u DB-u.");
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(korisnikEntity);
	}

	@Override
	public ResponseEntity<?> update(Long id, KorisnikDTO korisnikDTO) {

		logger.info("Pozvan je metod update() sa ID {} i objektom KorisnikDTO {}", id, korisnikDTO);

		// Ispitujem da li je korisnik sa prosledjenim ID-om registrovan.
		KorisnikEntity korisnikEntity = korisnikRepository.findById(id).orElse(null);
		if (korisnikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		if (korisnikDTO.getJmbg() != null && !korisnikDTO.getJmbg().equals(korisnikEntity.getJmbg())) {
			if (korisnikDTO.getJmbg().length() != 13) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("JMBG mora da ima tačno 13 karaktera.");
			}
			List<KorisnikEntity> korisniciListNoviJmbg = korisnikRepository.findByJmbg(korisnikDTO.getJmbg());
			if (korisniciListNoviJmbg.size() > 0) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("JMBG već postoji u DB-u.");
			}
			korisnikEntity.setJmbg(korisnikDTO.getJmbg());
		}
		if (korisnikDTO.getIme() != null) {
			if (korisnikDTO.getIme().length() < 2 || korisnikDTO.getIme().length() > 150) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Ime korisnika može imati najmanje 2 i naviše 150 karaktera.");
			}
			korisnikEntity.setIme(korisnikDTO.getIme());
		}
		if (korisnikDTO.getPrezime() != null) {
			if (korisnikDTO.getPrezime().length() < 2 || korisnikDTO.getPrezime().length() > 100) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Prezime korisnika može imati najmanje 2 i naviše 100 karaktera.");
			}
			korisnikEntity.setPrezime(korisnikDTO.getPrezime());
		}

		// Snimam u DB.
		try {
			korisnikRepository.save(korisnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(korisnikEntity);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {

		logger.info("Pozvan je metod delete() sa ID-om {}.", id);

		// Ispitujem da li je korisnik sa prosledjenim ID-om registrovan.
		KorisnikEntity korisnikEntity = korisnikRepository.findById(id).orElse(null);
		if (korisnikEntity == null) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Nema korisnika sa prosleđenim ID-om.");
		}

	    // Brisem iz DB-a.
	    try {
	    	korisnikRepository.delete(korisnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Korisnik je pronađen ali se dogodila neočekivana greška prilikom brisanja podataka u DB-u.");
		}

	    return ResponseEntity.status(HttpStatus.OK).body(korisnikEntity);
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
		List<KorisnikEntity> korisniciList = new ArrayList<KorisnikEntity>();
		for (int i = 0; i < count; i++) {
			KorisnikEntity korisnikEntity = new KorisnikEntity();
			OsobaEntity osobaEntity = RADE.generisiOsobu(6, 65, RADE.generisiPol());
			korisnikEntity.setJmbg(osobaEntity.getJmbg());
			korisnikEntity.setIme(osobaEntity.getIme());
			korisnikEntity.setPrezime(osobaEntity.getPrezime());
			korisniciList.add(korisnikEntity);
		}
		try {
			korisnikRepository.saveAll(korisniciList);
			logger.info("Korisnici su uspešno kreirani: {}", korisniciList);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB: {}", e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}
		
	    return ResponseEntity.status(HttpStatus.OK).body(korisniciList);
	}
}
