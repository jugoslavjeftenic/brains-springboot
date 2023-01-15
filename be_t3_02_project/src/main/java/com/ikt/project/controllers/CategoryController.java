package com.ikt.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.project.entities.CategoryEntity;
import com.ikt.project.repositories.CategoryRepository;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

	// T3 1.3
	/*
	 * Izmeniti kontrolere kreirane na prethodnim časovima, tako da rade sa bazom
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	// T2 2.3
	/*
	 * Kreirati REST endpoint koji vraća listu svih kategorija
	 * • putanja /project/categories
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<CategoryEntity> getAll() {
		return categoryRepository.findAll();
	}

	// T2 2.7
	/*
	 * Kreirati REST endpoint koji vraća kategoriju po vrednosti prosleđenog ID-a
	 * • putanja /project/categories/{id}
	 * • u slučaju da ne postoji kategorija sa traženom vrednošću ID-a vratiti null
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public CategoryEntity getById(@PathVariable Integer id) {
		CategoryEntity category;
		try {
			category = categoryRepository.findById(id).get();
			return category;
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 2.4
	/*
	 * Kreirati REST endpoint koji omogućava dodavanje nove kategorije
	 * • putanja /project/categories
	 * • metoda treba da vrati dodatu kategoriju
	 */
	@RequestMapping(method = RequestMethod.POST)
	public CategoryEntity addCategory(@RequestBody CategoryEntity newCategory) {
		try {
			if (!(categoryRepository.findByCategoryName(newCategory.getCategoryName()).size() > 0)) {
				return categoryRepository.save(newCategory);
			}
		} catch (Exception e) {
			System.out.println("Kategorija vec postoji u bazi.");
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/populatetable")
	public Iterable<CategoryEntity> populateTable() {
		List<CategoryEntity> categories = new ArrayList<>();
		String[][] categoriesData = {
				{"Majstori", "Pronađite majstora za sve što niste sposobni da uradite sami."},
				{"Privatni casovi", "Sve što ste hteli da znate o matematici, fizici, hemiji… a nije imao ko da Vam objasni."},
				{"eMarketing", "Najbolji način da upropastite svoj brend."},
			    {"Pametni uređaji", "Pametni uređaji koji će vas ostaviti bez ideja šta da radite sa njima."},
			    {"Računarski softver i hardver", "Berza tehnološki najnovijih uređaja koji će zastareti pre nego što stignu do Vas."},
			    {"Samopomoćne knjige i proizvodi", "Knjige, audio kursevi, aplikacije i druge proizvode koji će Vas učiniti još nezadovoljnijim sa vašim životom."},
			    {"Zaštita podataka i bezbednost", "usluge i tehnologije koje će vas učiniti sigurnim - dok vaši podaci cirkulišu po internetu."},
			    {"Usluge korisničke podrške", "usluge koje će vas ostaviti još više izgubljenim i bespomoćnim."},
			    {"Gubitak težine i fitnes", "proizvodi, programi i usluge od kojih će te biti još manje zadovoljni vašim telom."},
			    {"Razvoj karijere i profesionalnog razvoja", "Knjige, kursevi, mentorski programi i druge usluge od kojih će te biti još manje uspešni na poslu."},
			    {"Lični rast i razvoj", "Knjige, kursevi, radionice, i druge proizvode i usluge koje će vas učiniti još nesigurnijim u sebe."},
			    {"Zabava i online upoznavanje", "Aplikacije, sajtovi, i druge usluge koje će vas učiniti još usamljenijim."},
				{"Automobili", "Dajte drugima carstvo za metalnog ljubimca."}
		};
		for (int i = 0; i < categoriesData.length; i++) {
			CategoryEntity category = new CategoryEntity();
			category.setCategoryName(categoriesData[i][0]);
			category.setCategoryDescription(categoriesData[i][1]);
			try {
				if (!(categoryRepository.findByCategoryName(category.getCategoryName()).size() > 0)) {
					categories.add(category);
				}
			} catch (Exception e) {
				System.out.println("Kategorija vec postoji u bazi.");
			}
		}
		return categoryRepository.saveAll(categories);
	}

	// T2 2.5
	/*
	 * Kreirati REST endpoint koji omogućava izmenu postojeće kategorije
	 * • putanja /project/categories/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednoj kategoriji metoda treba da vrati null,
	 *   a u suprotnom vraća podatke kategorije sa izmenjenim vrednostima
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity updatedCategory) {
		CategoryEntity category;
		try {
			category = categoryRepository.findById(id).get();
			if (updatedCategory.getCategoryName() != null) {
				category.setCategoryName(updatedCategory.getCategoryName());
			}
			if (updatedCategory.getCategoryDescription() != null) {
				category.setCategoryDescription(updatedCategory.getCategoryDescription());
			}
			return categoryRepository.save(category);
		} catch (Exception e) {
			return null;
		}
	}
	
	// T2 2.6
	/*
	 * Kreirati REST endpoint koji omogućava brisanje postojeće kategorije
	 * • putanja /project/categories/{id}
	 * • ukoliko je prosleđen ID koji ne pripada nijednoj kategoriji metoda treba da vrati null,
	 *   a u suprotnom vraća podatke o kategoriji koja je obrisana
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		try {
			CategoryEntity category = categoryRepository.findById(id).get();
			categoryRepository.delete(category);
			return category;
		} catch (Exception e) {
			return null;
		}
	}
}
