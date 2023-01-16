package com.ikt.project.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfferEntity {
	
	// T2 3.1
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
	
	// T3 1.1
	/*
	 * U paketu com.iktpreobuka.project.entities u klasama kreiranim na prethodnim časovima ubaciti
	 * odgovarajuće Hibernate anotacije i njihove parametre za svako od polja
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "offer_generator")
	@SequenceGenerator(name="offer_generator", sequenceName = "offer_sequence", allocationSize=1)
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
	private Double regularPrice;
	@Column
	private Double actionPrice;
	@Column
	private String imagePath;
	@Column
	private Integer availableOffers;
	@Column
	private Integer boughtOffers;
	@Column
	private EOfferEntity offerStatus;
	@Version
	private Integer version;
	// T3 2.1
	/*
	 * Povezati ponudu i kategoriju
	 * • jedna ponuda pripada tačno jednoj kategoriju, dok jedna kategorija ima više ponuda koje joj pripadaju
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private CategoryEntity category;
	// T3 2.2
	/*
	 * Povezati korisnika i ponudu
	 * • korisnik može da kreira više ponuda, a jednu ponudu kreira tačno jedan korisnik
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;
    // T3 3.4
	/*
	 * Povezati ponudu i račun
	 * • račun predstavlja kupovinu jedne ponude
	 * • jedna ponuda se može nalaziti na više računa
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "offer", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<BillEntity> bills = new ArrayList<BillEntity>();
	
	public OfferEntity() {
		super();
	}

	public OfferEntity(Integer id, String offerName, String offerDesc, LocalDateTime offerCreated,
			LocalDateTime offerExpires, Double regularPrice, Double actionPrice, String imagePath,
			Integer availableOffers, Integer boughtOffers, EOfferEntity offerStatus, Integer version,
			CategoryEntity category, UserEntity user, List<BillEntity> bills) {
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
		this.version = version;
		this.category = category;
		this.user = user;
		this.bills = bills;
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

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Double getActionPrice() {
		return actionPrice;
	}

	public void setActionPrice(Double actionPrice) {
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<BillEntity> getBills() {
		return bills;
	}

	public void setBills(List<BillEntity> bills) {
		this.bills = bills;
	}
}
