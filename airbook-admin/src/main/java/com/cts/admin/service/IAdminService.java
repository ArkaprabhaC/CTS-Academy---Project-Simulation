package com.cts.admin.service;

import java.util.List;

import com.cts.admin.model.Admin;
import com.cts.admin.model.Airport;
import com.cts.admin.model.Flight;
import com.cts.admin.model.ResponseFormat;

/**
 * IAdminService interface
 * @author Arkaprabha
 *
 */
public interface IAdminService {
	
	/**
	 * Gets all admin users in the system
	 * @return
	 */
	List<Admin> getAll();
	
	/**
	 * Get one admin user matching given username
	 * Throws AdminNotFoundException if username not found
	 * 
	 * @param name
	 * @return
	 */
	Admin getOneAdmin(String name);
	
	/**
	 * Adds an admin to the system
	 * @param admin
	 * @return
	 */
	ResponseFormat addAdmin(Admin admin);
	
	/**
	 * Deletes an admin from the system
	 * @param username
	 * @return
	 */
	ResponseFormat deleteAdmin(String username);
	
	
	/** Feign client calls to other API's **/
	
	
	/**
	 * Adds an airport to system
	 * @param airport
	 * @return
	 */
	ResponseFormat addAirport(Airport airport);
	
	/**
	 * Adds a flight to system
	 * @param flight
	 * @return
	 */
	ResponseFormat addFlight(Flight flight);
	
	/**
	 * Deletes an airport from the system
	 * @param icao
	 * @return
	 */
	ResponseFormat deleteAirport(String icao);
	
	/**
	 * Deletes a flight from the system
	 * @param planeNo
	 * @return
	 */
	ResponseFormat deleteFlight(String planeNo);

}
