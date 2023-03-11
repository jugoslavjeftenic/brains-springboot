package com.ikt.t04.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ikt.t04.project.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {

}
