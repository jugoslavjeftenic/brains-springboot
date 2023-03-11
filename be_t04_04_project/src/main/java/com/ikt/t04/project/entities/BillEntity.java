package com.ikt.t04.project.entities;

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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "offer")
	private OfferEntity offer;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user")
	private UserEntity user;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "voucher")
    private VoucherEntity voucher;

    public BillEntity() {
		super();
	}

	public BillEntity(Long id, Boolean paymentMade, Boolean paymentCanceled, LocalDateTime billCreated, Integer version,
			OfferEntity offer, UserEntity user, VoucherEntity voucher) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
		this.version = version;
		this.offer = offer;
		this.user = user;
		this.voucher = voucher;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}

	public Boolean getPaymentCanceled() {
		return paymentCanceled;
	}

	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}

	public LocalDateTime getBillCreated() {
		return billCreated;
	}

	public void setBillCreated(LocalDateTime billCreated) {
		this.billCreated = billCreated;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public OfferEntity getOffer() {
		return offer;
	}

	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public VoucherEntity getVoucher() {
		return voucher;
	}

	public void setVoucher(VoucherEntity voucher) {
		this.voucher = voucher;
	}
}
