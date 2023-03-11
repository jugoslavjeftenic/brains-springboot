package com.ikt.t04.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t04.project.entities.BillEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

}
