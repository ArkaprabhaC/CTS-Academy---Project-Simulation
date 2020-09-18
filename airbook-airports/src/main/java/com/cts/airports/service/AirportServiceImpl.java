package com.cts.airports.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cts.airports.dao.IAirportRepository;
import com.cts.airports.exception.AirportAlreadyExistException;
import com.cts.airports.exception.AirportNotFoundException;
import com.cts.airports.model.Airport;
import com.cts.airports.model.ResponseFormat;

import lombok.NoArgsConstructor;

/**
 * Airport service implementation
 * @author Arkaprabha
 *
 */
@Service
@NoArgsConstructor
public class AirportServiceImpl implements IAirportService {

	/**
	 * Autowired airport Repository
	 */
	@Autowired
	private IAirportRepository airportRepo;
	
	/**
	 * log4j logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirportServiceImpl.class);
	
	@Override
	public List<Airport> getAirports() {
		LOGGER.info("Getting all airports");
		return airportRepo.findAll();
	}

	@Override
	public Airport getAirportByICAO(final String icao) {
		
		LOGGER.info("Trying to find the airport by ICAO..");
		return airportRepo.findAirportByIcao(icao)
				.orElseThrow(() -> new AirportNotFoundException("Airport with given ICAO code not found"));
	}

	@Override
	public ResponseFormat addAirport(final Airport airport) {
		LOGGER.info("Checking if airport exist:- {} ", airport.getName());
		final Optional<Airport> findAirport = airportRepo.findAirportByIcao(airport.getIcao());
		
		if(findAirport.isPresent()) {
			LOGGER.info("Airport exists in system already. Throwing exception...");
			throw new AirportAlreadyExistException("Airport already exists in the system");
		}
		
		LOGGER.info("Airport not present in system.");
		LOGGER.info("Adding airport...");
		
		airportRepo.save(airport);
		
		return new ResponseFormat(HttpStatus.OK, "Airport added successfully");
		
	}

	@Override
	public ResponseFormat updateAirport(final Airport airport) {
		
		LOGGER.info("Checking if airport exist:- {} ", airport.getName());
		final Optional<Airport> findAirport = airportRepo.findAirportByIcao(airport.getIcao());
		
		if(!findAirport.isPresent()) {
			LOGGER.info("Airport not found in system. Throwing exception...");
			throw new AirportNotFoundException("Airport not found in the system");
		}
		
		LOGGER.info("Airport present in system.");
		LOGGER.info("Updating airport...");
		
		final Airport oldAirport = findAirport.get();
		
		oldAirport.setCity(airport.getCity());
		oldAirport.setIata(airport.getIata());
		oldAirport.setIcao(airport.getIcao());
		oldAirport.setName(airport.getName());
		
		airportRepo.save(oldAirport);
		
		return new ResponseFormat(HttpStatus.OK, "Airport updated successfully");
	}

	@Override
	public ResponseFormat deleteAirport(final String icao) {
		LOGGER.info("Checking if airport exist:- {} ", icao);
		final Optional<Airport> findAirport = airportRepo.findAirportByIcao(icao);
		
		if(!findAirport.isPresent()) {
			LOGGER.info("Airport not found in system. Throwing exception...");
			throw new AirportNotFoundException("Airport not found in the system");
		}
		
		LOGGER.info("Airport present in system.");
		LOGGER.info("Removing airport...");
		
		airportRepo.delete(findAirport.get());
		
		return new ResponseFormat(HttpStatus.OK, "Airport deleted successfully");
	}

	@Override
	public Airport getAirportByName(final String airportName) {
		LOGGER.info("Trying to find the airport by NAME..");
		return airportRepo.findAirportByName(airportName)
				.orElseThrow(() -> new AirportNotFoundException("Airport with given name not found"));
	}

}
