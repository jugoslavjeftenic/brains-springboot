package com.ikt.t99.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.ikt.t99.dtos.LoginDTO;
import com.ikt.t99.entities.AdministratorEntity;
import com.ikt.t99.entities.KorisnikEntity;
import com.ikt.t99.entities.NastavnikEntity;
import com.ikt.t99.entities.RoditeljEntity;
import com.ikt.t99.entities.UcenikEntity;
import com.ikt.t99.repositories.AdministratorRepository;
import com.ikt.t99.repositories.KorisnikRepository;
import com.ikt.t99.repositories.NastavnikRepository;
import com.ikt.t99.repositories.RoditeljRepository;
import com.ikt.t99.repositories.UcenikRepository;
import com.ikt.t99.security.config.JWTAuthorizationFilter;
import com.ikt.t99.security.util.Encryption;

import io.jsonwebtoken.Jwts;

@Service
public class LoginServiceImpl implements LoginService {

	private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private NastavnikRepository nastavnikRepository;
	@Autowired
	private RoditeljRepository roditeljRepository;
	@Autowired
	private UcenikRepository ucenikRepository;
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	@Autowired
	private SecretKey secretKey;
	
	private String getJWToken(Object korisnik) {
		
		logger.info("Pozvan je metod getJWToken() sa objektom Object {}", korisnik);
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		String token = null;
		if (korisnik instanceof AdministratorEntity) {
			grantedAuthorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ULOGA_ADMINISTRATOR"));
			token = Jwts.builder()
					.setId("softtekJWT")
					.setSubject(((AdministratorEntity) korisnik).getKorisnickoIme())
					.claim(
							JWTAuthorizationFilter.AUTHORITIES,
							grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
					.signWith(this.secretKey)
					.compact();
		}
		if (korisnik instanceof NastavnikEntity) {
			grantedAuthorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ULOGA_NASTAVNIK"));
			token = Jwts.builder()
					.setId("softtekJWT")
					.setSubject(((NastavnikEntity) korisnik).getKorisnickoIme())
					.claim(
							JWTAuthorizationFilter.AUTHORITIES,
							grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
					.signWith(this.secretKey)
					.compact();
		}
		if (korisnik instanceof RoditeljEntity) {
			grantedAuthorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ULOGA_RODITELJ"));
			token = Jwts.builder()
					.setId("softtekJWT")
					.setSubject(((RoditeljEntity) korisnik).getKorisnickoIme())
					.claim(
							JWTAuthorizationFilter.AUTHORITIES,
							grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
					.signWith(this.secretKey)
					.compact();
		}
		if (korisnik instanceof UcenikEntity) {
			grantedAuthorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ULOGA_UCENIK"));
			token = Jwts.builder()
					.setId("softtekJWT")
					.setSubject(((UcenikEntity) korisnik).getKorisnickoIme())
					.claim(
							JWTAuthorizationFilter.AUTHORITIES,
							grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
					.signWith(this.secretKey)
					.compact();
		}
		return "Bearer " + token;
	}
	
	@Override
	public ResponseEntity<?> checkAndLoginUser(String username, String password, HttpSession session) {

		logger.info("Pozvan je metod checkAndLoginUser() sa username {}", username);

		if (username.startsWith("adm.")) {
			AdministratorEntity administratorEntity = administratorRepository.findByKorisnickoIme(username);
			if (administratorEntity != null && Encryption.validatePassword(password, administratorEntity.getLozinka())) {
				LoginDTO loginDTO = new LoginDTO(username, getJWToken(administratorEntity));
				session.setAttribute("user_id", administratorEntity.getAdministrator_id());
				return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
			}
		}
		if (username.startsWith("nst.")) {
			NastavnikEntity nastavnikEntity = nastavnikRepository.findByKorisnickoIme(username);
			if (nastavnikEntity != null && Encryption.validatePassword(password, nastavnikEntity.getLozinka())) {
				LoginDTO loginDTO = new LoginDTO(username, getJWToken(nastavnikEntity));
				session.setAttribute("user_id", nastavnikEntity.getNastavnik_id());
				return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
			}
		}
		if (username.startsWith("rdt.")) {
			RoditeljEntity roditeljEntity = roditeljRepository.findByKorisnickoIme(username);
			if (roditeljEntity != null && Encryption.validatePassword(password, roditeljEntity.getLozinka())) {
				LoginDTO loginDTO = new LoginDTO(username, getJWToken(roditeljEntity));
				session.setAttribute("user_id", roditeljEntity.getRoditelj_id());
				return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
			}
		}
		if (username.startsWith("ucn.")) {
			UcenikEntity ucenikEntity = ucenikRepository.findByKorisnickoIme(username);
			if (ucenikEntity != null && Encryption.validatePassword(password, ucenikEntity.getLozinka())) {
				LoginDTO loginDTO = new LoginDTO(username, getJWToken(ucenikEntity));
				session.setAttribute("user_id", ucenikEntity.getUcenik_id());
				return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pogrešne kredencije!");
	}

	@Override
	public ResponseEntity<?> napraviZelenog() {

		logger.info("Pozvan je metod napraviZelenog()");

		if (!administratorRepository.existsByKorisnickoIme("adm.zeleni")) {
			List<KorisnikEntity> korisniciList = korisnikRepository.findByJmbg("0000000000000");
			if (korisniciList.size() < 1) {
				KorisnikEntity korisnikEntity = new KorisnikEntity();
				korisnikEntity.setJmbg("0000000000000");
				korisnikEntity.setIme("Zeleni Zub");
				korisnikEntity.setPrezime("Superoperater");
				try {
					korisnikRepository.save(korisnikEntity);
				} catch (Exception e) {
					return ResponseEntity
							.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Došlo je do greške prilikom upisa podataka u DB." + e.getMessage());
				}
				korisniciList.add(korisnikEntity);
			}
			AdministratorEntity administratorEntity = new AdministratorEntity();
			administratorEntity.setKorisnickoIme("adm.zeleni");
			administratorEntity.setLozinka("{bcrypt}" + Encryption.getEncodedPassword("lozinka"));
			administratorEntity.setKorisnik(korisniciList.get(0));
			try {
				administratorRepository.save(administratorEntity);
			} catch (Exception e) {
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Došlo je do greške prilikom upisa podataka u DB." + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.CREATED).body("Zeleni Zub ponovo jaše!");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Zeleni Zub prezire slavu!");
	}
}
