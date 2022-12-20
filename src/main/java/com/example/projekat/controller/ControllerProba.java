package com.example.projekat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.entities.Patient;
import com.example.projekat.repository.IPatientService;
import com.example.projekat.repository.PatientRepository;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.PatientServ;

@RestController
@CrossOrigin("http://localhost:8080")
public class ControllerProba {

	@Autowired
	UserRepository ur;
	
	@Autowired
	PatientRepository pr;
	
	@Autowired
	IPatientService patientService;
	
	@Autowired
    private PatientServ personService;
 

	@RequestMapping("/proba")
	public String proba() {
		return "prolazi";
	}
	
//	@RequestMapping("/probaTomcat")
//	public String probaTomcat() {
//		return "probaTomcat";
//	}
	
	public List<Patient> getAll(){
		return (List<Patient>) pr.findAll();
	}
	
	@RequestMapping("/getAllPatients")
	public ResponseEntity<?> getAllPatients(){
		return ResponseEntity.ok(personService.getAll());
	}
	
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
	
	@RequestMapping(value = "/sorted")
	public List<Patient> getSorted(Sort sorted) {
		return patientService.findSorted(sorted);
	}
	
	@RequestMapping(value = "/pageable")
	public Page<Patient> getPageable(Pageable pageable) {
		return patientService.findPageable(pageable);
	}
	
	@RequestMapping(value = "/searchAndPaging")
	public List<Patient> searchAndPaging(@RequestParam(name="pageNo") int pageNo, @RequestParam(name="pageSize") int pageSize, @RequestParam(name="age") int age){
		return patientService.searchAndPaging(pageNo, pageSize, age);
	}
	
	@RequestMapping(value = "/transformHeight")
	public double getTransformed(@RequestParam(name="id")int id, @RequestParam(name="unit") String unit) {
		
		Patient p = pr.findPatientById(id);
		if(p != null) {
			if(unit.equals("m")) {
				return p.getHeight();
			}
			else if(unit.equals("cm")) {
				return p.getHeight() * 100;
			}
			else if(unit.equals("in")) {
				return p.getHeight() * 39.37007874;
			}
			else if(unit.equals("ft")) {
				return p.getHeight() * 3.2808399;
			}
		}
		return 0;
	}
}