package com.ikt.t10.example_testing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikt.t10.example_testing.services.GreetingService;

@RestController
public class GreetingController {

	@Autowired
	private GreetingService greetingService;
	
	@GetMapping("/greeting")
	public String greeting() {
		return greetingService.greet();
	}
}
