package com.ikt.t99.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.entities.RoditeljEntity;

public interface RoditeljRepository extends CrudRepository<RoditeljEntity, Long> {

	public Boolean existsByKorisnickoIme(String korisnickoIme);
	public Boolean existsByKorisnik(KorisnikEntity korisnik);
	public RoditeljEntity findByKorisnickoIme(String korisnickoIme);
	public List<RoditeljEntity> findByKorisnickoImeStartsWith(String startsWith);
}
