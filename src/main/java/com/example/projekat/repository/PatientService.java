package com.example.projekat.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@Override
	public List<Patient> findPaginatedAndSort(int pageNo, int pageSize, String sort) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
		Page<Patient> pagedResult = pr.findAll(paging);
		return pagedResult.toList();
	}
	
	@Override
	public List<Patient> sorting(String sortBy){
		Sort sortOrder = Sort.by(sortBy);
		List<Patient> patients = (List<Patient>) pr.findAll(sortOrder);
		return patients;
	}
	
	@Override
	public List<Patient> searchAndPaging(int pageNo, int pageSize, int age){
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Patient> page = pr.findAllPatientByAge(age, paging);
		return page.toList();
	}

	@Override
	public Page<Patient> findPageable(Pageable pageable) {
		return pr.findPatient(pageable);
	}
}
