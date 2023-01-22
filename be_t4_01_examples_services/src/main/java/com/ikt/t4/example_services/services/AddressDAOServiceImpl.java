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
		String hql = "SELECT a FROM AddressEntity a LEFT JOIN FETCH a.users u WHERE u.name LIKE :name";
		Query query = em.createQuery(hql);
		query.setParameter("name", name + "%");
		return query.getResultList();
	}
}
