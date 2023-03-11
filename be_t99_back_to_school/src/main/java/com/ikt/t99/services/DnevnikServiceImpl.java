package com.ikt.t99.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.DnevnikDTO;
import com.ikt.t99.dtos.DnevnikDTOreturn;
import com.ikt.t99.entities.DnevnikEntity;
import com.ikt.t99.entities.NastavnikEntity;
import com.ikt.t99.entities.PredajeEntity;
import com.ikt.t99.entities.PredmetEntity;
import com.ikt.t99.entities.UcenikEntity;
import com.ikt.t99.repositories.DnevnikRepository;
import com.ikt.t99.repositories.NastavnikRepository;
import com.ikt.t99.repositories.PredajeRepository;
import com.ikt.t99.repositories.PredmetRepository;
import com.ikt.t99.repositories.UcenikRepository;

import rade.RADE;

@Service
public class DnevnikServiceImpl implements DnevnikService {

	private final Logger logger = LoggerFactory.getLogger(DnevnikServiceImpl.class);

	@Autowired
	private DnevnikRepository dnevnikRepository;
	@Autowired
	private UcenikRepository ucenikRepository;
	@Autowired
	private NastavnikRepository nastavnikRepository;
	@Autowired
	private PredajeRepository predajeRepository;
	@Autowired
	private PredmetRepository predmetRepository;

	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ResponseEntity<?> create(DnevnikDTO dnevnikDTO) {
		
		logger.info("Pozvan je metod create() sa objektom DnevnikDTO {}", dnevnikDTO);

		// Instanciram ucenikEntity ako postoji.
		UcenikEntity ucenikEntity = ucenikRepository.findById(dnevnikDTO.getUcenikId()).orElse(null);
		if (ucenikEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ucenik nije pronađen u DB-u.");
		}
		
		// Instanciram nastavnikEntity ako postoji.
		NastavnikEntity nastavnikEntity = nastavnikRepository.findById(dnevnikDTO.getNastavnikId()).orElse(null);
		if (nastavnikEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nastavnik nije pronađen u DB-u.");
		}
		
		// Instanciram predmetEntity ako postoji.
		PredmetEntity predmetEntity = predmetRepository.findById(dnevnikDTO.getPredmetId()).orElse(null);
		if (predmetEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Predmet nije pronađen u DB-u.");
		}
		
		// Instanciram dnevnikEntity i setujem vrednosti.
		DnevnikEntity dnevnikEntity = new DnevnikEntity();
		dnevnikEntity.setDatum(dnevnikDTO.getDatum());
		dnevnikEntity.setUcenik(ucenikEntity);
		dnevnikEntity.setPredmet(predmetEntity);
		dnevnikEntity.setNastavnik(nastavnikEntity);
		dnevnikEntity.setRazred(predmetEntity.getRazred());
		dnevnikEntity.setPolugodiste(dnevnikDTO.getPolugodiste());
		dnevnikEntity.setOcena(dnevnikDTO.getOcena());
		
		// Snimam u DB.
		try {
			logger.info("Dnevnik je uspešno kreiran: {}", dnevnikDTO);
			dnevnikRepository.save(dnevnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB: " + e.getMessage());
		}
		
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
	public ResponseEntity<?> read(Long id) {

		// Instanciram dnevnikEntity ako postoji.
		DnevnikEntity dnevnikEntity = dnevnikRepository.findById(id).orElse(null);
		if (dnevnikEntity == null) {
			logger.warn("Dnevnik sa ID {} nije pronađen u DB-u.", id);
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dnevnik nije pronađen u DB-u.");
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
	public ResponseEntity<?> update(Long id, DnevnikDTO dnevnikDTO) {

		logger.info("Pozvan je metod update() sa ID {} i objektom DnevnikDTO {}", id, dnevnikDTO);

		// Instanciram dnevnikEntity ako postoji.
		DnevnikEntity dnevnikEntity = dnevnikRepository.findById(id).orElse(null);
		if (dnevnikEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dnevnik nije pronađen u DB-u.");
		}

		// Validiram vrednosti polja u telu zahteva i setujem nove vrednosti.
		if (dnevnikDTO.getDatum() != null && !dnevnikDTO.getDatum().isAfter(LocalDate.now())) {
			dnevnikEntity.setDatum(dnevnikDTO.getDatum());
		}
		if (dnevnikDTO.getUcenikId() != null) {
			UcenikEntity ucenikEntity = ucenikRepository.findById(dnevnikDTO.getUcenikId()).orElse(null);
			if (ucenikEntity == null) {
			    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Učenik nije pronađen u DB-u.");
			}
			dnevnikEntity.setUcenik(ucenikEntity);
		}
		if (dnevnikDTO.getPredmetId() != null) {
			PredmetEntity predmetEntity = predmetRepository.findById(dnevnikDTO.getPredmetId()).orElse(null);
			if (predmetEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Predmet nije pronađen u DB-u.");
			}
			dnevnikEntity.setPredmet(predmetEntity);
			dnevnikEntity.setRazred(predmetEntity.getRazred());
		}
		if (dnevnikDTO.getNastavnikId() != null) {
			NastavnikEntity nastavnikEntity = nastavnikRepository.findById(dnevnikDTO.getNastavnikId()).orElse(null);
			if (nastavnikEntity == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nastavnik nije pronađen u DB-u.");
			}
			dnevnikEntity.setNastavnik(nastavnikEntity);
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
			dnevnikEntity.setOcena(dnevnikDTO.getOcena());
		}
		
		// Snimam u DB.
		try {
			dnevnikRepository.save(dnevnikEntity);
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB.", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do neočekivane greške prilikom upisa podataka u DB: " + e.getMessage());
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
	public ResponseEntity<?> delete(Long id) {

		logger.info("Pozvan je metod delete() sa ID-om {}.", id);

		// Instanciram dnevnikEntity ako postoji.
		DnevnikEntity dnevnikEntity = dnevnikRepository.findById(id).orElse(null);
		if (dnevnikEntity == null) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dnevnik nije pronađen u DB-u.");
		}
		
	    // Brisem iz DB-a.
		try {
			dnevnikRepository.delete(dnevnikEntity);
		} catch (Exception e) {
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
	public ResponseEntity<?> listByParam(String datum, String datum_do, Long ucenik_id, Long predmet_id,
			Long nastavnik_id, Integer razred, Integer polugodiste, Integer ocena) {

		logger.info("Pozvana metoda listByParams() sa parametrima datum: {}, datum_do: {}, "
				+ "ucenik_id: {}, predmet_id: {}, nastavnik_id: {}, razred: {}, polugodiste: {}, ocena: {}",
				datum, datum_do, ucenik_id, predmet_id, nastavnik_id, razred, polugodiste, ocena);

		// Validiram prosledjene parametre i dinamicki sastavljam hql string.
		StringBuilder hqlString = new StringBuilder("SELECT d FROM DnevnikEntity d WHERE 1 = 1");

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
			if (ucenikRepository.existsById(ucenik_id)) {
			    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Učenik nije pronađen u DB-u.");
			}
            hqlString.append(" AND d.ucenik.id = :ucenik_id");
		}

		// predmet
		if (predmet_id != null) {
			if (!predmetRepository.existsById(predmet_id)) {
			    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Predmet nije pronađen u DB-u.");
			}
            hqlString.append(" AND d.predmet.id = :predmet_id");
		}

		// nastavnik
        if (nastavnik_id != null) {
    		if (!nastavnikRepository.existsById(nastavnik_id)) {
    		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nastavnik nije pronađen u DB-u.");
    		}
            hqlString.append(" AND d.nastavnik.id = :nastavnik_id");
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
        if (nastavnik_id != null) {
        	dnevnikQuery.setParameter("nastavnik_id", nastavnik_id);
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

	@Override
	public ResponseEntity<?> dummy(Integer count) {
		
		logger.info("Pozvana metoda dummy() sa brojem: {}", count);

		if (count < 1) {
			count = 1;
		}
		if (count > 1000) {
			count = 1000;
		}
		List<DnevnikEntity> dnevniciList = new ArrayList<DnevnikEntity>();
		List<UcenikEntity> uceniciList = (List<UcenikEntity>) ucenikRepository.findAll();
		for (int i = 0; i < count; i++) {
			DnevnikEntity dnevnikEntity = new DnevnikEntity();
			dnevnikEntity.setDatum(LocalDate.now().minusDays(RADE.mrRobot(0, 100)));
			dnevnikEntity.setUcenik(uceniciList.get(RADE.mrRobot(0, uceniciList.size())));
			List<PredmetEntity> predmetiList =
					predmetRepository.findByRazred(dnevnikEntity.getUcenik().getRazred());
			dnevnikEntity.setPredmet(predmetiList.get(RADE.mrRobot(0, predmetiList.size())));
			List<PredajeEntity> predajeList = predajeRepository.findByPredmet(dnevnikEntity.getPredmet());
			if (predajeList.size() < 1) {
				continue;
			}
			dnevnikEntity.setNastavnik(predajeList.get(RADE.mrRobot(0, predajeList.size())).getNastavnik());
			dnevnikEntity.setRazred(dnevnikEntity.getUcenik().getRazred());
			dnevnikEntity.setPolugodiste(dnevnikEntity.getUcenik().getPolugodiste());
			dnevnikEntity.setOcena(RADE.mrRobot(1, 6));
			dnevniciList.add(dnevnikEntity);
		}
		try {
			dnevnikRepository.saveAll(dnevniciList);
			logger.info("Dnevnici su uspešno kreirani.");
		} catch (Exception e) {
			logger.error("Došlo je do greške prilikom upisa podataka u DB: {}", e.getMessage());
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom upisa podataka u DB: " + e.getMessage());
		}
		
		// Instanciram povratnu listu  i prepakujem u povratni DTO.
		List<DnevnikDTOreturn> dnevniciListDTOreturn = new ArrayList<>();
		for (int i = 0; i < dnevniciList.size(); i++) {
			DnevnikDTOreturn dnevnikDTOreturn = new DnevnikDTOreturn();
			dnevnikDTOreturn.setDatum(dnevniciList.get(i).getDatum());
			dnevnikDTOreturn.setUcenikId(dnevniciList.get(i).getUcenik().getUcenik_id());
			dnevnikDTOreturn.setPredmetId(dnevniciList.get(i).getPredmet().getPredmet_id());
			dnevnikDTOreturn.setNastavnikId(dnevniciList.get(i).getNastavnik().getNastavnik_id());
			dnevnikDTOreturn.setRoditeljId(dnevniciList.get(i).getUcenik().getRoditelj().getRoditelj_id());
			dnevnikDTOreturn.setRazred(dnevniciList.get(i).getRazred());
			dnevnikDTOreturn.setPolugodiste(dnevniciList.get(i).getPolugodiste());
			dnevnikDTOreturn.setOcena(dnevniciList.get(i).getOcena());
			dnevniciListDTOreturn.add(dnevnikDTOreturn);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(dnevniciListDTOreturn);
	}
}
