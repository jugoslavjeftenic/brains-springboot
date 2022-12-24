package com.iktprekvalifikacija.data_examples.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.data_examples.entities.AddressEntity;
import com.iktprekvalifikacija.data_examples.repositories.AddressRepository;

@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public AddressEntity saveAddres(@RequestBody AddressEntity newAddress) {
		
		AddressEntity address = new AddressEntity();
		address.setStreet(newAddress.getStreet());
		address.setCity(newAddress.getCity());
		address.setCountry(newAddress.getCountry());
		addressRepository.save(address);
		return address;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<AddressEntity> getAll() {
		return (List<AddressEntity>) addressRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public AddressEntity getById(@PathVariable Integer id) {
		AddressEntity retVal;
		try {
			retVal = addressRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			retVal = null;
		}
		return retVal;
	}
}
