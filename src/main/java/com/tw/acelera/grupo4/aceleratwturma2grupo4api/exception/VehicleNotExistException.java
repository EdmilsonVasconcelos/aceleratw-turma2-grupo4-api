package com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception;

public class VehicleNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public VehicleNotExistException(String message){
        super(message);
    }

}
