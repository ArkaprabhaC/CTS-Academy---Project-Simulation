package com.cts.airports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.airports.model.Airport;
import com.cts.airports.model.ResponseFormat;
import com.cts.airports.service.IAirportService;

import lombok.NoArgsConstructor;

/**
 * Controller to handle the http requests
 * @author Arkaprabha
 *
 */
@RestController
@RequestMapping("/api/v1/airport")
@NoArgsConstructor
public class AirportController {

	/**
	 * Autowired airport service implementation
	 */
	@Autowired
	private IAirportService airportService;
	
	/**
	 * Returns all airports
	 * @return List<Airport>
	 */
	@GetMapping
	public ResponseEntity<List<Airport>> getAllAirports(){
		return ResponseEntity.ok(airportService.getAirports());
	}
	
	
	/**
	 * Returns an airport matching by name
	 * Throws AirportNotFoundException if airport not found
	 * 
	 * @param name
	 * @return Airport
	 */
	@GetMapping("/name/{name}")
	public ResponseEntity<Airport> getAirportByName(@PathVariable final String name){
		return ResponseEntity.ok(airportService.getAirportByName(name));
	}
	
	/**
	 * Returns an airport matching by icao
	 * Throws AirportNotFoundException if airport not found
	 * 
	 * @param icao
	 * @return Airport
	 */
	@GetMapping("/icao/{icao}")
	public ResponseEntity<Airport> getAirportByICAO(@PathVariable final String icao){
		return ResponseEntity.ok(airportService.getAirportByICAO(icao));
	}
	
	
	/**
	 * Adds an airport details to the system
	 * Throws AirportAlreadyExistException if airport already exists
	 * 
	 * @param airport
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseFormat> addAirport(@RequestBody final Airport airport){
		return ResponseEntity.ok(airportService.addAirport(airport));
	}
	
	
	/**
	 * Updates an existing airport
	 * 
	 * @param airport
	 * @return ResponseFormat
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseFormat> updateAirport(@RequestBody final Airport airport){
		return ResponseEntity.ok(airportService.updateAirport(airport));
	}
	
	/**
	 * Deletes an existing airport
	 * 
	 * @param icao
	 * @return ResponseFormat
	 */
	@DeleteMapping("/delete/{icao}")
	public ResponseEntity<ResponseFormat> deleteAirport(@PathVariable final String icao){
		return ResponseEntity.ok(airportService.deleteAirport(icao));
	}
}
