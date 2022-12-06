package com.example.projekat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping("/proba")
	public String proba() {
		return "prolazi";
	}
}
