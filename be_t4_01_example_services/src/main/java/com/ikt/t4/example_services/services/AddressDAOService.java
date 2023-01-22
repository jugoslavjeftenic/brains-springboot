package com.ikt.t4.example_services.services;

import com.ikt.t4.example_services.entities.AddressEntity;

public interface AddressDAOService {

	public Iterable<AddressEntity> findAddressesByUserName(String name);
}
