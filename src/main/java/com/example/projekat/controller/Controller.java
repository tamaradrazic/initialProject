package com.example.projekat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:8080")
public class Controller {

	@RequestMapping("/proba")
	public String proba() {
		return "prolazi";
	}
}
