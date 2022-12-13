package com.example.projekat.repository;

import java.util.List;

import com.example.projekat.entities.Patient;

public interface IPatientService {

	List<Patient> findPaginated(int pageNo, int pageSize);
}
