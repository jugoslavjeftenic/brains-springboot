package com.ikt.t4.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikt.t4.project.entities.CategoryEntity;
import com.ikt.t4.project.repositories.CategoryRepository;

@Service
public class CategoryDAOServiceImpl implements CategoryDAOService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Iterable<CategoryEntity> generateListOfCategories() {
		List<CategoryEntity> categories = new ArrayList<>();
		String[][] categoriesData = {
				{"Majstori", "Pronađite majstora za sve što niste sposobni da uradite sami."},
				{"Privatni časovi", "Sve što ste hteli da znate o matematici, fizici, hemiji… a niste slušali onog ko Vam je objašnjavao."},
				{"eMarketing", "Najbolji način da upropastite svoj brend."},
			    {"Pametni uređaji", "Pametni uređaji koji će vas ostaviti bez ideja šta da radite sa njima."},
			    {"Računarski softver i hardver", "Berza tehnološki najnovijih uređaja koji će zastareti pre nego što stignu do Vas."},
			    {"Samopomoćne knjige i proizvodi", "Knjige, audio kursevi, aplikacije i druge proizvode koji će Vas učiniti još nezadovoljnijim sa vašim životom."},
			    {"Zaštita podataka i bezbednost", "usluge i tehnologije koje će vas učiniti sigurnim - dok vaši podaci cirkulišu po internetu."},
			    {"Usluge korisničke podrške", "usluge koje će vas ostaviti još više izgubljenim i bespomoćnim."},
			    {"Gubitak težine i fitnes", "proizvodi, programi i usluge od kojih će te biti još manje zadovoljni vašim telom."},
			    {"Razvoj karijere i profesionalnog razvoja", "Knjige, kursevi, mentorski programi i druge usluge od kojih ćete biti još manje uspešni na poslu."},
			    {"Lični rast i razvoj", "Knjige, kursevi, radionice, i drugi proizvodi i usluge koje će vas učiniti još nesigurnijim u sebe."},
			    {"Zabava i online upoznavanje", "Aplikacije, sajtovi, i druge usluge koje će vas učiniti još usamljenijim."},
				{"Automobili", "Dajte drugima carstvo za svog metalnog ljubimca."}
		};
		for (int i = 0; i < categoriesData.length; i++) {
			CategoryEntity category = new CategoryEntity();
			category.setCategoryName(categoriesData[i][0]);
			category.setCategoryDescription(categoriesData[i][1]);
			if (!categoryRepository.existsByCategoryName(category.getCategoryName())) {
				categoryRepository.save(category);
				categories.add(category);
			}
		}
		return categories;
	}

	@Override
	public CategoryEntity checkAndChangeCategoryData(CategoryEntity categoryToCheck) {
		CategoryEntity categoryToReturn = new CategoryEntity();
		if (categoryToCheck.getId() != null) {
			try {
				categoryToReturn = categoryRepository.findById(categoryToCheck.getId()).get();
			} catch (Exception e) {
				// TODO Vratiti gresku da nema korisnika u bazi.
				return null;
			}
		}
		if (categoryToCheck.getCategoryName() != null) {
			categoryToReturn.setCategoryName(categoryToCheck.getCategoryName());
		}
		if (categoryToCheck.getCategoryDescription() != null) {
			categoryToReturn.setCategoryDescription(categoryToCheck.getCategoryDescription());
		}
		return categoryToReturn;
	}
}
