package com.ikt.t2.project.entities;

public class CategoryEntity {
	
	// 2.1
	/*
	 * U paketu com.iktpreobuka.project.entities napraviti klasu CategoryEntity sa sledećim atributima:
	 * • id, category name i category description
	 * • svi atributi, sem id-a treba da budu tekstualnog tipa
	 */
	private Integer id;
	private String catName;
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
