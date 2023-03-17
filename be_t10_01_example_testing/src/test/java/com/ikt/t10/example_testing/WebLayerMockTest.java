package com.ikt.t10.example_testing;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ikt.t10.example_testing.controllers.GreetingController;
import com.ikt.t10.example_testing.services.GreetingService;

@WebMvcTest(GreetingController.class)
public class WebLayerMockTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GreetingService greetingService;
	
	@Test
	public void greetingsShouldReturnAMessage() throws Exception {
		when(greetingService.greet()).thenReturn("Hello Vladimir");
		this.mockMvc.perform(get("/greeting"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Hello Vladimir")));
	}
}
