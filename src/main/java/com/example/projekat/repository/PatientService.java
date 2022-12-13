package com.example.projekat.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.projekat.entities.Patient;

@Service
public class PatientService implements IPatientService{

	@Autowired
	private PatientRepository pr;

	@Override
	public List<Patient> findPaginated(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Patient> pagedResult = pr.findAll(paging);
		
		return pagedResult.toList();
	}
}
