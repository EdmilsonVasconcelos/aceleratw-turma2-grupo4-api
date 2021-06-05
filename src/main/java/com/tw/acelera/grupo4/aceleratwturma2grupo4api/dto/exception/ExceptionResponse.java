package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponse {
	
	private String error;

}
