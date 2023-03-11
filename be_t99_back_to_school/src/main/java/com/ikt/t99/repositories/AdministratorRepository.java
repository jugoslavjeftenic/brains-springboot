package com.ikt.t99.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.AdministratorEntity;
import com.ikt.t99.entities.KorisnikEntity;

public interface AdministratorRepository extends CrudRepository<AdministratorEntity, Long> {

	public Boolean existsByKorisnickoIme(String korisnickoIme);
	public Boolean existsByKorisnik(KorisnikEntity korisnik);
	public AdministratorEntity findByKorisnickoIme(String korisnickoIme);
	public List<AdministratorEntity> findByKorisnickoImeStartsWith(String startsWith);
}
