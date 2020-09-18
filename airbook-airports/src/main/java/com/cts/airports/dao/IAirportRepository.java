package com.cts.airports.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.airports.model.Airport;
/**
 * Airport repository interface
 * @author Arkaprabha
 *
 */
public interface IAirportRepository extends JpaRepository<Airport, Long>{

	/**
	 * 
	 * @param icao
	 * @return
	 */
	Optional<Airport> findAirportByIcao(final String icao);
	
	
	/**
	 * 
	 * @param icao
	 * @return
	 */
	Optional<Airport> findAirportByName(final String airportName);
}
