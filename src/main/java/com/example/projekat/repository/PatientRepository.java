package com.example.projekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.projekat.entities.Patient;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Integer>{

	
}
