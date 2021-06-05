package com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.config.security.TokenService;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.login.request.LoginUserDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.login.response.TokenDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody LoginUserDTO request) {
		
		log.debug("AuthenticationController.authenticate - Start - Request:  [{}]", request);
		
		UsernamePasswordAuthenticationToken dataLogin = request.converter();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(dataLogin);
			
			String token = tokenService.generateToken(authentication);
			
			return ResponseEntity.ok(new TokenDTO("Bearer", token));
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().build();
			
		}
		
	}

}
