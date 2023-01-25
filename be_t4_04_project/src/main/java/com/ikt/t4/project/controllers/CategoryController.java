package com.ikt.t4.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t4.project.entities.CategoryEntity;
import com.ikt.t4.project.repositories.CategoryRepository;
import com.ikt.t4.project.services.CategoryDAOService;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryDAOService categoryService;

	// Create
	@RequestMapping(method = RequestMethod.POST, path = "/admin")
	public CategoryEntity create(@RequestBody CategoryEntity category) {
		if (!categoryRepository.existsByCategoryName(category.getCategoryName())) {
			return categoryRepository.save(categoryService.checkAndChangeCategoryData(category));
		}
	    return null;
	}

	// Read
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public CategoryEntity read(@PathVariable Long id) {
	    return categoryRepository.findById(id).orElse(null);
	}

	// Update
	@RequestMapping(method = RequestMethod.PUT, path = "/admin/{id}")
	public CategoryEntity update(@PathVariable Long id, @RequestBody CategoryEntity category) {
		category.setId(id);
		// TODO Vratiti odgovarajucu gresku kada id-a nema u bazi.
	    return categoryRepository.save(categoryService.checkAndChangeCategoryData(category));
	}

	// Soft Delete
	@RequestMapping(method = RequestMethod.DELETE, path = "/admin/{id}")
	public CategoryEntity deleteSoft(@PathVariable Long id) {
		CategoryEntity categoryToSoftDelete = categoryRepository.findById(id).get();
		categoryRepository.deleteById(id);
	    categoryToSoftDelete.setDeleted(true);
	    return categoryToSoftDelete;
	}

	// List all
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<CategoryEntity> list() {
	    return categoryRepository.findAll();
	}

	// Generate
	@RequestMapping(method = RequestMethod.POST, path = "/admin/populatetable")
	public Iterable<CategoryEntity> populateTable() {
		return categoryService.generateListOfCategories();
	}
}
