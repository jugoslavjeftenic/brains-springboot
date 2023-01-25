package com.ikt.t4.project.services;

import com.ikt.t4.project.entities.CategoryEntity;

public interface CategoryDAOService {

	public Iterable<CategoryEntity> generateListOfCategories();
	public CategoryEntity checkAndChangeUserData(CategoryEntity category);
}
