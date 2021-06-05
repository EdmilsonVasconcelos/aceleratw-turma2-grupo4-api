package com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception;

public class BrandExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BrandExistsException(String message){
        super(message);
    }

}
