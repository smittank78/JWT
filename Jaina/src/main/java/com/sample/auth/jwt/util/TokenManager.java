package com.sample.auth.jwt.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenManager implements Serializable {

	private static final long serialVersionUID = -2843623263047701254L;
	
	public static final long TOKEN_VALIDITY = 24 * 60 * 60 * 1000;
	private String jwtSecret = "This is secret key";
	
	public String generateJwtToken(UserDetails userDetails) {
			Map<String,Object> claims = new HashMap<>();
			return Jwts.builder().setClaims(claims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
							
	}
	
	public Boolean validateJwtToken(String token) { 
	      String username = getUsernameFromToken(token); 
	      Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	      System.out.println(username + " - " +claims.toString());
	      Boolean isTokenExpired = claims.getExpiration().before(new Date());
	      System.out.println("current time : " + new Date());
	      System.out.println("validate till : " + claims.getExpiration());
	      System.out.println("isTokenExpired : " + isTokenExpired);
	      return  !isTokenExpired; 
	} 
	
	public String getUsernameFromToken(String token) {
      final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody(); 
      System.out.println("Claims : " + claims.toString());
      return claims.getSubject(); 
	} 
	
}
