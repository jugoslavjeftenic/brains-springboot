package com.ikt.t3.project.entites;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public VoucherEntity() {
		super();
	}

	public VoucherEntity(Integer id, LocalDateTime expirationDate, Boolean isUsed, Integer version) {
		super();
		this.id = id;
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
		this.version = version;
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
}
