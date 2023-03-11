package com.ikt.t99.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.NastavnikEntity;
import com.ikt.t99.entities.PredajeEntity;
import com.ikt.t99.entities.PredmetEntity;

public interface PredajeRepository extends CrudRepository<PredajeEntity, Long> {

	public Boolean existsByNastavnikAndPredmet(NastavnikEntity nastavnik, PredmetEntity predmet);
	public List<PredajeEntity> findByNastavnik(NastavnikEntity nastavnik);
	public List<PredajeEntity> findByPredmet(PredmetEntity predmet);
	public List<PredajeEntity> findByNastavnikAndPredmet(NastavnikEntity nastavnik, PredmetEntity predmet);
}
