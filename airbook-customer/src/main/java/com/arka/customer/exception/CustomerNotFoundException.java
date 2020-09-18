package com.arka.customer.exception;

/**
 * CustomerNotFoundException definition
 * @author Arkaprabha
 *
 */
public class CustomerNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public CustomerNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor
	 * @param message
	 */
	public CustomerNotFoundException(final String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
