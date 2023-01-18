package com.ikt.t3.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t3.project.entites.VoucherEntity;
import com.ikt.t3.project.repositories.VoucherRepository;

@RestController
@RequestMapping(value = "/api/v1/vouchers")
public class VoucherController {

	// T3 4.3
	/*
	 * U paketu com.iktpreobuka.project.controllers napraviti klasu VoucherController sa
	 * REST endpoint-om koji vraća listu svih vaučera
	 * • putanja /project/vouchers
	 */
	@Autowired
	private VoucherRepository voucherRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<VoucherEntity> getAll() {
		return voucherRepository.findAll();
	}
}
