package com.iktpreobuka.data_examples;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ikt.t10_02.example_data_testing.entities.AddressEntity;
import com.ikt.t10_02.example_data_testing.entities.UserEntity;
import com.ikt.t10_02.example_data_testing.repositories.AddressRepository;
import com.ikt.t10_02.example_data_testing.repositories.CityRepository;
import com.ikt.t10_02.example_data_testing.repositories.UserRepository;

@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CityRepository cityRepository;
	
	private AddressEntity addressEntity;
	private List<UserEntity> userEntities = new ArrayList<>();
	
	private static boolean dbInit = false;
	
	@BeforeEach
	public void setup() {
		if (!dbInit) {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
			
			this.addressEntity = this.addressRepository.save(new AddressEntity("Uraa's domain",
					cityRepository.findById(23).orElse(null)));

			String[][] users = {
					{"Uraa", "uraa@ikt.rs", "1972-09-16", "064-1234-567", "1234567890123", "012345678"},
					{"Uraa's left Hand", "uraa_lh@ikt.rs", "1975-09-16", "064-1234-568", "1234567890124", "012345679"},
					{"Uraa's right Hand", "uraa_rh@ikt.rs", "1974-09-16", "064-1234-569", "1234567890125", "012345670"}
			};
			for (int i =0; i < 3; i++) {
				UserEntity user = new UserEntity();
				user.setName(users[i][0]);
				user.setEmail(users[i][1]);
				user.setBirthDate(LocalDate.parse(users[i][2]));
				user.setPhoneNumber(users[i][3]);
				user.setJmbg(users[i][4]);
				user.setRegBrLk(users[i][5]);
				user.setAddress(addressEntity);
				this.userEntities.add(this.userRepository.save(user));
			}
			dbInit = true;
		}
	}
	
	@Test
	public void checkIfUserServiceIsFound() throws Exception {
		this.mockMvc.perform(get("/api/v1/users")).andExpect(status().isOk());
	}
}
