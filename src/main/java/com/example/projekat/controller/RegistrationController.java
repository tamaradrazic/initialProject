package com.example.projekat.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.entities.UserEntity;
import com.example.projekat.repository.UserRepository;

@RestController
@Transactional
public class RegistrationController {

	@Autowired
	UserRepository ur;
	
	private static final String SALT = BCrypt.gensalt();

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public boolean registrate(@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName, 
			@RequestParam(name = "userName") String userName,
			@RequestParam(name = "password") String password, 
			@RequestParam(name = "email") String email,
			@RequestParam(name = "code") String code) {
		System.out.println(SALT);
		if (ur.findUserEntityByName(userName) == null) {
			UserEntity u = new UserEntity();
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setUsername(userName);
			u.setPassword(BCrypt.hashpw(password, SALT));
			u.setEmail(email);
			u.setCode(code);
			ur.save(u);
			
			return true;
		}
		return false;
	}
}
