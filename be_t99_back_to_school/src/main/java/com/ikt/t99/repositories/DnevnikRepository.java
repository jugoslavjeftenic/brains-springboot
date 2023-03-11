package com.ikt.t99.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.DnevnikEntity;
import com.ikt.t99.entities.NastavnikEntity;
import com.ikt.t99.entities.PredmetEntity;
import com.ikt.t99.entities.UcenikEntity;

public interface DnevnikRepository extends CrudRepository<DnevnikEntity, Long> {

	public List<DnevnikEntity> findByUcenik(UcenikEntity ucenik);
	public List<DnevnikEntity> findByUcenikAndNastavnik(UcenikEntity ucenik, NastavnikEntity nastavnik);
	public List<DnevnikEntity> findByPredmetAndNastavnik(PredmetEntity predmet, NastavnikEntity nastavnik);
	public List<DnevnikEntity> findByDatumAndUcenikAndPredmetAndNastavnikAndRazredAndPolugodisteAndOcena
	(LocalDate datum, UcenikEntity ucenik, PredmetEntity predmet, NastavnikEntity nastavnik,
			Integer razred, Integer polugodiste, Integer ocena);
}
