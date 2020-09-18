package com.cts.admin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Admin model
 * @author Arkaprabha
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {

	/**
	 * 
	 * Defines admin ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long adminId;
	
	/**
	 * Defines admin name
	 */
	private String username;
	
	/**
	 * Defines admin email
	 */
	private String email;
	
	/**
	 * Defines admin password
	 */
	private String password;
	
	/**
	 * Defines admin active
	 */
	private boolean active;
	
	/**
	 * customer role
	 */
	private String roles;

	/**
	 * 
	 * @param username
	 * @param email
	 * @param password
	 * @param active
	 * @param roles
	 */
	public Admin(final String username,final String email,final String password,final boolean active,final String roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}
	
	
	
	
}