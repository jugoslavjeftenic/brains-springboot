package com.iktprekvalifikacija.project.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class OfferEntity {
	
	// 3.1
	/*
	 * U paketu com.iktpreobuka.project.entities napraviti klasu OfferEntity sa sledećim atributima:
	 * • id, offer name, offer description, offer created, offer expires, regular price, action price,
	 *   image path, available offers, bought offers i offer status
	 * • atribut offer created podrazumeva datum kreiranja ponude, a offer expires datum kada ponuda ističe
	 * • atribut available offers govori koliko je trenutno ponuda na raspolaganju (broj dostupnih ponuda),
	 *   dok atribut bought offers govori koliko je ponuda dosad prodato (broj prodatih ponuda)
	 * • atribut image path podrazumeva putanju do slike i treba da bude tekstualnog tipa
	 * • offer status može da ima sledeće vrednosti:
	 *   WAIT_FOR_APPROVING, APPROVED, DECLINED i EXPIRED (koristiti enumeraciju)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "offer_generator")
	@SequenceGenerator(name="offer_generator", sequenceName = "offer_sequence", allocationSize=50)
	private Integer id;
	@Column(nullable = false)
	private String offerName;
	@Column
	private String offerDesc;
	// https://www.baeldung.com/migrating-to-java-8-date-time-api
	@Column
	private LocalDateTime offerCreated;
	@Column
	private LocalDateTime offerExpires;
	@Column
	private Float regularPrice;
	@Column
	private Float actionPrice;
	@Column
	private String imagePath;
	@Column
	private Integer availableOffers;
	@Column
	private Integer boughtOffers;
	@Column
	private EOfferEntity offerStatus;
	
	public OfferEntity(Integer id, String offerName, String offerDesc, LocalDateTime offerCreated,
			LocalDateTime offerExpires, Float regularPrice, Float actionPrice, String imagePath,
			Integer availableOffers, Integer boughtOffers, EOfferEntity offerStatus) {
		super();
		this.id = id;
		this.offerName = offerName;
		this.offerDesc = offerDesc;
		this.offerCreated = offerCreated;
		this.offerExpires = offerExpires;
		this.regularPrice = regularPrice;
		this.actionPrice = actionPrice;
		this.imagePath = imagePath;
		this.availableOffers = availableOffers;
		this.boughtOffers = boughtOffers;
		this.offerStatus = offerStatus;
	}

	public OfferEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDesc() {
		return offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}

	public LocalDateTime getOfferCreated() {
		return offerCreated;
	}

	public void setOfferCreated(LocalDateTime offerCreated) {
		this.offerCreated = offerCreated;
	}

	public LocalDateTime getOfferExpires() {
		return offerExpires;
	}

	public void setOfferExpires(LocalDateTime offerExpires) {
		this.offerExpires = offerExpires;
	}

	public Float getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Float regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Float getActionPrice() {
		return actionPrice;
	}

	public void setActionPrice(Float actionPrice) {
		this.actionPrice = actionPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getAvailableOffers() {
		return availableOffers;
	}

	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}

	public Integer getBoughtOffers() {
		return boughtOffers;
	}

	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}

	public EOfferEntity getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(EOfferEntity offerStatus) {
		this.offerStatus = offerStatus;
	}
}
