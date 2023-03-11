package com.ikt.t99.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.PredmetEntity;

public interface PredmetRepository extends CrudRepository<PredmetEntity, Long> {

	public Boolean existsByNazivAndRazred(String naziv, Integer razred);
	public List<PredmetEntity> findByNaziv(String naziv);
	public List<PredmetEntity> findByNazivStartsWith(String startsWith);
	public List<PredmetEntity> findByRazred(Integer razred);
}
