package com.example.projekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:8080")
public class ControllerProba {

	@Autowired
	UserRepository ur;

	@RequestMapping("/proba")
	public String proba() {
		return "prolazi";
	}
}
