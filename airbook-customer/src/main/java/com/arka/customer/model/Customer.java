package com.arka.customer.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines Customer model
 * @author Arkaprabha
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	/**
	 * 
	 * Defines customer ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long custId;
	
	/**
	 * Defines customer name
	 */
	private String username;
	
	/**
	 * Defines customer email
	 */
	private String email;
	
	/**
	 * Defines customer password
	 */
	private String password;
	
	/**
	 * Defines customer DOB
	 */
	private boolean active;
	
	/**
	 * customer role
	 */
	private String roles;
	
	/**
	 * Bookings arraylist
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<FlightBooking> bookings = new ArrayList<>();
	
	/**
	 * 
	 * @param username
	 * @param email
	 * @param password
	 * @param active
	 */
	public Customer(final String username, final String email, final String password, final boolean active) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
	}
	
	
	
	

	
}
