package com.ikt.t03.project.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t03.project.entites.EUserRole;
import com.ikt.t03.project.entites.OfferEntity;
import com.ikt.t03.project.entites.UserEntity;
import com.ikt.t03.project.entites.VoucherEntity;
import com.ikt.t03.project.repositories.OfferRepository;
import com.ikt.t03.project.repositories.UserRepository;
import com.ikt.t03.project.repositories.VoucherRepository;

import rade.RADE;

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

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<VoucherEntity> getAll() {
		return voucherRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public VoucherEntity getById(@PathVariable Integer id) {
		VoucherEntity voucher;
		try {
			voucher = voucherRepository.findById(id).get();
			return voucher;
		} catch (Exception e) {
			return null;
		}
	}
	
	// T3 4.6
	/*
	 * Kreirati REST endpoint-ove za dodavanje, izmenu i brisanje vaučera
	 * • putanja /project/vouchers/{offerId}/buyer/{buyerId}
	 * • NAPOMENA: samo korisnik sa ulogom ROLE_CUSTOMER se može naći kao kupac na vaučeru
	 *   (u suprotnom ne dozvoliti kreiranje vaučera)
	 * • putanja /project/vouchers/{id} (izmena)
	 * • putanja /project/vouchers/{id} (brisanje)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/{offerId}/buyer/{buyerId}")
	public VoucherEntity addVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@RequestParam String expirationDate, @RequestParam Boolean isUsed) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		LocalDateTime exDate;
		try {
			exDate = LocalDateTime.parse(expirationDate, formatter);
		} catch (Exception e) {
			try {
				exDate = LocalDateTime.parse(expirationDate + "T00:00:00", formatter).plusDays(1);
			} catch (Exception e2) {
				return null;
			}
		}
		try {
			VoucherEntity newVoucher = new VoucherEntity();
			newVoucher.setExpirationDate(exDate);
			newVoucher.setIsUsed(isUsed);
			newVoucher.setOffer(offerRepository.findById(offerId).get());
			newVoucher.setUser(userRepository.findByIdAndUserRole(buyerId, EUserRole.ROLE_CUSTOMER));
			return voucherRepository.save(newVoucher);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/populatetable/{count}")
	public Iterable<VoucherEntity> populateTable(@PathVariable Integer count) {
		// TODO Treba napraviti da vraca ID-ove rekorda a ne count.
		int offersCount = (int) offerRepository.count();
		List<Integer> userIds = userRepository.findAllIdsByUserRole(EUserRole.ROLE_CUSTOMER);
		List<VoucherEntity> vouchers = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			VoucherEntity voucher = new VoucherEntity();
			voucher.setExpirationDate(LocalDateTime.now().plusDays(RADE.mrRobot(1, 10)));
			try {
				voucher.setOffer(offerRepository.findById(RADE.mrRobot(1, offersCount)).get());
			} catch (Exception e) {
				voucher.setOffer(null);
			}
			voucher.setIsUsed(RADE.mrRobot());
			try {
				voucher.setUser(userRepository.findById(userIds.get(RADE.mrRobot(1, userIds.size()))).get());
			} catch (Exception e) {
				voucher.setUser(null);
			}
			vouchers.add(voucher);
		}
		return voucherRepository.saveAll(vouchers);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public VoucherEntity updateVoucher(@PathVariable Integer id,
			@RequestParam(required = false) String expirationDate, @RequestParam(required = false) Boolean isUsed,
			@RequestParam(required = false) Integer offer, @RequestParam(required = false) Integer user) {
		LocalDateTime exDate = null;
		if (expirationDate != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			try {
				exDate = LocalDateTime.parse(expirationDate, formatter);
			} catch (Exception e) {
				try {
					exDate = LocalDateTime.parse(expirationDate + "T00:00:00", formatter).plusDays(1);
				} catch (Exception e2) {
					exDate = null;
				}
			}
		}
		try {
			VoucherEntity voucher = voucherRepository.findById(id).get();
			if (exDate != null) {
				voucher.setExpirationDate(exDate);
			}
			if (isUsed != null) {
				voucher.setIsUsed(isUsed);
			}
			if (offer != null) {
				try {
					voucher.setOffer(offerRepository.findById(offer).get());
				} catch (Exception e) {
					return null;
				}
			}
			if (user != null) {
				try {
					voucher.setUser(userRepository.findById(user).get());
				} catch (Exception e) {
					return null;
				}
			}
			return voucherRepository.save(voucher);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public VoucherEntity deleteVoucher(@PathVariable Integer id) {
		try {
			VoucherEntity voucher = voucherRepository.findById(id).get();
			voucherRepository.delete(voucher);
			return voucher;
		} catch (Exception e) {
			return null;
		}
	}
	
	// T3 4.7
	/*
	 * Kreirati REST endpoint za pronalazak svih vaučera određenog kupca
	 * • putanja /project/vouchers/findByBuyer/{buyerId}
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/findByBuyer/{buyerId}")
	public Iterable<VoucherEntity> getByUser(@PathVariable Integer buyerId) {
		UserEntity user;
		try {
			user = userRepository.findById(buyerId).get();
		} catch (Exception e) {
			return null;
		}
		return voucherRepository.findByUser(user);
	}
	
	// T3 4.8
	/*
	 * Kreirati REST endpoint za pronalazak svih vaučera određene ponude
	 * • putanja /project/vouchers/findByOffer/{offerId}
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/findByOffer/{offerId}")
	public Iterable<VoucherEntity> getByOffer(@PathVariable Integer offerId) {
		OfferEntity offer;
		try {
			offer = offerRepository.findById(offerId).get();
		} catch (Exception e) {
			return null;
		}
		return voucherRepository.findByOffer(offer);
	}
	
	// T3 4.9
	/*
	 * Kreirati REST endpoint za pronalazak svih vaučera koji nisu istekli
	 * • putanja /project/vouchers/findNonExpiredVoucher
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/findNonExpiredVoucher")
	public Iterable<VoucherEntity> getByExpirationDate() {
		return voucherRepository.findByExpirationDateGreaterThan(LocalDateTime.now());
	}
}
