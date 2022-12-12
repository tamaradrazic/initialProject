package com.example.projekat.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.config.JwtTokenUtil;
import com.example.projekat.entities.UserEntity;
import com.example.projekat.model.JwtRequest;
import com.example.projekat.model.JwtResponse;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.JwtUserDetailsService;

@RestController
@CrossOrigin("http://localhost:8080")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserRepository ur;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public void createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		Random random = new Random();
		int num = random.nextInt(999999);
		String code = String.format("%06d", num);
		System.out.println(code);
		UserEntity u = ur.findUserEntityByName(authenticationRequest.getUsername());
		u.setCode(code);
		ur.save(u);
	}

	@RequestMapping(value = "/withCode", method = RequestMethod.POST)
	public ResponseEntity<?> test(@RequestBody JwtRequest authenticationRequest, @RequestParam String code)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		UserEntity u = ur.findUserEntityByName(authenticationRequest.getUsername());
		if (u.getCode().equals(code)) {
			final String token = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new JwtResponse(token));
		}
		return null;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
