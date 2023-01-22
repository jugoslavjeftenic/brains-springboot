package com.ikt.t4.project.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "voucher_generator")
	@SequenceGenerator(name="voucher_generator", sequenceName = "voucher_sequence", allocationSize=1)
	private Long id;
	@Column(nullable=false)
    private LocalDateTime expirationDate;
	@Column(nullable=false)
    private Boolean isUsed;
    @Version
    private Integer version;

	@JsonIgnore
	@OneToOne(mappedBy = "voucher", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private BillEntity bill;
}
