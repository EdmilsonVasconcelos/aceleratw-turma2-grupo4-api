package com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception;

public class BrandNotExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BrandNotExistsException(String message){
        super(message);
    }

}
