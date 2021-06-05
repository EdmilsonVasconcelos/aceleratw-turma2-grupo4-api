package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.login.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
	
	private String type;
	
	private String token;

}
