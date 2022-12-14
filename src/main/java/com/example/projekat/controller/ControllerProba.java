package com.example.projekat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
	
	@RequestMapping(value = "/paginationXML", produces = {MediaType.APPLICATION_XML_VALUE}, headers = "Accept=application/xml")
	public List<Patient> getPaginatedPatients(@RequestParam(name="pageNo") int pageNo, @RequestParam(name="pageSize") int pageSize) {
		return patientService.findPaginated(pageNo, pageSize);
	}
	
	@RequestMapping(value = "/sorting")
	public List<Patient> getSortedPatients(@RequestParam(name="sort") String sort){
		return patientService.sorting(sort);
	}
	
	@RequestMapping(value = "/paginationAndSorting")
	public List<Patient> getPaginatedPatients(@RequestParam(name="pageNo") int pageNo, @RequestParam(name="pageSize") int pageSize, @RequestParam(name="sort") String sort) {
		return patientService.findPaginatedAndSort(pageNo, pageSize, sort);
	}
	
	@RequestMapping(value = "/searchAndPaging")
	public List<Patient> searchAndPaging(@RequestParam(name="pageNo") int pageNo, @RequestParam(name="pageSize") int pageSize, @RequestParam(name="age") int age){
		return patientService.searchAndPaging(pageNo, pageSize, age);
	}
}