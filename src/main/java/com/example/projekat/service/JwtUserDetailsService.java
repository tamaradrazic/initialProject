package com.example.projekat.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.projekat.entities.UserEntity;
import com.example.projekat.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	//$2y$16$2TvUkY0z8Ei5AvX.w3LgF.vDuEbPUrZ1J/6BlBwxbyJdMd2.Ig4C6
	//$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6
	
	@Autowired
	UserRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity u = ur.findUserEntityByName(username);

		if (u.getUsername().equals(username)) {
			return new User(u.getUsername(), u.getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
