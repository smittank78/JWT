package com.sample.auth.jwt.dto;

public class LoginResponseDTO {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginResponseDTO(String token) {
		super();
		this.token = token;
	}
	
	
	
}
