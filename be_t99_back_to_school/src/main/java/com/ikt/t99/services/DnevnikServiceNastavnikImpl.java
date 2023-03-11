package com.ikt.t99.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.DnevnikDTO;
import com.ikt.t99.dtos.DnevnikDTONastavnik;
import com.ikt.t99.dtos.DnevnikDTOreturn;
import com.ikt.t99.entities.DnevnikEntity;
import com.ikt.t99.entities.NastavnikEntity;
import com.ikt.t99.entities.PredmetEntity;
import com.ikt.t99.entities.UcenikEntity;
import com.ikt.t99.repositories.DnevnikRepository;
import com.ikt.t99.repositories.NastavnikRepository;
import com.ikt.t99.repositories.PredajeRepository;
import com.ikt.t99.repositories.PredmetRepository;
import com.ikt.t99.repositories.UcenikRepository;

@Service
public class DnevnikServiceNastavnikImpl implements DnevnikServiceNastavnik {

	private final Logger logger = LoggerFactory.getLogger(DnevnikServiceNastavnikImpl.class);

	@Autowired
	private NastavnikRepository nastavnikRepository;
	@Autowired
	private UcenikRepository ucenikRepository;
	@Autowired
	private PredmetRepository predmetRepository;
	@Autowired
	private PredajeRepository predajeRepository;
	@Autowired
	private DnevnikRepository dnevnikRepository;
	@Autowired
	private EmailService emailService;

	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ResponseEntity<?> create(HttpServletRequest request, DnevnikDTONastavnik dnevnikDTONastavnik) {
		
		logger.info("Pozvan je metod create() sa objektom DnevnikDTONastavnik {}", dnevnikDTONastavnik);

		// Instanciram nastavnikEntity.
		NastavnikEntity nastavnikEntity = new NastavnikEntity();
		try {
			nastavnikEntity = nastavnikRepository.findByKorisnickoIme(request.getUserPrincipal().getName());
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom instanciranja nastavnika: " + e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom instanciranja nastavnika: " + e.getMessage());
		}
		
		// Instanciram ucenikEntity ako postoji.
		UcenikEntity ucenikEntity = ucenikRepository.findById(dnevnikDTONastavnik.getUcenikId()).orElse(null);
		if (ucenikEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ucenik nije pronađen u DB-u.");
		}
		
