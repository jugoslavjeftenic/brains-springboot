package com.ikt.t99.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ikt.t99.entities.KorisnikEntity;

public interface KorisnikRepository extends CrudRepository<KorisnikEntity, Long> {

	public List<KorisnikEntity> findByJmbg(String jmbg);
	public List<KorisnikEntity> findByJmbgStartsWith(String startsWith);
	public List<KorisnikEntity> findByImeStartsWith(String startsWith);
	public List<KorisnikEntity> findByPrezimeStartsWith(String startsWith);
	@Query("SELECT k FROM KorisnikEntity k"
			+ " LEFT JOIN k.administrator a"
			+ " LEFT JOIN k.nastavnik n"
			+ " LEFT JOIN k.roditelj r"
			+ " LEFT JOIN k.ucenik u"
			+ " WHERE"
			+ " a.id IS NULL AND"
			+ " n.id IS NULL AND"
			+ " r.id IS NULL AND"
			+ " u.id IS NULL")
	public List<KorisnikEntity> findNoRole();
	@Query("SELECT k FROM KorisnikEntity k"
			+ " LEFT JOIN k.administrator a"
			+ " WHERE"
			+ " a.id IS NULL")
	public List<KorisnikEntity> findNotAdministrator();
	@Query("SELECT k FROM KorisnikEntity k"
			+ " LEFT JOIN k.nastavnik n"
			+ " WHERE"
			+ " n.id IS NULL")
	public List<KorisnikEntity> findNotNastavnik();
	@Query("SELECT k FROM KorisnikEntity k"
			+ " LEFT JOIN k.roditelj r"
			+ " WHERE"
			+ " r.id IS NULL")
	public List<KorisnikEntity> findNotRoditelj();
	@Query("SELECT k FROM KorisnikEntity k"
			+ " LEFT JOIN k.ucenik u"
			+ " WHERE"
			+ " u.id IS NULL")
	public List<KorisnikEntity> findNotUcenik();
}
