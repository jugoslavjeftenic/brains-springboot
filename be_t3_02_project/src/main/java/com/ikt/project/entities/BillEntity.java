package com.ikt.project.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BillEntity {

	// T3 3.1
	/*
	 * U paketu com.iktpreobuka.project.entities kreirati klasu BillEntity sa sledećim atributima:
	 * • id – automatski generisan surogatni ključ (celi broj),
	 * • paymentMade - da li je novac uplaćen (true ili false),
	 * • paymentCanceled - da li je kupovina otkazana (true ili false),
	 * • billCreated - datum kada je račun napravljen.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bill_generator")
	@SequenceGenerator(name="bill_generator", sequenceName = "bill_sequence", allocationSize=1)
	private Integer id;
	@Column
    private Boolean paymentMade;
    @Column
    private Boolean paymentCanceled;
    @Column
    private LocalDateTime billCreated;
    @Version
    private Integer version;
    // T3 3.4
	/*
	 * Povezati ponudu i račun
	 * • račun predstavlja kupovinu jedne ponude
	 * • jedna ponuda se može nalaziti na više računa
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offer;
	// T3 3.5
	/*
	 * Povezati korisnika i račun
	 * • račun predstavlja kupovinu jedne ponude od strane jednog kupca
	 * • jedan korisnik može imati više računa
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;
	
    public BillEntity() {
		super();
	}

	public BillEntity(Integer id, Boolean paymentMade, Boolean paymentCanceled, LocalDateTime billCreated,
			Integer version, OfferEntity offer, UserEntity user) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
		this.version = version;
		this.offer = offer;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
}
