package com.tw.acelera.grupo4.aceleratwturma2grupo4api.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {
	
	@Value("${security.jwt.expiration}")
	private String expiration;
	
	@Value("${security.jwt.signature-key}")
	private String secret;

	public String generateToken(Authentication authentication) {
		
		log.debug("TokenService.generateToken - Start - Request:  [{}]", authentication);
		
		User userLogged = (User) authentication.getPrincipal();
		
		Date today = new Date();
		
		Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration));
		
		String response = Jwts.builder()
				.setIssuer("Api of carango bom")
				.setSubject(userLogged.getId().toString())
				.setIssuedAt(today)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
		
		log.debug("TokenService.generateToken - Finish - Request:  [{}], Response:  [{}]", authentication, response);
		
		return response;
	}
	
	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}