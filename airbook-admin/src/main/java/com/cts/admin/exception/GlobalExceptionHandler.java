package com.cts.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.admin.model.ResponseFormat;

import lombok.NoArgsConstructor;

/**
 * Global Exception Handler
 * @author Arkaprabha
 *
 */
@RestControllerAdvice
@NoArgsConstructor
public class GlobalExceptionHandler {
	/**
	 * This method is used to handle ADminAlreadyExist Exception
	 */
	@ExceptionHandler(AdminAlreadyExistException.class)
	public ResponseEntity<ResponseFormat> handleCustAlExist(final AdminAlreadyExistException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.CONFLICT, exception.getMessage()),
				HttpStatus.CONFLICT);
	}

	/**
	 * This method is used to handle AdminNotFound Exception
	 */
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<ResponseFormat> handleCustNotFound(final AdminNotFoundException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}
	
	
	/**
	 * This method is used to handle exceptions that gets mapped
	 * into one single parent exception : GenericNotFoundException
	 * 
	 * The child Exceptions are :
	 * AirportNotFoundException
	 * FlightNotFoundException
	 */
	@ExceptionHandler(GenericNotFoundException.class)
	public ResponseEntity<ResponseFormat> handleGenericNotFound(final GenericNotFoundException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}
	

	/**
	 * This method is used to handle exceptions that gets mapped
	 * into one single parent exception : GenericAlreadyExistException
	 * 
	 * The child Exceptions are :
	 * AirportAlreadyExistException
	 * FlightAlreadyExistException
	 */
	@ExceptionHandler(GenericAlreadyExistException.class)
	public ResponseEntity<ResponseFormat> handleGenerocAlExist(final GenericAlreadyExistException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.CONFLICT, exception.getMessage()),
				HttpStatus.CONFLICT);
	}
}
