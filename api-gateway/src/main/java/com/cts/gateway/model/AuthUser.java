package com.cts.gateway.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Details service
 * @author Arkaprabha
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser{
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	/**
	 * Defines name
	 */
	private String username;
	
	/**
	 * Defines password
	 */
	private String password;
	
	/**
	 * Defines DOB
	 */
	private boolean active;
	/**
	 * 
	 */
	private String roles;

	
}
