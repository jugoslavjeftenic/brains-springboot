package com.ikt.t99.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.NastavnikDTO;
import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.entities.NastavnikEntity;
import com.ikt.t99.repositories.KorisnikRepository;
import com.ikt.t99.repositories.NastavnikRepository;
import com.ikt.t99.security.util.Encryption;

import rade.RADE;

@Service
public class NastavnikServiceImpl implements NastavnikService {

	private final Logger logger = LoggerFactory.getLogger(NastavnikServiceImpl.class);

	@Autowired
	private NastavnikRepository nastavnikRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public ResponseEntity<?> create(NastavnikDTO nastavnikDTO) {
		
		logger.info("Pozvan je metod create() sa objektom NastavnikDTO {}", nastavnikDTO);

		// Instanciram korisnika i ispitujem da li postoji.
		KorisnikEntity korisnikEntity = korisnikRepository.findById(nastavnikDTO.getKorisnikId()).orElse(null);
		if (korisnikEntity == null) {
			logger.error("Nema korisnika sa prosleđenim ID-om: {}", nastavnikDTO.getKorisnikId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
		}

		// Ispitujem da li je korisnik vec registrovan kao nastavnik.
		if (nastavnikRepository.existsByKorisnik(korisnikEntity)) {
			logger.warn("Korisnik je već registrovan kao nastavnik: {}", korisnikEntity);
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Korisnik je već registrovan kao nastavnik.");
		}
		
		// Instanciram novog nastavnika i setujem vrednosti.
		// Za validnost polja se brine @Valid anotacija.
		NastavnikEntity nastavnikEntity = new NastavnikEntity();
		nastavnikEntity.setKorisnickoIme("nst." + nastavnikDTO.getKorisnickoIme());
		nastavnikEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(nastavnikDTO.getLozinka()));
		nastavnikEntity.setKorisnik(korisnikEntity);
		
		// Snimam u DB.
		try {
			nastavnikRepository.save(nastavnikEntity);
			logger.info("Nastavnik je uspešno kreiran: {}", nastavnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(nastavnikEntity);
	}

	@Override
	public ResponseEntity<?> read(Long id) {
		
		logger.info("Pozvan je metod read() sa ID {}", id);

		// Instanciram nastavnikEntity ako postoji.
		NastavnikEntity nastavnikEntity = nastavnikRepository.findById(id).orElse(null);
		if (nastavnikEntity == null) {
			logger.warn("Nastavnik sa ID {} nije pronađen u DB-u.", id);
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nastavnik nije pronađen u DB-u.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(nastavnikEntity);
	}

	@Override
	public ResponseEntity<?> update(Long id, NastavnikDTO nastavnikDTO) {

		logger.info("Pozvan je metod update() sa ID {} i objektom NastavnikDTO {}", id, nastavnikDTO);

		// Ispitujem da li je nastavnik sa prosledjenim ID-om registrovan.
		NastavnikEntity nastavnikEntity = nastavnikRepository.findById(id).orElse(null);
		if (nastavnikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema nastavnika sa prosleđenim ID-om.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		if (nastavnikDTO.getKorisnikId() != null &&
				!nastavnikDTO.getKorisnikId().equals(nastavnikEntity.getKorisnik().getKorisnik_id())) {
			KorisnikEntity korisnikEntity =
					korisnikRepository.findById(nastavnikDTO.getKorisnikId()).orElse(null);
			if (korisnikEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
			}
			if (nastavnikRepository.existsByKorisnik(korisnikEntity)) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnik je već registrovan kao nastavnik.");
			}
			nastavnikEntity.setKorisnik(korisnikEntity);
		}
		if (nastavnikDTO.getKorisnickoIme() != null &&
				!("nst." + nastavnikDTO.getKorisnickoIme()).equals(nastavnikEntity.getKorisnickoIme())) {
			if (nastavnikDTO.getKorisnickoIme().length() < 4 ||
					nastavnikDTO.getKorisnickoIme().length() > 16) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisničko ime može imati najmanje 4 i naviše 16 karaktera.");
			}
			if (nastavnikRepository.existsByKorisnickoIme("nst." + nastavnikDTO.getKorisnickoIme())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Korisničko ime je zauzeto.");
			}
			nastavnikEntity.setKorisnickoIme("nst." + nastavnikDTO.getKorisnickoIme());
		}
		if (nastavnikDTO.getLozinka() != null) {
			if (nastavnikDTO.getLozinka().length() < 6 ||
					nastavnikDTO.getLozinka().length() > 20) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnička lozinka može imati najmanje 6 i naviše 20 karaktera.");
			}
			if (!nastavnikDTO.getLozinka().equals(nastavnikDTO.getLozinkaPotvrda())) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Lozinka se ne poklapa sa potvrdom lozinke.");
			}
			nastavnikEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(nastavnikDTO.getLozinka()));
		}

		// Snimam u DB.
		try {
			nastavnikRepository.save(nastavnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(nastavnikEntity);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {

		logger.info("Pozvan je metod delete() sa ID-om {}.", id);

		// Ispitujem da li je nastavnik sa prosledjenim ID-om registrovan.
		NastavnikEntity nastavnikEntity = nastavnikRepository.findById(id).orElse(null);
		if (nastavnikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema nastavnika sa prosleđenim ID-om.");
		}

	    // Brisem iz DB-a.
	    try {
	    	nastavnikRepository.delete(nastavnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Nastavnik je pronađen ali se dogodila neočekivana greška prilikom brisanja podataka u DB-u.");
		}

	    return ResponseEntity.status(HttpStatus.OK).body(nastavnikEntity);
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
//		List<KorisnikEntity> korisniciList = korisnikRepository.findNotNastavnik();
		if (korisniciList.size() < count) {
			count = korisniciList.size();
		}
		List<NastavnikEntity> nastavniciList = new ArrayList<NastavnikEntity>();
		for (int i = 0; i < count; i++) {
			NastavnikEntity nastavnikEntity = new NastavnikEntity();
			nastavnikEntity.setKorisnik(korisniciList.get(i));
			nastavnikEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword("lozinka"));
			String nadimak = RADE.replaceYULatinWithUS(RADE.generisiNadimak().toLowerCase().replace(" ", "-"));
			nastavnikEntity.setKorisnickoIme("nst." + nadimak.substring(0, Math.min(nadimak.length(), 16)));
			int stanislav = 1;
			if (nastavnikRepository.existsByKorisnickoIme(nastavnikEntity.getKorisnickoIme())) {
				String ime = nastavnikEntity.getKorisnik().getIme();
				nadimak = "nst." + RADE.replaceYULatinWithUS(ime.substring(0, Math.min(ime.length(), 10)) +
						nastavnikEntity.getKorisnik().getPrezime().substring(0, 2)).toLowerCase();
				nastavnikEntity.setKorisnickoIme(nadimak);
				while (true) {
					if (nastavnikRepository.existsByKorisnickoIme(nastavnikEntity.getKorisnickoIme())) {
						nastavnikEntity.setKorisnickoIme(nadimak + stanislav++);
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
			nastavniciList.add(nastavnikEntity);
			try {
				nastavnikRepository.save(nastavnikEntity);
				logger.info("Nastavnik je uspešno kreiran: {}", nastavnikEntity);
			} catch (Exception e) {
				logger.error("Došlo je do greške prilikom upisa podataka u DB: {}", e.getMessage());
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Došlo je do greške prilikom upisa podataka u DB.");
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(nastavniciList);
	}
}
