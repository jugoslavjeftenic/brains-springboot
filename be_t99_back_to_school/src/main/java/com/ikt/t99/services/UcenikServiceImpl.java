package com.ikt.t99.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.UcenikDTO;
import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.entities.RoditeljEntity;
import com.ikt.t99.entities.UcenikEntity;
import com.ikt.t99.repositories.KorisnikRepository;
import com.ikt.t99.repositories.RoditeljRepository;
import com.ikt.t99.repositories.UcenikRepository;
import com.ikt.t99.security.util.Encryption;

import rade.RADE;

@Service
public class UcenikServiceImpl implements UcenikService {

	@Autowired
	private UcenikRepository ucenikRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private RoditeljRepository roditeljRepository;

	@Override
	public ResponseEntity<?> create(UcenikDTO ucenikDTO) {

		// Instanciram korisnika i ispitujem da li postoji.
		KorisnikEntity korisnikEntity = korisnikRepository.findById(ucenikDTO.getKorisnikId()).orElse(null);
		if (korisnikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
		}

		// Ispitujem da li je korisnik vec registrovan kao ucenik.
		if (ucenikRepository.existsByKorisnik(korisnikEntity)) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Korisnik je već registrovan kao ucenik.");
		}

		// Instanciram roditelja i ispitujem da li postoji.
		RoditeljEntity roditeljEntity = roditeljRepository.findById(ucenikDTO.getRoditeljId()).orElse(null);
		if (roditeljEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema roditelja sa prosleđenim ID-om.");
		}

