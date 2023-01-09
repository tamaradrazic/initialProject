package com.example.projekat.controller;

import java.text.SimpleDateFormat;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.entities.Patient;
import com.example.projekat.repository.PatientRepository;

/**
 * PatientController class
 * 
 * @author tamara
 * @since 2022-12-30
 */
@CrossOrigin(origins = "*")
@RestController
public class PatientController {

	@Autowired
	PatientRepository pr;

	@Autowired
	private MessageSource messageSource;

	/**
	 * This method is used to return patients date of birth in format that is ordered by country.
	 * 
	 * @param id  This is parameter is users id that is used to find user
	 * @param locale This parameter is used to order which language is used to format date
	 * @return <code>String</code> This returns <code>String</code> value that is date of birth.
	 */
	@RequestMapping("/getPatientById")
	public String getDateForPatientById(@RequestParam("id") int id, Locale locale) {
		Patient p = pr.findPatientById(id);
		String message = messageSource.getMessage("spring.mvc.date-format", null, locale);

		SimpleDateFormat DateFor = new SimpleDateFormat(message);
		String stringDate = DateFor.format(p.getDate());
		System.out.println(message);
		System.out.println(stringDate);
		return stringDate;
	}
}
