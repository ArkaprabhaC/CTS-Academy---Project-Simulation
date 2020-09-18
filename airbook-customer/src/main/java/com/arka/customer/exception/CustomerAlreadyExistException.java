package com.arka.customer.exception;

/**
 * CustomerAlreadyExistException definition
 * @author Arkaprabha
 *
 */
public class CustomerAlreadyExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public CustomerAlreadyExistException() {
		super();
	}

	/**
	 * Parameterized constructor
	 * @param message
	 */
	public CustomerAlreadyExistException(final String message) {
		super(message);
	}

}