		// Instanciram novog ucenika i setujem vrednosti.
		// Za validnost polja se brine @Valid anotacija.
		UcenikEntity ucenikEntity = new UcenikEntity();
		ucenikEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(ucenikDTO.getLozinka()));
		ucenikEntity.setKorisnik(korisnikEntity);
		ucenikEntity.setRoditelj(roditeljEntity);
		ucenikEntity.setRazred(ucenikDTO.getRazred());
		ucenikEntity.setPolugodiste(ucenikDTO.getPolugodiste());
		String ime = ucenikEntity.getKorisnik().getIme();
		String nadimak = "ucn." + RADE.replaceYULatinWithUS(ime.substring(0, Math.min(ime.length(), 10)) +
				ucenikEntity.getKorisnik().getPrezime().substring(0, 2)).toLowerCase();
		ucenikEntity.setKorisnickoIme(nadimak);
		int stanislav = 1;
		while (true) {
			if (ucenikRepository.existsByKorisnickoIme(ucenikEntity.getKorisnickoIme())) {
				ucenikEntity.setKorisnickoIme(nadimak + stanislav++);
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
			ucenikRepository.save(ucenikEntity);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ucenikEntity);
	}

	@Override
	public ResponseEntity<?> read(Long id) {

		// Instanciram ucenikEntity ako postoji.
		UcenikEntity ucenikEntity = ucenikRepository.findById(id).orElse(null);
		if (ucenikEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ucenik nije pronađen u DB-u.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(ucenikEntity);
	}

	@Override
	public ResponseEntity<?> update(Long id, UcenikDTO ucenikDTO) {

		// Ispitujem da li je ucenik sa prosledjenim ID-om registrovan.
		UcenikEntity ucenikEntity = ucenikRepository.findById(id).orElse(null);
		if (ucenikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema učenika sa prosleđenim ID-om.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		if (ucenikDTO.getKorisnikId() != null &&
				!ucenikDTO.getKorisnikId().equals(ucenikEntity.getKorisnik().getKorisnik_id())) {
			KorisnikEntity korisnikEntity =
					korisnikRepository.findById(ucenikDTO.getKorisnikId()).orElse(null);
			if (korisnikEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema korisnika sa prosleđenim ID-om.");
			}
			if (ucenikRepository.existsByKorisnik(korisnikEntity)) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnik je već registrovan kao ucenik.");
			}
			ucenikEntity.setKorisnik(korisnikEntity);
		}
		if (ucenikDTO.getKorisnickoIme() != null &&
				!("ucn." + ucenikDTO.getKorisnickoIme()).equals(ucenikEntity.getKorisnickoIme())) {
			if (ucenikDTO.getKorisnickoIme().length() < 4 ||
					ucenikDTO.getKorisnickoIme().length() > 16) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisničko ime može imati najmanje 4 i naviše 16 karaktera.");
			}
			if (ucenikRepository.existsByKorisnickoIme("ucn." + ucenikDTO.getKorisnickoIme())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Korisničko ime je zauzeto.");
			}
			ucenikEntity.setKorisnickoIme("ucn." + ucenikDTO.getKorisnickoIme());
		}
		if (ucenikDTO.getLozinka() != null) {
			if (ucenikDTO.getLozinka().length() < 6 ||
					ucenikDTO.getLozinka().length() > 20) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Korisnička lozinka može imati najmanje 6 i naviše 20 karaktera.");
			}
			if (!ucenikDTO.getLozinka().equals(ucenikDTO.getLozinkaPotvrda())) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Lozinka se ne poklapa sa potvrdom lozinke.");
			}
			ucenikEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword(ucenikDTO.getLozinka()));
		}
		if (ucenikDTO.getRazred() != null) {
			if (ucenikDTO.getRazred() < 1 || ucenikDTO.getRazred() > 8) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Dozvoljene vrednosti za razred su brojevi 1 - 8.");
			}
			ucenikEntity.setRazred(ucenikDTO.getRazred());
		}
		if (ucenikDTO.getPolugodiste() != null) {
			if (ucenikDTO.getPolugodiste() < 1 || ucenikDTO.getPolugodiste() > 2) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Dozvoljene vrednosti za polugodište su brojevi 1 i 2.");
			}
			ucenikEntity.setPolugodiste(ucenikDTO.getPolugodiste());
		}
		if (ucenikDTO.getRoditeljId() != null &&
				!ucenikDTO.getRoditeljId().equals(ucenikEntity.getRoditelj().getRoditelj_id())) {
			RoditeljEntity roditeljEntity = roditeljRepository.findById(ucenikDTO.getRoditeljId()).orElse(null);
			if (roditeljEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema roditelja sa prosleđenim ID-om.");
			}
			ucenikEntity.setRoditelj(roditeljEntity);
		}

		// Snimam u DB.
		try {
			ucenikRepository.save(ucenikEntity);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(ucenikEntity);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {

		// Ispitujem da li je ucenik sa prosledjenim ID-om registrovan.
		UcenikEntity ucenikEntity = ucenikRepository.findById(id).orElse(null);
		if (ucenikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema učenika sa prosleđenim ID-om.");
		}

	    // Brisem iz DB-a.
	    try {
	    	ucenikRepository.delete(ucenikEntity);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Učenik je pronađen ali se dogodila neočekivana greška prilikom brisanja podataka u DB-u.");
		}

	    return ResponseEntity.status(HttpStatus.OK).body(ucenikEntity);
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
//		List<KorisnikEntity> korisniciList = korisnikRepository.findNotUcenik();
		if (korisniciList.size() < count) {
			count = korisniciList.size();
		}
		List<RoditeljEntity> roditeljiList = (List<RoditeljEntity>) roditeljRepository.findAll();
		if (roditeljiList.size() < 1) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Nema roditelja u DB. Potrebno je prvo generisati min. jednog roditelja.");
		}
		List<UcenikEntity> uceniciList = new ArrayList<UcenikEntity>();
		for (int i = 0; i < count; i++) {
			UcenikEntity ucenikEntity = new UcenikEntity();
			ucenikEntity.setKorisnik(korisniciList.get(i));
			ucenikEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword("lozinka"));
			ucenikEntity.setRazred(RADE.mrRobot(1, 9));
			ucenikEntity.setPolugodiste(RADE.mrRobot(1, 3));
			ucenikEntity.setRoditelj(roditeljiList.get(RADE.mrRobot(0, roditeljiList.size())));
			String ime = ucenikEntity.getKorisnik().getIme();
			String nadimak = "ucn." + RADE.replaceYULatinWithUS(ime.substring(0, Math.min(ime.length(), 10)) +
					ucenikEntity.getKorisnik().getPrezime().substring(0, 2)).toLowerCase();
			ucenikEntity.setKorisnickoIme(nadimak);
			int stanislav = 1;
			while (true) {
				if (ucenikRepository.existsByKorisnickoIme(ucenikEntity.getKorisnickoIme())) {
					ucenikEntity.setKorisnickoIme(nadimak + stanislav++);
				}
				else {
					break;
				}
			}
			if (stanislav > 999) {
				System.out.println("Stanislave, preterano ser*!");
				continue;
			}
			uceniciList.add(ucenikEntity);
			try {
				ucenikRepository.save(ucenikEntity);
			} catch (Exception e) {
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Došlo je do neočekivane greške prilikom upisa podataka u DB.");
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(uceniciList);
	}
}
