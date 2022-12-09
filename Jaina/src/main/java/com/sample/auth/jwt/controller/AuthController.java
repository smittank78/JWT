package com.sample.auth.jwt.controller;

import java.text.ParseException;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.auth.jwt.dto.LoginDTO;
import com.sample.auth.jwt.dto.LoginResponseDTO;
import com.sample.auth.jwt.util.TokenManager;

@RestController
@RequestMapping("api/v1/")
@PropertySource("classpath:application.properties")
public class AuthController {
	
	@Autowired
	private TokenManager tokenManager;
	
	@PostMapping("login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDto) throws ParseException {	
		
		System.out.println("user creating... " + loginDto.getUsername() + " : " + loginDto.getPassword());
		LoginResponseDTO jwtResponseModel = null;
		
		/**
		 * Below if condition is static. For making this production ready same validation should happen 
		 * from Database side
		 */
		
		
		if(!loginDto.getUsername().equals("admin") || !loginDto.getPassword().equals("pass") ) {
		    return new ResponseEntity<LoginResponseDTO>(jwtResponseModel, HttpStatus.UNAUTHORIZED);
		}
		
		/*****************/
		
		final UserDetails userDetails = 
				new User(loginDto.getUsername(),
				loginDto.getPassword(), new ArrayList<>());
				
		final String jwtToken = tokenManager.generateJwtToken(userDetails);
		System.out.println(jwtToken +" -  " + loginDto.toString());
	    return new ResponseEntity<LoginResponseDTO>(new LoginResponseDTO(jwtToken), HttpStatus.OK);
	}
	
	
	@GetMapping("privateapi")
	public ResponseEntity<String> privateapi(@RequestHeader(value="authorization") String token) {
		System.out.println("token verifying...");
		try {
			if(!tokenManager.validateJwtToken(token)) {
				return new ResponseEntity<String>("Not a valid token", HttpStatus.UNAUTHORIZED);
			}		
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>("Invalid Token", HttpStatus.UNAUTHORIZED);
		}
		
	    return new ResponseEntity<String>("Executed Private API", HttpStatus.OK);
		
	}
	
	
}
