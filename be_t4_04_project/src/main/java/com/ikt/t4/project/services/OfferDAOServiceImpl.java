package com.ikt.t4.project.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikt.t4.project.entities.CategoryEntity;
import com.ikt.t4.project.entities.EOfferEntity;
import com.ikt.t4.project.entities.EUserRole;
import com.ikt.t4.project.entities.OfferEntity;
import com.ikt.t4.project.entities.UserEntity;
import com.ikt.t4.project.repositories.CategoryRepository;
import com.ikt.t4.project.repositories.OfferRepository;
import com.ikt.t4.project.repositories.UserRepository;

@Service
public class OfferDAOServiceImpl implements OfferDAOService {

	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<OfferEntity> generateListOfOffers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfferEntity checkAndChangeOfferData(Long categoryId, Long sellerId, OfferEntity offerToCheck) {
		OfferEntity offerToReturn = new OfferEntity();
		if (offerToCheck.getId() != null) {
			try {
				offerToReturn = offerRepository.findById(offerToCheck.getId()).get();
			} catch (Exception e) {
				// TODO Vratiti gresku da nema ponude u bazi.
				return null;
			}
		}
		if (offerToCheck.getOfferName() != null) {
			offerToReturn.setOfferName(offerToCheck.getOfferName());
		}
		if (offerToCheck.getOfferDesc() != null) {
			offerToReturn.setOfferDesc(offerToCheck.getOfferDesc());
		}
		if (offerToCheck.getId() == null) {
			offerToReturn.setOfferCreated(LocalDateTime.now());
			offerToReturn.setOfferExpires(offerToReturn.getOfferCreated().plusDays(10));
		}
		else {
			if (offerToCheck.getOfferCreated() != null) {
				offerToReturn.setOfferCreated(offerToCheck.getOfferCreated());
				offerToReturn.setOfferExpires(offerToReturn.getOfferCreated().plusDays(10));
			}
		}
		if (offerToCheck.getRegularPrice() != null) {
			offerToReturn.setRegularPrice(offerToCheck.getRegularPrice());
		}
		if (offerToCheck.getActionPrice() != null) {
			offerToReturn.setActionPrice(offerToCheck.getActionPrice());
		}
		if (offerToCheck.getImagePath() != null) {
			offerToReturn.setImagePath(offerToCheck.getImagePath());
		}
		if (offerToCheck.getAvailableOffers() != null) {
			offerToReturn.setAvailableOffers(offerToCheck.getAvailableOffers());
		}
		if (offerToCheck.getId() == null) {
			offerToReturn.setBoughtOffers(0);
			offerToReturn.setOfferStatus(EOfferEntity.WAIT_FOR_APPROVING);
		}
		if (categoryId != null) {
			CategoryEntity category = new CategoryEntity();
			try {
				category = categoryRepository.findById(categoryId).get();
			} catch (Exception e) {
				// TODO Vratiti gresku da nema kategorije u bazi.
				return null;
			}
			offerToReturn.setCategory(category);
		}
		if (sellerId != null) {
			UserEntity user = new UserEntity();
			try {
				user = userRepository.findById(sellerId).get();
				if (user.getUserRole() != EUserRole.ROLE_SELLER) {
					// TODO Vratiti gresku da korisnik nije prodavac.
					return null;
				}
			} catch (Exception e) {
				// TODO Vratiti gresku da nema prodavca u bazi.
				return null;
			}
			offerToReturn.setUser(user);
		}
		return offerToReturn;
	}
}
