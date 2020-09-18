package com.cts.flights.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.flights.model.Flight;

/**
 * Flight repository interface
 * Implementation to be provided at runtime
 * 
 * @author Arkaprabha
 *
 */
public interface IFlightRepository extends JpaRepository<Flight, Long>{

	/**
	 * Finds flights by icao's
	 * 
	 * @param departureIcao
	 * @param arrivalIcao
	 * @return Optional<List<Flight>>
	 */
	Optional<List<Flight>> findByDepartureICAOAndArrivalICAO(final String departureIcao, final String arrivalIcao);
	
	/**
	 * Finds flight by Plane Number
	 * @param planeNo
	 * @return Optional<Flight>
	 */
	Optional<Flight> findByPlaneNo(final String planeNo);
	
}
