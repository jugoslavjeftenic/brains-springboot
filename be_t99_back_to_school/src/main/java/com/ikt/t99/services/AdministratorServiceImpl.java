package com.ikt.t99.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.AdministratorDTO;
import com.ikt.t99.entities.AdministratorEntity;
import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.repositories.AdministratorRepository;
import com.ikt.t99.repositories.KorisnikRepository;
import com.ikt.t99.security.util.Encryption;

import rade.RADE;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	private final Logger logger = LoggerFactory.getLogger(AdministratorServiceImpl.class);

	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public ResponseEntity<?> create(AdministratorDTO administratorDTO) {
		
		logger.info("Pozvan je metod create() sa objektom AdministratorDTO {}", administratorDTO);

		// Instanciram korisnika i ispitujem da li postoji.
		KorisnikEntity korisnikEntity = korisnikRepository.findById(administratorDTO.getKorisnikId()).orElse(null);
		if (korisnikEntity == null) {
			logger.error("Nema korisnika sa prosleđenim ID-om: {}", administratorDTO.getKorisnikId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
		}

		// Ispitujem da li je korisnik vec registrovan kao administrator.
		if (administratorRepository.existsByKorisnik(korisnikEntity)) {
			logger.warn("Korisnik je već registrovan kao administrator: {}", korisnikEntity);
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Korisnik je već registrovan kao administrator.");
		}
		
		// Instanciram novog administratora i setujem vrednosti.
		// Za validnost polja se brine @Valid anotacija.
		AdministratorEntity administratorEntity = new AdministratorEntity();
		administratorEntity.setKorisnickoIme("adm." + administratorDTO.getKorisnickoIme());
		administratorEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(administratorDTO.getLozinka()));
		administratorEntity.setKorisnik(korisnikEntity);
		
		// Snimam u DB.
		try {
			administratorRepository.save(administratorEntity);
			logger.info("Administrator je uspešno kreiran: {}", administratorEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(administratorEntity);
	}

	@Override
	public ResponseEntity<?> read(Long id) {
		
		logger.info("Pozvan je metod read() sa ID {}", id);

		// Instanciram administratorEntity ako postoji.
		AdministratorEntity administratorEntity = administratorRepository.findById(id).orElse(null);
		if (administratorEntity == null) {
			logger.warn("Administrator sa ID {} nije pronađen u DB-u.", id);
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrator nije pronađen u DB-u.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(administratorEntity);
	}

	@Override
	public ResponseEntity<?> update(Long id, AdministratorDTO administratorDTO) {

		logger.info("Pozvan je metod update() sa ID {} i objektom AdministratorDTO {}", id, administratorDTO);
		
		// Ispitujem da li je administrator sa prosledjenim ID-om registrovan.
		AdministratorEntity administratorEntity = administratorRepository.findById(id).orElse(null);
		if (administratorEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema administratora sa prosleđenim ID-om.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		if (administratorDTO.getKorisnikId() != null &&
				!administratorDTO.getKorisnikId().equals(administratorEntity.getKorisnik().getKorisnik_id())) {
			KorisnikEntity korisnikEntity =
					korisnikRepository.findById(administratorDTO.getKorisnikId()).orElse(null);
			if (korisnikEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
			}
			if (administratorRepository.existsByKorisnik(korisnikEntity)) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnik je već registrovan kao administrator.");
			}
			administratorEntity.setKorisnik(korisnikEntity);
		}
		if (administratorDTO.getKorisnickoIme() != null &&
				!("adm." + administratorDTO.getKorisnickoIme()).equals(administratorEntity.getKorisnickoIme())) {
			if (administratorDTO.getKorisnickoIme().length() < 4 ||
					administratorDTO.getKorisnickoIme().length() > 16) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisničko ime može imati najmanje 4 i naviše 16 karaktera.");
			}
			if (administratorRepository.existsByKorisnickoIme("adm." + administratorDTO.getKorisnickoIme())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Korisničko ime je zauzeto.");
			}
			administratorEntity.setKorisnickoIme("adm." + administratorDTO.getKorisnickoIme());
		}
		if (administratorDTO.getLozinka() != null) {
			if (administratorDTO.getLozinka().length() < 6 ||
					administratorDTO.getLozinka().length() > 20) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnička lozinka može imati najmanje 6 i naviše 20 karaktera.");
			}
			if (!administratorDTO.getLozinka().equals(administratorDTO.getLozinkaPotvrda())) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Lozinka se ne poklapa sa potvrdom lozinke.");
			}
			administratorEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(administratorDTO.getLozinka()));
		}

		// Snimam u DB.
		try {
			administratorRepository.save(administratorEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(administratorEntity);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {

		logger.info("Pozvan je metod delete() sa ID-om {}.", id);
		
		// Ispitujem da li je administrator sa prosledjenim ID-om registrovan.
		AdministratorEntity administratorEntity = administratorRepository.findById(id).orElse(null);
		if (administratorEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema administratora sa prosleđenim ID-om.");
		}

	    // Brisem iz DB-a.
	    try {
	    	administratorRepository.delete(administratorEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Administrator je pronađen ali se dogodila neočekivana greška prilikom brisanja podataka u DB-u.");
		}

	    return ResponseEntity.status(HttpStatus.OK).body(administratorEntity);
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
		List<KorisnikEntity> korisniciList = korisnikRepository.findNoRole();
//		List<KorisnikEntity> korisniciList = korisnikRepository.findNotAdministrator();
		if (korisniciList.size() < count) {
			count = korisniciList.size();
		}
		List<AdministratorEntity> administratoriList = new ArrayList<AdministratorEntity>();
		for (int i = 0; i < count; i++) {
			AdministratorEntity administratorEntity = new AdministratorEntity();
			administratorEntity.setKorisnik(korisniciList.get(i));
			administratorEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword("lozinka"));
			String nadimak = RADE.replaceYULatinWithUS(RADE.generisiNadimak().toLowerCase().replace(" ", "-"));
			administratorEntity.setKorisnickoIme("adm." + nadimak.substring(0, Math.min(nadimak.length(), 16)));
			int stanislav = 1;
			if (administratorRepository.existsByKorisnickoIme(administratorEntity.getKorisnickoIme())) {
				String ime = administratorEntity.getKorisnik().getIme();
				nadimak = "adm." + RADE.replaceYULatinWithUS(ime.substring(0, Math.min(ime.length(), 10)) +
						administratorEntity.getKorisnik().getPrezime().substring(0, 2)).toLowerCase();
				administratorEntity.setKorisnickoIme(nadimak);
				while (true) {
					if (administratorRepository.existsByKorisnickoIme(administratorEntity.getKorisnickoIme())) {
						administratorEntity.setKorisnickoIme(nadimak + stanislav++);
					}
					else {
						break;
					}
				}
			}
			if (stanislav > 999) {
				System.out.println("Stanislave, preterano ser*!");
				continue;
			}
			administratoriList.add(administratorEntity);
			try {
				administratorRepository.save(administratorEntity);
				logger.info("Administrator je uspešno kreiran: {}", administratorEntity);
			} catch (Exception e) {
				logger.error("Došlo je do greške prilikom upisa podataka u DB: {}", e.getMessage());
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Došlo je do greške prilikom upisa podataka u DB.");
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(administratoriList);
	}

	@Override
	public ResponseEntity<?> downloadLog() {
		logger.info("Pozvana metoda downloadLog()");
	    File file = new File("logs/spring-boot-logging.log");
	    InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error("Doslo je do greske prilikom instanciranja log datoteke: {}", e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom instanciranja log datoteke: " + e.getMessage());
		}

	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = logfile.txt");

	    logger.info("Log fajl je uspesno preuzet.");
	    return ResponseEntity
	    		.status(HttpStatus.OK)
	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
}
