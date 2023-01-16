package com.example.projekat.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projekat.config.JwtTokenUtil;
import com.example.projekat.dto.LoginDTO;
import com.example.projekat.entities.UserEntity;
import com.example.projekat.entities.UserRole;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.JwtUserDetailsService;

/**
 * JwtAuthenticationController The JwtAuthenticationController class implements methods for
 * authentification.
 * 
 * @author tamara
 * @since 2022-12-30
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserRepository ur;
	
	/**
	 * This method is used to authenticate user by username and password and generate 
	 * six digit random code for two factor authentication.
	 * 
	 * @param username  This is the first parameter in createAuthenticationToken method, and it
	 *                  represents username
	 * @param password This is the second parameter in createAuthenticationToken method, it
	 *                  represents users password
	 * @return <code>String</code> This return String that represents six digit code
	 */

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String createAuthenticationToken(@RequestParam("username") String username,
			@RequestParam("password") String password) throws Exception {
		UserEntity u = ur.findUserEntityByName(username);
		try {
			if (u.isStatus()) {
				try {
					authenticate(username, password);
					Random random = new Random();
					int num = random.nextInt(999999);
					String code = String.format("%06d", num);
					System.out.println(code);
					u.setCode(code);
					ur.save(u);
					ur.flush();
					return code;
				} catch (Exception e) {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method is used to authenticate user with code from <code>createAuthenticationToken</code> method
	 * 
	 * @param username  This is the first parameter in withCode method, and it
	 *                  represents username
	 * @param password This is the second parameter in withCode method, it
	 *                  represents users password
	 * @param code This is the third parameter in withCode method, it represents six digit code from <code>createAuthenticationToken</code> method
	 * @return <code>LoginDTO</code> This return <code>LoginDTO</code> that represents <code>DTO</code> object that has <code>JWT token</code> that is generated from password 
	 * and user role
	 */

	@RequestMapping(value = "/withCode", method = RequestMethod.POST)
	public LoginDTO withCode(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam String code) throws Exception {
		authenticate(username, password);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UserEntity u = ur.findUserEntityByName(username);
		if (u.getCode().equals(code)) {
			final String token = jwtTokenUtil.generateToken(userDetails);
			UserRole role = u.getUserRoleId();
			LoginDTO dto = new LoginDTO(token, role);
			return dto;
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
