package com.example.projekat.services;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.projekat.repository.PatientRepository;
import com.example.projekat.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

	@Mock 
	private PatientRepository pr;
	
	private PatientService patientService;
	
	@BeforeEach 
	void setUp() {
        this.patientService
            = new PatientService(this.pr);
    }
	
	@Test 
	void getAllPerson() {
		patientService.getAll();
        verify(pr).findAll();
    }
}
