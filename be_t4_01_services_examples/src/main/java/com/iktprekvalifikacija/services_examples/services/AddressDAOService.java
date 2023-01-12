package com.iktprekvalifikacija.services_examples.services;

import com.iktprekvalifikacija.services_examples.entities.AddressEntity;

public interface AddressDAOService {

	public Iterable<AddressEntity> findAddressesByUserName(String name);
}
