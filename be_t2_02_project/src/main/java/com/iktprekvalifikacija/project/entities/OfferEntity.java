package com.iktprekvalifikacija.project.entities;

public class OfferEntity {
	/*
	 * 3.1 u paketu com.iktpreobuka.project.entities napraviti klasu OfferEntity sa
	 * sledećim atributima: • id, offer name, offer description, offer created,
	 * offer expires, regular price, action price, image path, available offers,
	 * bought offers i offer status 
	 * • atribut offer created podrazumeva datum
	 * kreiranja ponude, a offer expires datum kada ponuda ističe • atribut
	 * available offers govori koliko je trenutno ponuda na raspolaganju (broj
	 * dostupnih ponuda), dok atribut bought offers govori koliko je ponuda dosad
	 * prodato (broj prodatih ponuda) • atribut image path podrazumeva putanju do
	 * slike i treba da bude tekstualnog tipa • offer status može da ima sledeće
	 * vrednosti: WAIT_FOR_APPROVING, APPROVED, DECLINED i EXPIRED (koristiti
	 * enumeraciju)
	 */
	
	private Integer id;
	private String offerName, offerDesc;
	
}
