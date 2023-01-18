package com.ikt.t3.project.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t3.project.entites.VoucherEntity;
import com.ikt.t3.project.repositories.OfferRepository;
import com.ikt.t3.project.repositories.UserRepository;
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

	// TODO testirati
	@RequestMapping(method = RequestMethod.POST, path = "/{offerId}/buyer/{buyerId}")
	public VoucherEntity addVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@RequestParam String expirationDate, @RequestParam Boolean isUsed) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		LocalDateTime exDate;
		try {
			exDate = LocalDateTime.parse(expirationDate, formatter);
		} catch (Exception e) {
			try {
				exDate = LocalDateTime.parse(expirationDate + "T00:00:00", formatter);
				exDate = exDate.plusDays(1);
			} catch (Exception e2) {
				return null;
			}
		}
		try {
			VoucherEntity newVoucher = new VoucherEntity();
			newVoucher.setExpirationDate(exDate);
			newVoucher.setIsUsed(isUsed);
			newVoucher.setOffer(offerRepository.findById(offerId).get());
			newVoucher.setUser(userRepository.findById(buyerId).get());
			return voucherRepository.save(newVoucher);
		} catch (Exception e) {
			return null;
		}
	}
}
