package com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception;

public class UserExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserExistException(String message){
        super(message);
    }

}
