package com.example.projekat.repository;

import java.util.List;
import com.example.projekat.entities.Patient;

public interface IPatientService {

	List<Patient> findPaginated(int pageNo, int pageSize);

	List<Patient> findPaginatedAndSort(int pageNo, int pageSize, String sort);
	List<Patient> sorting(String sortBy);
	List<Patient> searchAndPaging(int pageNo, int pageSize, int age);
}
