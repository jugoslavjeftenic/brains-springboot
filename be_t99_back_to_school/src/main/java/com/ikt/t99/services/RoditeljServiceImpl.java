package com.ikt.t99.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.RoditeljDTO;
import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.entities.RoditeljEntity;
import com.ikt.t99.repositories.KorisnikRepository;
import com.ikt.t99.repositories.RoditeljRepository;
import com.ikt.t99.security.util.Encryption;

import rade.RADE;

@Service
public class RoditeljServiceImpl implements RoditeljService {

	private final Logger logger = LoggerFactory.getLogger(RoditeljServiceImpl.class);

	@Autowired
	private RoditeljRepository roditeljRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public ResponseEntity<?> create(RoditeljDTO roditeljDTO) {
		
		logger.info("Pozvan je metod create() sa objektom RoditeljDTO {}", roditeljDTO);

		// Instanciram korisnika i ispitujem da li postoji.
		KorisnikEntity korisnikEntity = korisnikRepository.findById(roditeljDTO.getKorisnikId()).orElse(null);
		if (korisnikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
		}

		// Ispitujem da li je korisnik vec registrovan kao roditelj.
		if (roditeljRepository.existsByKorisnik(korisnikEntity)) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Korisnik je već registrovan kao roditelj.");
		}
		
