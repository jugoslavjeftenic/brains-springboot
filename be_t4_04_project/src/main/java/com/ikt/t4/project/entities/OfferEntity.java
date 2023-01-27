package com.ikt.t4.project.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SQLDelete(sql = "UPDATE offer_entity SET deleted = true WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class OfferEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "offer_generator")
	@SequenceGenerator(name="offer_generator", sequenceName = "offer_sequence", allocationSize=1)
	private Long id;
	@Column(nullable=false, length=64)
	private String offerName;
	@Column(length=256)
	private String offerDesc;
	@Column(nullable=false)
	private LocalDateTime offerCreated;
	@Column(nullable=false)
	private LocalDateTime offerExpires;
	@Column(nullable=false)
	private Double regularPrice;
	@Column(nullable=false)
	private Double actionPrice;
	@Column(nullable=false, length=256)
	private String imagePath;
	@Column(nullable=false)
	private Integer availableOffers;
	@Column(nullable=false)
	private Integer boughtOffers;
	@Column(nullable=false)
	private EOfferStatus offerStatus;
	@Version
	private Integer version;
	@Column(nullable=false)
	private boolean deleted = Boolean.FALSE;

	// https://www.jpa-buddy.com/blog/soft-deletion-in-hibernate-things-you-may-miss/
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "category")
	private CategoryEntity category;
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user")
	private UserEntity user;
	@JsonIgnore
	@OneToMany(mappedBy = "offer", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<BillEntity> bills = new ArrayList<BillEntity>();

	public OfferEntity() {
		super();
	}

	public OfferEntity(Long id, String offerName, String offerDesc, LocalDateTime offerCreated,
			LocalDateTime offerExpires, Double regularPrice, Double actionPrice, String imagePath,
			Integer availableOffers, Integer boughtOffers, EOfferStatus offerStatus, Integer version, boolean deleted,
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
		this.deleted = deleted;
		this.category = category;
		this.user = user;
		this.bills = bills;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public EOfferStatus getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(EOfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
