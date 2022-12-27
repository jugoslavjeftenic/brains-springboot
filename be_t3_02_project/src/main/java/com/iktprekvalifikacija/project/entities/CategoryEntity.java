package com.iktprekvalifikacija.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class CategoryEntity {
	
	// 2.1
	/*
	 * U paketu com.iktpreobuka.project.entities napraviti klasu CategoryEntity sa sledećim atributima:
	 * • id, category name i category description
	 * • svi atributi, sem id-a treba da budu tekstualnog tipa
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "category_generator")
	@SequenceGenerator(name="category_generator", sequenceName = "category_sequence", allocationSize=50)
	private Integer id;
	@Column(nullable = false)
	private String catName;
	@Column
	private String catDescription;
	
	public CategoryEntity(Integer id, String catName, String catDescription) {
		super();
		this.id = id;
		this.catName = catName;
		this.catDescription = catDescription;
	}

	public CategoryEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatDescription() {
		return catDescription;
	}

	public void setCatDescription(String catDescription) {
		this.catDescription = catDescription;
	}
}
