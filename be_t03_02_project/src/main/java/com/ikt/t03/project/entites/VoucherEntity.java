package com.ikt.t03.project.entites;

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
public class VoucherEntity {

	// T3 4.1
	/*
	 * U paketu com.iktpreobuka.project.entities kreirati klasu VoucherEntity sa sledećim atributima:
	 * • id – automatski generisan surogatni ključ (celi broj),
	 * • expirationDate- datum dokad vaučer važi i
	 * • isUsed- da li je vaučer iskorišćen (true ili false)
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "voucher_generator")
	@SequenceGenerator(name="voucher_generator", sequenceName = "voucher_sequence", allocationSize=1)
	private Integer id;
    @Column
    private LocalDateTime expirationDate;
	@Column
    private Boolean isUsed;
    @Version
    private Integer version;
    // T3 4.4
	/*
	 * Povezati ponudu i vaučer
	 * • vaučer predstavlja račun na kome je uplata novca izvršena, pa poput računa,
	 *   vaučer se odnosi na kupovinu jedne ponude
	 * • jedna ponuda se može nalaziti na više vaučera
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offer;
    // T3 4.5
	/*
	 * Povezati korisnika i vaučer
	 * • vaučer se odnosi na kupovinu jedne ponude od strane jednog korisnika
	 * • jedan korisnik može imati više vaučera
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;

	public VoucherEntity() {
		super();
	}

	public VoucherEntity(Integer id, LocalDateTime expirationDate, Boolean isUsed, Integer version, OfferEntity offer,
			UserEntity user) {
		super();
		this.id = id;
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
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

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
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
