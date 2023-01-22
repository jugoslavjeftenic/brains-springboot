package com.ikt.t4.project.entities;

import java.time.LocalDateTime;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BillEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bill_generator")
	@SequenceGenerator(name="bill_generator", sequenceName = "bill_sequence", allocationSize=1)
	private Long id;
	@Column(nullable=false)
    private Boolean paymentMade;
	@Column(nullable=false)
    private Boolean paymentCanceled;
	@Column(nullable=false)
    private LocalDateTime billCreated;
    @Version
    private Integer version;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offer;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;
	
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher")
    private VoucherEntity voucher;
}
