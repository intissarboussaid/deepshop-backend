package com.deepinsta.config;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.deepinsta.modal.Account;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
	
	private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	public String extractEmail(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
				}
	  public String generateJwtToken(Authentication authentication) {

		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

		    return Jwts.builder()
		        .setSubject((userPrincipal.getUsername()))
		        .setIssuedAt(new Date())
		        .setExpiration(new Date((new Date()).getTime() + 1000 * 60 * 24))
		        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
		        .compact();
		  }
	
	public<T>T extractClaims(String token, Function<Claims, T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(Account userDetails) {
		return  generateToken(new HashMap<>(),userDetails);
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
				
	}
	
	///valid token
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String email =extractEmail(token);
		return (email.equals(userDetails.getUsername()))&& !isTokenExpired(token);
	}
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	private Key getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	    return Keys.hmacShaKeyFor(keyBytes);
	}

	public List<Account> generateToken(List<Account> account) {
		// TODO Auto-generated method stub
		return account;
	}
}
