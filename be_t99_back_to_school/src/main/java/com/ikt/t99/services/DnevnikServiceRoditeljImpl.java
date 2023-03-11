package com.ikt.t99.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.ikt.t99.dtos.DnevnikDTOreturn;
import com.ikt.t99.entities.DnevnikEntity;
import com.ikt.t99.entities.RoditeljEntity;
import com.ikt.t99.entities.UcenikEntity;
import com.ikt.t99.repositories.NastavnikRepository;
import com.ikt.t99.repositories.PredmetRepository;
import com.ikt.t99.repositories.RoditeljRepository;
import com.ikt.t99.repositories.UcenikRepository;

@Service
public class DnevnikServiceRoditeljImpl implements DnevnikServiceRoditelj {

	private final Logger logger = LoggerFactory.getLogger(DnevnikServiceRoditeljImpl.class);

	@Autowired
	private RoditeljRepository roditeljRepository;
	@Autowired
	private UcenikRepository ucenikRepository;
	@Autowired
	private PredmetRepository predmetRepository;
	@Autowired
	private NastavnikRepository nastavnikRepository;

	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public ResponseEntity<?> listByParam(HttpServletRequest request, String datum, String datum_do,
			Long ucenik_id, Long predmet_id, Long nastavnik_id, Integer razred, Integer polugodiste, Integer ocena) {

		logger.info("Pozvana metoda listByParams() sa parametrima datum: {}, datum_do: {}, "
				+ "ucenik_id: {}, predmet_id: {}, razred: {}, polugodiste: {}, ocena: {}",
				datum, datum_do, ucenik_id, predmet_id, razred, polugodiste, ocena);

		// Instanciram roditelja i listu id-ova dece.
		RoditeljEntity roditeljEntity = new RoditeljEntity();
		try {
			roditeljEntity = roditeljRepository.findByKorisnickoIme(request.getUserPrincipal().getName());
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom instanciranja roditelja: " + e.getMessage());
		}
		List<Long> decaIds = new ArrayList<Long>();
		if (ucenik_id != null) {
			UcenikEntity ucenikEntity = ucenikRepository.findById(ucenik_id).orElse(null);
			if (ucenikEntity == null) {
			    return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body("Učenik nije pronađen u DB-u.");
			}
			if (!ucenikEntity.getRoditelj().equals(roditeljEntity)) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Učenik nije dete logovanog roditelja.");
			}
			decaIds.add(ucenikEntity.getUcenik_id());
		}
		else {
			decaIds = roditeljEntity.getDeca()
				    .stream()
				    .map(UcenikEntity::getUcenik_id)
				    .collect(Collectors.toList());
		}
		if (decaIds.size() < 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Roditelj nema registrovanu decu.");
		}
		
		// Validiram prosledjene parametre i dinamicki sastavljam hql string.
		StringBuilder hqlString = new StringBuilder("SELECT d FROM DnevnikEntity d WHERE d.ucenik.id IN :deca_ids");

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
        dnevnikQuery.setParameter("deca_ids", decaIds);
        if (datum != null) {
            dnevnikQuery.setParameter("parsedDatum", parsedDatum);
        	dnevnikQuery.setParameter("parsedDatumDo", parsedDatumDo);
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
			dnevnikDTOreturn.setUcenikId(dnevniciList.get(i).getUcenik().getUcenik_id());
			dnevnikDTOreturn.setDnevnik_id(dnevniciList.get(i).getDnevnik_id());
			dnevnikDTOreturn.setDatum(dnevniciList.get(i).getDatum());
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
