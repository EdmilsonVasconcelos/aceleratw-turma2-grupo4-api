package com.tw.acelera.grupo4.aceleratwturma2grupo4api.validation;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.exception.ErrorValidationDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.exception.ExceptionResponse;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.BrandExistsException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.BrandNotExistsException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.UserExistException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.UserNotExistException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.VehicleNotExistException;

@RestControllerAdvice
public class ErrorValidationHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorValidationDTO> handle(MethodArgumentNotValidException exception) {
		List<ErrorValidationDTO> errors = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorValidationDTO error = new ErrorValidationDTO(e.getField(), message);
			errors.add(error);
		});
		
		return errors;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserExistException.class)
	public ExceptionResponse handle(UserExistException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserNotExistException.class)
	public ExceptionResponse handle(UserNotExistException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BrandExistsException.class)
	public ExceptionResponse handle(BrandExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BrandNotExistsException.class)
	public ExceptionResponse handle(BrandNotExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(VehicleNotExistException.class)
	public ExceptionResponse handle(VehicleNotExistException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ExceptionResponse handle(SQLIntegrityConstraintViolationException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
}
