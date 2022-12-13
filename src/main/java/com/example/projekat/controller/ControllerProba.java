package com.example.projekat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.entities.Patient;
import com.example.projekat.repository.IPatientService;
import com.example.projekat.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:8080")
public class ControllerProba {

	@Autowired
	UserRepository ur;
	
	@Autowired
	IPatientService patientService;

	@RequestMapping("/proba")
	public String proba() {
		return "prolazi";
	}
	
//	@RequestMapping("/probaTomcat")
//	public String probaTomcat() {
//		return "probaTomcat";
//	}
	
	@RequestMapping("/probaTomcat")
	public List<Patient> getPaginatedPatients(@RequestParam(name="pageNo") int pageNo, @RequestParam(name="pageSize") int pageSize) {
		return patientService.findPaginated(pageNo, pageSize);
	}
}
