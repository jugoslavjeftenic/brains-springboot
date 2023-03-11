package com.ikt.t99.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.entities.NastavnikEntity;

public interface NastavnikRepository extends CrudRepository<NastavnikEntity, Long> {

	public Boolean existsByKorisnickoIme(String korisnickoIme);
	public Boolean existsByKorisnik(KorisnikEntity korisnik);
	public NastavnikEntity findByKorisnickoIme(String korisnickoIme);
	public List<NastavnikEntity> findByKorisnickoImeStartsWith(String startsWith);
}
