package com.ikt.t99.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.entities.UcenikEntity;

public interface UcenikRepository extends CrudRepository<UcenikEntity, Long> {

	public Boolean existsByKorisnickoIme(String korisnickoIme);
	public Boolean existsByKorisnik(KorisnikEntity korisnik);
	public UcenikEntity findByKorisnickoIme(String korisnickoIme);
	public List<UcenikEntity> findByKorisnickoImeStartsWith(String startsWith);
}
