package com.ikt.t04.project.services;

import com.ikt.t04.project.entities.CategoryEntity;

public interface CategoryDAOService {

	public Iterable<CategoryEntity> generateListOfCategories();
	public CategoryEntity checkAndChangeCategoryData(CategoryEntity category);
}
