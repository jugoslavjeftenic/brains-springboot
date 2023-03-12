package com.ikt.t10.example_testing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ikt.t10.example_testing.controllers.HomeController;

@SpringBootTest
public class AssertExampleTest {

	@Autowired
	private HomeController homeController;
	
	@Test
	public void contextLoads() {
		assertThat(homeController).isNotNull();
	}
}