		// Instanciram novog roditelja i setujem vrednosti.
		// Za validnost polja se brine @Valid anotacija.
		RoditeljEntity roditeljEntity = new RoditeljEntity();
		roditeljEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(roditeljDTO.getLozinka()));
		roditeljEntity.setEposta(roditeljDTO.getEposta());
		roditeljEntity.setKorisnik(korisnikEntity);
		String ime = roditeljEntity.getKorisnik().getIme();
		String nadimak = "rdt." + RADE.replaceYULatinWithUS(ime.substring(0, Math.min(ime.length(), 10)) +
				roditeljEntity.getKorisnik().getPrezime().substring(0, 2)).toLowerCase();
		roditeljEntity.setKorisnickoIme(nadimak);
		int stanislav = 1;
		while (true) {
			if (roditeljRepository.existsByKorisnickoIme(roditeljEntity.getKorisnickoIme())) {
				roditeljEntity.setKorisnickoIme(nadimak + stanislav++);
			}
			else {
				break;
			}
		}
		if (stanislav > 999) {
			System.out.println("Stanislave, preterano ser*!");
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Nije moguće generisati korisničko ime jer u DB-u postoji više od 999 korisnika sa istim imenom i prezimenom.");
		}
		
		// Snimam u DB.
		try {
			roditeljRepository.save(roditeljEntity);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(roditeljEntity);
	}

	@Override
	public ResponseEntity<?> read(Long id) {

		// Instanciram roditeljEntity ako postoji.
		RoditeljEntity roditeljEntity = roditeljRepository.findById(id).orElse(null);
		if (roditeljEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Roditelj nije pronađen u DB-u.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(roditeljEntity);
	}

	@Override
	public ResponseEntity<?> update(Long id, RoditeljDTO roditeljDTO) {

		// Ispitujem da li je roditelj sa prosledjenim ID-om registrovan.
		RoditeljEntity roditeljEntity = roditeljRepository.findById(id).orElse(null);
		if (roditeljEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema roditelja sa prosleđenim ID-om.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		if (roditeljDTO.getKorisnikId() != null &&
				!roditeljDTO.getKorisnikId().equals(roditeljEntity.getKorisnik().getKorisnik_id())) {
			KorisnikEntity korisnikEntity =
					korisnikRepository.findById(roditeljDTO.getKorisnikId()).orElse(null);
			if (korisnikEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
			}
			if (roditeljRepository.existsByKorisnik(korisnikEntity)) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnik je već registrovan kao roditelj.");
			}
			roditeljEntity.setKorisnik(korisnikEntity);
		}
		if (roditeljDTO.getKorisnickoIme() != null &&
				!("rdt." + roditeljDTO.getKorisnickoIme()).equals(roditeljEntity.getKorisnickoIme())) {
			if (roditeljDTO.getKorisnickoIme().length() < 4 ||
					roditeljDTO.getKorisnickoIme().length() > 16) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisničko ime može imati najmanje 4 i naviše 16 karaktera.");
			}
			if (roditeljRepository.existsByKorisnickoIme("rdt." + roditeljDTO.getKorisnickoIme())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Korisničko ime je zauzeto.");
			}
			roditeljEntity.setKorisnickoIme("rdt." + roditeljDTO.getKorisnickoIme());
		}
		if (roditeljDTO.getLozinka() != null) {
			if (roditeljDTO.getLozinka().length() < 6 ||
					roditeljDTO.getLozinka().length() > 20) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnička lozinka može imati najmanje 6 i naviše 20 karaktera.");
			}
			if (!roditeljDTO.getLozinka().equals(roditeljDTO.getLozinkaPotvrda())) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Lozinka se ne poklapa sa potvrdom lozinke.");
			}
			roditeljEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(roditeljDTO.getLozinka()));
		}
		if (roditeljDTO.getEposta() != null) {
			if (roditeljDTO.getEposta().length() < 6 ||
					roditeljDTO.getEposta().length() > 100) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Adresa elektronske pošte može imati najmanje 6 i naviše 100 karaktera.");
			}
			String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(roditeljDTO.getEposta());
		    if (!matcher.matches()) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Upisana adresa elektronske pošte nije validna.");
		    }
			roditeljEntity.setEposta(roditeljDTO.getEposta());
		}

		// Snimam u DB.
		try {
			roditeljRepository.save(roditeljEntity);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(roditeljEntity);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {

		// Ispitujem da li je roditelj sa prosledjenim ID-om registrovan.
		RoditeljEntity roditeljEntity = roditeljRepository.findById(id).orElse(null);
		if (roditeljEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema roditelja sa prosleđenim ID-om.");
		}

	    // Brisem iz DB-a.
	    try {
	    	roditeljRepository.delete(roditeljEntity);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Roditelj je pronađen ali se dogodila neočekivana greška prilikom brisanja podataka u DB-u.");
		}

	    return ResponseEntity.status(HttpStatus.OK).body(roditeljEntity);
	}
	// ----------------------------------------------------------------------------------------------------

	@Override
	public ResponseEntity<?> dummy(Integer count) {

		if (count < 1) {
			count = 1;
		}
		if (count > 1000) {
			count = 1000;
		}
		List<KorisnikEntity> korisniciList = korisnikRepository.findNoRole();
//		List<KorisnikEntity> korisniciList = korisnikRepository.findNotRoditelj();
		if (korisniciList.size() < count) {
			count = korisniciList.size();
		}
		List<RoditeljEntity> roditeljiList = new ArrayList<RoditeljEntity>();
		for (int i = 0; i < count; i++) {
			RoditeljEntity roditeljEntity = new RoditeljEntity();
			roditeljEntity.setKorisnik(korisniciList.get(i));
			roditeljEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword("lozinka"));
			roditeljEntity.setEposta(RADE.replaceYULatinWithUS
					(roditeljEntity.getKorisnik().getIme() +
							roditeljEntity.getKorisnik().getPrezime().substring(0, 3)).toLowerCase() + "@ikt.rs");
			String ime = roditeljEntity.getKorisnik().getIme();
			String nadimak = "rdt." + RADE.replaceYULatinWithUS(ime.substring(0, Math.min(ime.length(), 10)) +
					roditeljEntity.getKorisnik().getPrezime().substring(0, 2)).toLowerCase();
			roditeljEntity.setKorisnickoIme(nadimak);
			int stanislav = 1;
			while (true) {
				if (roditeljRepository.existsByKorisnickoIme(roditeljEntity.getKorisnickoIme())) {
					roditeljEntity.setKorisnickoIme(nadimak + stanislav++);
				}
				else {
					break;
				}
			}
			if (stanislav > 999) {
				System.out.println("Stanislave, preterano ser*!");
				continue;
			}
			roditeljiList.add(roditeljEntity);
			try {
				roditeljRepository.save(roditeljEntity);
			} catch (Exception e) {
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(roditeljiList);
	}
}
