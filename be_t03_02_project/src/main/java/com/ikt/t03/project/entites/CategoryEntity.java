package com.ikt.t03.project.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CategoryEntity {
	
	// T2 2.1
	/*
	 * U paketu com.iktpreobuka.project.entities napraviti klasu CategoryEntity sa sledećim atributima:
	 * • id, category name i category description
	 * • svi atributi, sem id-a treba da budu tekstualnog tipa
	 */
	
	// T3 1.1
	/*
	 * U paketu com.iktpreobuka.project.entities u klasama kreiranim na prethodnim časovima ubaciti
	 * odgovarajuće Hibernate anotacije i njihove parametre za svako od polja
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "category_generator")
	@SequenceGenerator(name="category_generator", sequenceName = "category_sequence", allocationSize=1)
	private Integer id;
	@Column(nullable = false)
	private String categoryName;
	@Column
	private String categoryDescription;
	@Version
	private Integer version;
	// T3 2.1
	/*
	 * Povezati ponudu i kategoriju
	 * • jedna ponuda pripada tačno jednoj kategoriju, dok jedna kategorija ima više ponuda koje joj pripadaju
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<OfferEntity> offers = new ArrayList<OfferEntity>();

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(Integer id, String categoryName, String categoryDescription, Integer version,
			List<OfferEntity> offers) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.version = version;
		this.offers = offers;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<OfferEntity> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferEntity> offers) {
		this.offers = offers;
	}
}
