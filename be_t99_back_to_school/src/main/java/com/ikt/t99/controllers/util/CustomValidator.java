package com.ikt.t99.controllers.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ikt.t99.dtos.AdministratorDTO;
import com.ikt.t99.dtos.DnevnikDTO;
import com.ikt.t99.dtos.DnevnikDTONastavnik;
import com.ikt.t99.dtos.KorisnikDTO;
import com.ikt.t99.dtos.NastavnikDTO;
import com.ikt.t99.dtos.PredmetDTO;
import com.ikt.t99.dtos.RoditeljDTO;
import com.ikt.t99.dtos.UcenikDTO;
import com.ikt.t99.repositories.AdministratorRepository;
import com.ikt.t99.repositories.NastavnikRepository;

@Component
public class CustomValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(CustomValidator.class);

	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private NastavnikRepository nastavnikRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("Metoda supports() je pozvana.");
		return
				KorisnikDTO.class.equals(clazz) ||
				AdministratorDTO.class.equals(clazz) ||
				NastavnikDTO.class.equals(clazz) ||
				RoditeljDTO.class.equals(clazz) ||
				UcenikDTO.class.equals(clazz) ||
				PredmetDTO.class.equals(clazz) ||
				DnevnikDTO.class.equals(clazz) ||
				DnevnikDTONastavnik.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("Metoda validate() je pozvana.");
		if (target instanceof AdministratorDTO) {
			AdministratorDTO administratorDTO = (AdministratorDTO) target;
			if (administratorDTO.getLozinka() != null &&
					!administratorDTO.getLozinka().equals(administratorDTO.getLozinkaPotvrda())) {
				errors.reject("400", "Lozinka se ne podudara sa potvrdom lozinke.");
				logger.warn("Lozinka se ne podudara sa potvrdom lozinke.");
			}
			if (administratorRepository.existsByKorisnickoIme("adm." + administratorDTO.getKorisnickoIme())) {
				errors.reject("400", "Korisničko ime '" + administratorDTO.getKorisnickoIme() + "' već postoji u bazi.");
				logger.warn("Korisničko ime '{}' već postoji u bazi.", administratorDTO.getKorisnickoIme());
			}
		}
		if (target instanceof NastavnikDTO) {
			NastavnikDTO nastavnikDTO = (NastavnikDTO) target;
			if (nastavnikDTO.getLozinka() != null &&
					!nastavnikDTO.getLozinka().equals(nastavnikDTO.getLozinkaPotvrda())) {
				errors.reject("400", "Lozinka se ne podudara sa potvrdom lozinke.");
				logger.warn("Lozinka se ne podudara sa potvrdom lozinke.");
			}
			if (nastavnikRepository.existsByKorisnickoIme("nst." + nastavnikDTO.getKorisnickoIme())) {
				errors.reject("400", "Korisničko ime '" + nastavnikDTO.getKorisnickoIme() + "' već postoji u bazi.");
				logger.warn("Korisničko ime '{}' već postoji u bazi.", nastavnikDTO.getKorisnickoIme());
			}
		}
		if (target instanceof RoditeljDTO) {
			RoditeljDTO roditeljDTO = (RoditeljDTO) target;
			if (roditeljDTO.getLozinka() != null &&
					!roditeljDTO.getLozinka().equals(roditeljDTO.getLozinkaPotvrda())) {
				errors.reject("400", "Lozinka se ne podudara sa potvrdom lozinke.");
				logger.warn("Lozinka se ne podudara sa potvrdom lozinke.");
			}
		}
		if (target instanceof UcenikDTO) {
			UcenikDTO ucenikDTO = (UcenikDTO) target;
			if (ucenikDTO.getLozinka() != null &&
					!ucenikDTO.getLozinka().equals(ucenikDTO.getLozinkaPotvrda())) {
				errors.reject("400", "Lozinka se ne podudara sa potvrdom lozinke.");
				logger.warn("Lozinka se ne podudara sa potvrdom lozinke.");
			}
		}
	}
}
