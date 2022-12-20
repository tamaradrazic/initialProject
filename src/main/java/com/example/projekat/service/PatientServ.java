package com.example.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projekat.entities.Patient;
import com.example.projekat.repository.PatientRepository;

@Service
public class PatientServ {

	@Autowired
	PatientRepository pr;
	
	public List<Patient> getAll(){
		return (List<Patient>) pr.findAll();
	}
	
	public PatientServ(PatientRepository pr)
    {
        // this keyword refers to current instance
        this.pr = pr;
    }
}
