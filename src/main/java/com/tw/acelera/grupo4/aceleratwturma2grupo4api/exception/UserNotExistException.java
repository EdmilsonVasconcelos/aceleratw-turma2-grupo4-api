package com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception;

public class UserNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public UserNotExistException(String message){
        super(message);
    }

}
