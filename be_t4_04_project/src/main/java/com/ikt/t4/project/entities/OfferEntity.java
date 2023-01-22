package com.ikt.t4.project.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	private EOfferEntity offerStatus;
	@Version
	private Integer version;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private CategoryEntity category;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;
	@JsonIgnore
	@OneToMany(mappedBy = "offer", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<BillEntity> bills = new ArrayList<BillEntity>();

}
