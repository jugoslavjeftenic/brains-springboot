package com.iktprekvalifikacija.project.entities;

public class CategoryEntity {

	private Integer id;
	private String catName, catDescription;
	
	public CategoryEntity() {
		super();
	}

	public CategoryEntity(Integer id, String catName, String catDescription) {
		super();
		this.id = id;
		this.catName = catName;
		this.catDescription = catDescription;
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
