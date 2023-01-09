package com.example.projekat.dto;

import com.example.projekat.entities.UserRole;

public class LoginDTO {

	String token;
	UserRole role;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserRole getUserRole() {
		return role;
	}
	public void setUserRole(UserRole userRole) {
		role = userRole;
	}
	public LoginDTO(String token, UserRole userRole) {
		super();
		this.token = token;
		this.role = userRole;
	}
	@Override
	public String toString() {
		return "LoginDTO [token=" + token + ", UserRole=" + role + "]";
	}
}
