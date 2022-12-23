package com.iktprekvalifikacija.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktprekvalifikacija.project.entities.CategoryEntity;

@RestController
@RequestMapping(value = "/project/categories")
public class CategoryController {

	static int cid = 0;
	List<CategoryEntity> categories = new ArrayList<>();

	// 2.2
	private List<CategoryEntity> getDB() {
		if(categories.size() == 0) {
			cid = 0;
			String[][] catDesc = {
					{"Majstori", "Da li Vam se nekad desi nezgoda u stanu ili kući, a da ne znate kome da se obratite? Na našem sajtu možete da pronađete majstora baš po svojoj meri."},
					{"Privatni casovi", "Matematika, fizika, hemija… Individualni časovi mogu da ti pomognu da savladaš najteže zadatke. Lako naruči profesora iz oblasti koje želiš."},
					{"Digitalni marketing", "U kategoriji možete pronaći sledeće usluge: Grafički dizajn, Izrada animiranih reklama, Izrada aplikacija, Izrada Web sajtova, Pisanje tekstova za blog, SEO optimizacija, Vođenje društvenih mreža."},
					{"Zabava", "U ovoj kategoriji možete da pronađete sve pružaoce usluga koji će učiniti da Vaše venčanje, proslava ili bilo koji svečani događaj protekne savršeno."},
					{"Sport", "U ovoj kategoriji možete pronaći pružaoce usluga iz oblasni sporta i zdravlja. Iskusni fitnes treneri, koji će Vam pomoći da trenirate na pravi način."},
					{"Malisani", "Ovde možete pronaći profesionalce koji pružaju sledeće usluge: logoped, animatori, organizacija proslave rođendana, dadilja."},
					{"Uredjenje doma", "U kategoriji uređenje doma možete da pronađete profesionalce koji pružaju sleće usluge:  Geodetske usluge, Izrada etno objekata, Izrada fontana, Ugradnja prirodnog kamena, Unutrašnji dizajn, Arhitekta."},
					{"Lepota i zdravlje", "U ovoj kategoriji možete da pronađete pružaoca usluga: manikira, pedikira, profesionalnog sminkanja, tetoviranja, medicinskog pedikira, masaže i druge."},
					{"Dekoracija", "Sve što Vam je potrebno da Vašu proslavu, rođendan, venčanje dekorišete baš kao što ste zamislili, možete pronaći u ovoj kategoriji."},
					{"Poslovne usluge", "U kategoriji poslovne usluge možete da pronađete pružaoce sledećih usluga: Advokat, Imovinsko pravne usluge, Računovodstvene usluge, Sređivanje, čuvanje i upravljanje dokumentacijom, Unos podataka u Excel."},
					{"Bezbednost", "U kategoriji bezbednost možete da pronađete pružaoce usluga preventivnih mera bezbednosti."},
					{"Kucni ljubimci", "Naši krzneni ljubimci su najverniji prijatelji i zaslužuju takav tretman. Pronađite pružaoce usluga koji će se za to i pobrinuti: Dresura pasa, Izrada kućice za pse, Šetanje pasa, Šišanje pasa i Veterinarske usluge."},
					{"Ostale usluge", "U ovoj kategoriji možete da pronađete sledeće usluge: Čišćenje snega i leda i Pogrebne usluge."},
					{"Automobili", "Brednirajte Vaš službeni automobil ili pronađite agencije koje se bave iznajmljivanjem vozila."}
			};
			for (int i = 0; i < catDesc.length; i++) {
				categories.add(new CategoryEntity(++cid, catDesc[i][0], catDesc[i][1]));
			}
		}
		return categories;
	}

	// 2.3
	@RequestMapping(method = RequestMethod.GET)
	public List<CategoryEntity> getAllCategories() {
		return getDB();
	}
	
	// 2.4
	@RequestMapping(method = RequestMethod.POST)
	public CategoryEntity saveCategory(@RequestBody CategoryEntity newCategory) {
		getDB();
		newCategory.setId(++cid);
		categories.add(newCategory);
		return newCategory;
	}

	// 2.5
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity updatedCategory) {
		for (CategoryEntity category : getDB()) {
			if (category.getId().equals(id)) {
				if (updatedCategory.getCatName() != null) {
					category.setCatName(updatedCategory.getCatName());
				}
				if (updatedCategory.getCatDescription() != null) {
					category.setCatDescription(updatedCategory.getCatDescription());
				}
				return category;
			}
		}
		return null;
	}
	
	// 2.6
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		for (CategoryEntity category : getDB()) {
			if (category.getId().equals(id)) {
				getDB().remove(category);
				return category;
			}
		}
		return null;
	}

	// 2.7
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public CategoryEntity getCategoryById(@PathVariable Integer id) {
		for (CategoryEntity category : getDB()) {
			if(category.getId().equals(id))
				return category;
		}
		return null;
	}
}
