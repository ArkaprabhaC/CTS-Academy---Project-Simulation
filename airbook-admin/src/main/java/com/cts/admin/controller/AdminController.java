package com.cts.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.admin.model.Admin;
import com.cts.admin.model.Airport;
import com.cts.admin.model.Flight;
import com.cts.admin.model.ResponseFormat;
import com.cts.admin.service.IAdminService;

import lombok.NoArgsConstructor;

/**
 * Admin rest controller
 * @author Arkaprabha
 *
 */
@RestController
@RequestMapping("/api/v1/admin")
@NoArgsConstructor
public class AdminController {

	/**
	 * Autowired admin service
	 */
	@Autowired
	private IAdminService service;
	
	/**
	 * Gets all admin users
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Admin>> getAll(){
		return ResponseEntity.ok(service.getAll());
	}
	
	/**
	 * Gets one admin user
	 * @param username
	 * @return
	 */
	@GetMapping("/{username}")
	public ResponseEntity<Admin> getOneAdmin(@PathVariable final String username){
		return ResponseEntity.ok(service.getOneAdmin(username));
	}
	
	/**
	 * Adds admin user
	 * @param admin
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseFormat> addAdmin(@RequestBody final Admin admin){
		return ResponseEntity.ok(service.addAdmin(admin));
	}
	
	/**
	 * Deletes admin user
	 * @param username
	 * @return
	 */
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<ResponseFormat> deleteAdmin(@PathVariable final String username){
		return ResponseEntity.ok(service.deleteAdmin(username));
	}
	
	
	
	/** Feign client API calls **/
	
	/**
	 * Adds an airport
	 * @param airport
	 * @return
	 */
	@PostMapping("/add/airport")
	public ResponseEntity<ResponseFormat> addAirport(@RequestBody final Airport airport){
		return ResponseEntity.ok(service.addAirport(airport));
	}
	
	/**
	 * Delete an airport
	 * @param icao
	 * @return
	 */
	@DeleteMapping("/delete/airport/{icao}")
	public ResponseEntity<ResponseFormat> deleteAirport(@PathVariable final String icao){
		return ResponseEntity.ok(service.deleteAirport(icao));
	}
	
	/**
	 * Adds a flight
	 * @param flight
	 * @return
	 */
	@PostMapping("/add/flight")
	public ResponseEntity<ResponseFormat> addFlight(@RequestBody final Flight flight){
		return ResponseEntity.ok(service.addFlight(flight));
	}
	
	/**
	 * Deletes a flight
	 * @param planeNo
	 * @return
	 */
	@DeleteMapping("/delete/flight/{planeNo}")
	public ResponseEntity<ResponseFormat> deleteFlight(@PathVariable final String planeNo){
		return ResponseEntity.ok(service.deleteFlight(planeNo));
	}
	
	
	
}
