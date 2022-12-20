package com.example.projekat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projekat.entities.Patient;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Integer>{
	
	@Query("select p from Patient p where p.age = :age")
	List<Patient> findPatientByAge(@Param("age")int age);

	Page<Patient> findAllPatientByAge(int age, Pageable paging);
	
	@Query("select p from Patient p")
	Page<Patient> findPatient(Pageable paging);
	
	@Query("select p from Patient p")
	List<Patient> findSorted(Sort sorted);
	
	@Query("select p from Patient p where p.id = :id")
	Patient findPatientById(@Param("id")int id);
}
