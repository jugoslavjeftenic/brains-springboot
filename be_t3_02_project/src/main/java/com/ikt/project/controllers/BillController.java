package com.ikt.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.project.entities.BillEntity;
import com.ikt.project.repositories.BillRepository;

@RestController
@RequestMapping(value = "/api/v1/bills")
public class BillController {

	// T3 3.3
	/*
	 * U paketu com.iktpreobuka.project.controllers napraviti klasu BillController
	 * sa REST endpoint-om koji vraća listu svih računa
	 * • putanja /project/bills
	 */
	@Autowired
	private BillRepository billRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<BillEntity> getAll() {
		return billRepository.findAll();
	}

}
