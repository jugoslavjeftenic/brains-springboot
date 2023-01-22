package com.ikt.t4.example_services.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ikt.t4.example_services.entities.AddressEntity;

@Service
public class AddressDAOServiceImpl implements AddressDAOService {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<AddressEntity> findAddressesByUserName(String name) {
		// 1. Write a query
		String hql = "SELECT a FROM AddressEntity a LEFT JOIN FETCH a.users u WHERE u.name LIKE :name";
		Query query = em.createQuery(hql);
		// 2. Provide parameter values to the query
		query.setParameter("name", name + "%");
		// Execute query
		// Return results
		return query.getResultList();
	}
}