		// Instanciram predmetEntity ako postoji.
		PredmetEntity predmetEntity = predmetRepository.findById(dnevnikDTONastavnik.getPredmetId()).orElse(null);
		if (predmetEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Predmet nije pronađen u DB-u.");
		}
		if (!ucenikEntity.getRazred().equals(predmetEntity.getRazred())) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Učenik pohađa " + ucenikEntity.getRazred() + " razred, "
							+ "a predmet je registrovan za " + predmetEntity.getRazred() + " razred.");
		}
		
		// Ispitujem da li nastavnik predaje predmet.
		if (!predajeRepository.existsByNastavnikAndPredmet(nastavnikEntity, predmetEntity)) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Predmet nije registrovan kod nastavnika.");
		}
		
		// Instanciram dnevnikEntity i setujem vrednosti.
		DnevnikEntity dnevnikEntity = new DnevnikEntity();
		dnevnikEntity.setDatum(LocalDate.now());
		dnevnikEntity.setUcenik(ucenikEntity);
		dnevnikEntity.setPredmet(predmetEntity);
		dnevnikEntity.setNastavnik(nastavnikEntity);
		dnevnikEntity.setRazred(ucenikEntity.getRazred());
		dnevnikEntity.setPolugodiste(ucenikEntity.getPolugodiste());
		dnevnikEntity.setOcena(dnevnikDTONastavnik.getOcena());
		
		// Snimam u DB.
		try {
			dnevnikRepository.save(dnevnikEntity);
			logger.info("Dnevnik je uspešno kreiran: {}", dnevnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB: " + e.getMessage());
		}
		
		// Obavestavam roditelja o novoj oceni
		emailService.sendSimpleEmailMessage(dnevnikEntity.getUcenik().getRoditelj().getEposta(),
				"Obaveštenje o novoj oceni za učenicu/ka " + dnevnikEntity.getUcenik().getKorisnik().getIme()
				+ " " + dnevnikEntity.getUcenik().getKorisnik().getPrezime(),
				"Učenica/k " + dnevnikEntity.getUcenik().getKorisnik().getIme() + " "
				+ dnevnikEntity.getUcenik().getKorisnik().getPrezime() + " je dobila/o novu ocenu "
				+ dnevnikEntity.getOcena() + " iz predmeta "
				+ dnevnikEntity.getPredmet().getNaziv() + ". "
				+ "Ukoliko želite više detalja o dobijenoj oceni, kontaktirajte nastavnicu/ka "
				+ nastavnikEntity.getKorisnik().getIme() + " " + nastavnikEntity.getKorisnik().getPrezime() + ".");
		logger.info("Obavestavam roditelja o novoj oceni.");

		// Instanciram  i prepakujem u povratni DTO.
		DnevnikDTOreturn dnevnikDTOreturn = new DnevnikDTOreturn();
		List<DnevnikEntity> dnevniciList = dnevnikRepository
				.findByDatumAndUcenikAndPredmetAndNastavnikAndRazredAndPolugodisteAndOcena
				(dnevnikEntity.getDatum(), ucenikEntity, predmetEntity, nastavnikEntity,
						dnevnikEntity.getRazred(), dnevnikEntity.getPolugodiste(), dnevnikEntity.getOcena());
		dnevnikDTOreturn.setDnevnik_id(dnevniciList.get(dnevniciList.size() - 1).getDnevnik_id());
		dnevnikDTOreturn.setDatum(dnevnikEntity.getDatum());
		dnevnikDTOreturn.setUcenikId(dnevnikEntity.getUcenik().getUcenik_id());
		dnevnikDTOreturn.setPredmetId(dnevnikEntity.getPredmet().getPredmet_id());
		dnevnikDTOreturn.setNastavnikId(dnevnikEntity.getNastavnik().getNastavnik_id());
		dnevnikDTOreturn.setRoditeljId(dnevnikEntity.getUcenik().getRoditelj().getRoditelj_id());
		dnevnikDTOreturn.setRazred(dnevnikEntity.getRazred());
		dnevnikDTOreturn.setPolugodiste(dnevnikEntity.getPolugodiste());
		dnevnikDTOreturn.setOcena(dnevnikEntity.getOcena());

		return ResponseEntity.status(HttpStatus.OK).body(dnevnikDTOreturn);
	}

	@Override
	public ResponseEntity<?> read(HttpServletRequest request, Long id) {
		
		logger.info("Pozvan je metod read() sa ID {}", id);

		// Instanciram nastavnikEntity.
		NastavnikEntity nastavnikEntity = new NastavnikEntity();
		try {
			nastavnikEntity = nastavnikRepository.findByKorisnickoIme(request.getUserPrincipal().getName());
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom instanciranja nastavnika {}", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom instanciranja nastavnika: " + e.getMessage());
		}

		// Instanciram dnevnikEntity ako postoji.
		DnevnikEntity dnevnikEntity = dnevnikRepository.findById(id).orElse(null);
		if (dnevnikEntity == null) {
			logger.error("Dnevnik nije pronađen u DB-u.");
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dnevnik nije pronađen u DB-u.");
		}
		if (!dnevnikEntity.getNastavnik().equals(nastavnikEntity)) {
			logger.error("Predmet nije registrovan kod nastavnika.");
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Predmet nije registrovan kod nastavnika.");
		}

		// Instanciram  i prepakujem u povratni DTO.
		DnevnikDTOreturn dnevnikDTOreturn = new DnevnikDTOreturn();
		dnevnikDTOreturn.setDnevnik_id(dnevnikEntity.getDnevnik_id());
		dnevnikDTOreturn.setDatum(dnevnikEntity.getDatum());
		dnevnikDTOreturn.setUcenikId(dnevnikEntity.getUcenik().getUcenik_id());
		dnevnikDTOreturn.setPredmetId(dnevnikEntity.getPredmet().getPredmet_id());
		dnevnikDTOreturn.setNastavnikId(dnevnikEntity.getNastavnik().getNastavnik_id());
		dnevnikDTOreturn.setRoditeljId(dnevnikEntity.getUcenik().getRoditelj().getRoditelj_id());
		dnevnikDTOreturn.setRazred(dnevnikEntity.getRazred());
		dnevnikDTOreturn.setPolugodiste(dnevnikEntity.getPolugodiste());
		dnevnikDTOreturn.setOcena(dnevnikEntity.getOcena());

		return ResponseEntity.status(HttpStatus.OK).body(dnevnikDTOreturn);
	}

	@Override
	public ResponseEntity<?> update(HttpServletRequest request, Long id, DnevnikDTO dnevnikDTO) {
		
		logger.info("Pozvan je metod update() sa ID {} i objektom DnevnikDTO {}", id, dnevnikDTO);

		// Instanciram nastavnikEntity.
		NastavnikEntity nastavnikEntity = new NastavnikEntity();
		try {
			nastavnikEntity = nastavnikRepository.findByKorisnickoIme(request.getUserPrincipal().getName());
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom instanciranja nastavnika {}", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom instanciranja nastavnika: " + e.getMessage());
		}

		// Instanciram dnevnikEntity ako postoji.
		DnevnikEntity dnevnikEntity = dnevnikRepository.findById(id).orElse(null);
		if (dnevnikEntity == null) {
			logger.error("Dnevnik nije pronađen u DB-u.");
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dnevnik nije pronađen u DB-u.");
		}
		if (!dnevnikEntity.getNastavnik().equals(nastavnikEntity)) {
			logger.error("Dnevnik nije registrovan kod logovanog nastavnika.");
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Dnevnik nije registrovan kod logovanog nastavnika.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		boolean promenaOcene = false;
		if (dnevnikDTO.getDatum() != null && !dnevnikDTO.getDatum().isAfter(LocalDate.now())) {
			dnevnikEntity.setDatum(dnevnikDTO.getDatum());
		}
		if (dnevnikDTO.getUcenikId() != null) {
			UcenikEntity ucenikEntity = ucenikRepository.findById(dnevnikDTO.getUcenikId()).orElse(null);
			if (ucenikEntity == null) {
			    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ucenik nije pronađen u DB-u.");
			}
			dnevnikEntity.setUcenik(ucenikEntity);
		}
		if (dnevnikDTO.getPredmetId() != null) {
			PredmetEntity predmetEntity = predmetRepository.findById(dnevnikDTO.getPredmetId()).orElse(null);
			if (predmetEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Predmet nije pronađen u DB-u.");
			}
			if (!predajeRepository.existsByNastavnikAndPredmet(nastavnikEntity, predmetEntity)) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Predmet nije registrovan kod nastavnika.");
			}
			dnevnikEntity.setPredmet(predmetEntity);
			dnevnikEntity.setRazred(predmetEntity.getRazred());
		}
		if (dnevnikDTO.getPolugodiste() != null) {
			if (dnevnikDTO.getPolugodiste() < 1 || dnevnikDTO.getPolugodiste() > 2) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Dozvoljene vrednosti za polugodište su brojevi 1 i 2.");
			}
			dnevnikEntity.setPolugodiste(dnevnikDTO.getPolugodiste());
		}
		if (dnevnikDTO.getOcena() != null) {
			if (dnevnikDTO.getOcena() < 1 || dnevnikDTO.getOcena() > 5) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Dozvoljene vrednosti za ocenu su brojevi 1 - 5.");
			}
			promenaOcene = true;
			dnevnikEntity.setOcena(dnevnikDTO.getOcena());
		}
		
		// Snimam u DB.
		try {
			dnevnikRepository.save(dnevnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB: " + e.getMessage());
		}
		
		// Obavestavam roditelja o promeni ocene ako je promenjena ocena
		if (promenaOcene) {
			emailService.sendSimpleEmailMessage(dnevnikEntity.getUcenik().getRoditelj().getEposta(),
					"Obaveštenje o promeni ocene za učenicu/ka " + dnevnikEntity.getUcenik().getKorisnik().getIme()
					+ " " + dnevnikEntity.getUcenik().getKorisnik().getPrezime(),
					"Učenici/ku " + dnevnikEntity.getUcenik().getKorisnik().getIme() + " "
					+ dnevnikEntity.getUcenik().getKorisnik().getPrezime() + " je promenjena ocena na "
					+ dnevnikEntity.getOcena() + " iz predmeta "
					+ dnevnikEntity.getPredmet().getNaziv() + ". "
					+ "Ukoliko želite više detalja o dobijenoj oceni, kontaktirajte nastavnicu/ka "
					+ nastavnikEntity.getKorisnik().getIme() + " " + nastavnikEntity.getKorisnik().getPrezime() + ".");
			logger.info("Obavestavam roditelja o promeni ocene.");
		}
		
		// Instanciram  i prepakujem u povratni DTO.
		DnevnikDTOreturn dnevnikDTOreturn = new DnevnikDTOreturn();
		dnevnikDTOreturn.setDnevnik_id(dnevnikEntity.getDnevnik_id());
		dnevnikDTOreturn.setDatum(dnevnikEntity.getDatum());
		dnevnikDTOreturn.setUcenikId(dnevnikEntity.getUcenik().getUcenik_id());
		dnevnikDTOreturn.setPredmetId(dnevnikEntity.getPredmet().getPredmet_id());
		dnevnikDTOreturn.setNastavnikId(dnevnikEntity.getNastavnik().getNastavnik_id());
		dnevnikDTOreturn.setRoditeljId(dnevnikEntity.getUcenik().getRoditelj().getRoditelj_id());
		dnevnikDTOreturn.setRazred(dnevnikEntity.getRazred());
		dnevnikDTOreturn.setPolugodiste(dnevnikEntity.getPolugodiste());
		dnevnikDTOreturn.setOcena(dnevnikEntity.getOcena());

		return ResponseEntity.status(HttpStatus.OK).body(dnevnikDTOreturn);
	}

	@Override
	public ResponseEntity<?> delete(HttpServletRequest request, Long id) {
		
		logger.info("Pozvan je metod delete() sa ID {}", id);

		// Instanciram nastavnikEntity.
		NastavnikEntity nastavnikEntity = new NastavnikEntity();
		try {
			nastavnikEntity = nastavnikRepository.findByKorisnickoIme(request.getUserPrincipal().getName());
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom instanciranja nastavnika: {}", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom instanciranja nastavnika: " + e.getMessage());
		}

		// Instanciram dnevnikEntity ako postoji.
		DnevnikEntity dnevnikEntity = dnevnikRepository.findById(id).orElse(null);
		if (dnevnikEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dnevnik nije pronađen u DB-u.");
		}
		if (!dnevnikEntity.getNastavnik().equals(nastavnikEntity)) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Dnevnik nije registrovan kod logovanog nastavnika.");
		}
		
	    // Brisem iz DB-a.
		try {
			dnevnikRepository.delete(dnevnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom brisanja: {}", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Dnevnik je pronađen ali se dogodila neočekivana greška prilikom "
							+ "brisanja podataka u DB-u: " + e.getMessage());
		}

		// Instanciram  i prepakujem u povratni DTO.
		DnevnikDTOreturn dnevnikDTOreturn = new DnevnikDTOreturn();
		dnevnikDTOreturn.setDnevnik_id(dnevnikEntity.getDnevnik_id());
		dnevnikDTOreturn.setDatum(dnevnikEntity.getDatum());
		dnevnikDTOreturn.setUcenikId(dnevnikEntity.getUcenik().getUcenik_id());
		dnevnikDTOreturn.setPredmetId(dnevnikEntity.getPredmet().getPredmet_id());
		dnevnikDTOreturn.setNastavnikId(dnevnikEntity.getNastavnik().getNastavnik_id());
		dnevnikDTOreturn.setRoditeljId(dnevnikEntity.getUcenik().getRoditelj().getRoditelj_id());
		dnevnikDTOreturn.setRazred(dnevnikEntity.getRazred());
		dnevnikDTOreturn.setPolugodiste(dnevnikEntity.getPolugodiste());
		dnevnikDTOreturn.setOcena(dnevnikEntity.getOcena());

		return ResponseEntity.status(HttpStatus.OK).body(dnevnikDTOreturn);
	}
	// ----------------------------------------------------------------------------------------------------

	@Override
	public ResponseEntity<?> listByParam(HttpServletRequest request, String datum, String datum_do,
			Long ucenik_id, Long predmet_id, Integer razred, Integer polugodiste, Integer ocena) {

		logger.info("Pozvana metoda listByParams() sa parametrima datum: {}, datum_do: {}, "
				+ "ucenik_id: {}, predmet_id: {}, razred: {}, polugodiste: {}, ocena: {}",
				datum, datum_do, ucenik_id, predmet_id, razred, polugodiste, ocena);

		// Instanciram nastavnikEntity.
		NastavnikEntity nastavnikEntity = new NastavnikEntity();
		try {
			nastavnikEntity = nastavnikRepository.findByKorisnickoIme(request.getUserPrincipal().getName());
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom instanciranja nastavnika: " + e.getMessage());
		}
		
		// Validiram prosledjene parametre i dinamicki sastavljam hql string.
		StringBuilder hqlString = new StringBuilder("SELECT d FROM DnevnikEntity d WHERE d.nastavnik.id = :nastavnik_id");

		// datum
		LocalDate parsedDatum = null;
		LocalDate parsedDatumDo = null;
		if (datum != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
			try {
				parsedDatum = LocalDate.parse(datum, formatter);
			} catch (Exception e) {
				try {
					formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
					parsedDatum = LocalDate.parse(datum, formatter);
				} catch (Exception e2) {
					return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body("Datum u polju \"datum\" nije mogao da se parsira. "
									+ "Potreban format je \"yyyy-MM-dd\": " + e.getMessage());
				}
			}
			parsedDatumDo = parsedDatum;
			if (datum_do != null) {
				try {
					parsedDatumDo = LocalDate.parse(datum_do, formatter);
				} catch (Exception e) {
					try {
						formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
						parsedDatumDo = LocalDate.parse(datum_do, formatter);
					} catch (Exception e2) {
						return ResponseEntity
								.status(HttpStatus.BAD_REQUEST)
								.body("Datum u polju \"datum_do\" nije mogao da se parsira. "
										+ "Potreban format je \"yyyy-MM-dd\": " + e.getMessage());
					}
				}
			}
            hqlString.append(" AND d.datum BETWEEN :parsedDatum AND :parsedDatumDo");
        }
		
		// ucenik
		if (ucenik_id != null) {
			UcenikEntity ucenikEntity = ucenikRepository.findById(ucenik_id).orElse(null);
			if (ucenikEntity == null) {
			    return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body("Učenik nije pronađen u DB-u.");
			}
			List<DnevnikEntity> dnevniciUcenikList =
					dnevnikRepository.findByUcenikAndNastavnik(ucenikEntity, nastavnikEntity);
			if (dnevniciUcenikList.size() < 1) {
				return ResponseEntity
						.status(HttpStatus.PAYMENT_REQUIRED)
						.body("Traženi učenik nije registrovan kod logovanog nastavnika.\n"
								+ "(ali sa adminima oko svega može da padne dogovor;)");
			}
			if (dnevniciUcenikList.size() > 1) {
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Traženi predmet je registrovan kod logovanog nastavnika u " + dnevniciUcenikList.size()
								+ " motorke/i.\nPotrebno je ručno pročešljati bazu za duplikatima i naterati"
								+ " programera da propisno uradi aplikaciju.");
			}
            hqlString.append(" AND d.ucenik.id = :ucenik_id");
		}

		// predmet
		if (predmet_id != null) {
			PredmetEntity predmetEntity = predmetRepository.findById(predmet_id).orElse(null);
			if (predmetEntity == null) {
			    return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body("Predmet nije pronađen u DB-u.");
			}
			List<DnevnikEntity> dnevniciPredajeList =
					dnevnikRepository.findByPredmetAndNastavnik(predmetEntity, nastavnikEntity);
			if (dnevniciPredajeList.size() < 1) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body("Traženi predmet nije registrovan kod logovanog nastavnika.");
			}
			if (dnevniciPredajeList.size() > 1) {
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Traženi predmet je registrovan kod logovanog nastavnika u " + dnevniciPredajeList.size()
								+ " motorke/i.\nPotrebno je ručno pročešljati bazu za duplikatima i naterati"
								+ " programera da propisno uradi aplikaciju.");
			}
            hqlString.append(" AND d.predmet.id = :predmet_id");
		}

		// razred
		if (razred != null) {
			if (razred < 1 || razred > 8) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Dozvoljene vrednosti za razred su brojevi 1 - 8.");
			}
            hqlString.append(" AND d.razred = :razred");
        }

		// polugodiste
		if (polugodiste != null) {
			if (polugodiste < 1 || polugodiste > 2) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Dozvoljene vrednosti za polugodište su brojevi 1 i 2.");
			}
            hqlString.append(" AND d.polugodiste = :polugodiste");
        }

		// ocena
		if (ocena != null) {
			if (ocena < 1 || ocena > 5) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Dozvoljene vrednosti za ocenu su brojevi 1 - 5.");
			}
            hqlString.append(" AND d.ocena = :ocena");
        }

        // Kreiram query i dinamicki setujem parametre.
        TypedQuery<DnevnikEntity> dnevnikQuery = entityManager.createQuery(hqlString.toString(),
        		DnevnikEntity.class);
        dnevnikQuery.setParameter("nastavnik_id", nastavnikEntity.getNastavnik_id());
        if (datum != null) {
            dnevnikQuery.setParameter("parsedDatum", parsedDatum);
        	dnevnikQuery.setParameter("parsedDatumDo", parsedDatumDo);
        }
        if (ucenik_id != null) {
        	dnevnikQuery.setParameter("ucenik_id", ucenik_id);
        }
        if (predmet_id != null) {
            dnevnikQuery.setParameter("predmet_id", predmet_id);
        }
        if (razred != null) {
            dnevnikQuery.setParameter("razred", razred);
        }
        if (polugodiste != null) {
            dnevnikQuery.setParameter("polugodiste", polugodiste);
        }
        if (ocena != null) {
            dnevnikQuery.setParameter("ocena", ocena);
        }
		
		// Instanciram povratnu listu dnevnika.
		List<DnevnikDTOreturn> dnevniciDTOList = new ArrayList<DnevnikDTOreturn>();
        
		// Instanciram listu dnevnika i prepakujem u povratnu listu.
        List<DnevnikEntity> dnevniciList = dnevnikQuery.getResultList() ;
		for (int i = 0; i < dnevniciList.size(); i++) {
			DnevnikDTOreturn dnevnikDTOreturn = new DnevnikDTOreturn();
			dnevnikDTOreturn.setDnevnik_id(dnevniciList.get(i).getDnevnik_id());
			dnevnikDTOreturn.setDatum(dnevniciList.get(i).getDatum());
			dnevnikDTOreturn.setUcenikId(dnevniciList.get(i).getUcenik().getUcenik_id());
			dnevnikDTOreturn.setPredmetId(dnevniciList.get(i).getPredmet().getPredmet_id());
			dnevnikDTOreturn.setNastavnikId(dnevniciList.get(i).getNastavnik().getNastavnik_id());
			dnevnikDTOreturn.setRoditeljId(dnevniciList.get(i).getUcenik().getRoditelj().getRoditelj_id());
			dnevnikDTOreturn.setRazred(dnevniciList.get(i).getRazred());
			dnevnikDTOreturn.setPolugodiste(dnevniciList.get(i).getPolugodiste());
			dnevnikDTOreturn.setOcena(dnevniciList.get(i).getOcena());
			dnevniciDTOList.add(dnevnikDTOreturn);
		}

		return ResponseEntity.status(HttpStatus.OK).body(dnevniciDTOList);
	}
}
