package com.cts.admin.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.admin.dao.IAdminRepository;
import com.cts.admin.exception.AdminAlreadyExistException;
import com.cts.admin.exception.AdminNotFoundException;
import com.cts.admin.feign.AirportClient;
import com.cts.admin.feign.DBSyncClient;
import com.cts.admin.feign.FlightClient;
import com.cts.admin.model.Admin;
import com.cts.admin.model.Airport;
import com.cts.admin.model.Flight;
import com.cts.admin.model.ResponseFormat;

import lombok.NoArgsConstructor;


/**
 * AdminService Impl class
 * @author Arkaprabha
 *
 */
@Service
@NoArgsConstructor
public class AdminServiceImpl implements IAdminService {

	/**
	 * Autowired admin repository
	 */
	@Autowired
	private IAdminRepository adminRepo;
	
	/**
	 * Autowired flight ms client
	 */
	@Autowired
	private FlightClient flightClient;
	
	/**
	 * Autowired airport ms client
	 */
	@Autowired
	private AirportClient airportClient;
	
	/**
	 * DBSyncClient
	 */
	@Autowired
	private DBSyncClient dbSyncClient;
	
	/**
	 * BCrypt Password Encoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * log4j logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	
	@Override
	public List<Admin> getAll() {
		LOGGER.info("Finding all admin...");
		return adminRepo.findAll();
	}

	@Override
	public Admin getOneAdmin(final String name) {
		LOGGER.info("Finding admin with name :: {}",name);
		return adminRepo.findByUsername(name)
			.orElseThrow(() -> new AdminNotFoundException("Admin with given username not found"));
	}

	@Override
	public ResponseFormat addAdmin(final Admin admin) {
		
		
		LOGGER.info("Finding admin with name :: {}",admin.getUsername());
		final Optional<Admin> findAdmin = adminRepo.findByUsername(admin.getUsername()); // NOPMD by Arkaprabha on 13/9/20 12:16 PM
		final boolean findAdminById = adminRepo.findById(admin.getAdminId()).isPresent();
		
		if(findAdminById) {
			LOGGER.info("Admin already present in system. Throwing exception....");
			throw new AdminAlreadyExistException("Admin with given ID already exist. Remove ID field and try again");
		}
		if(findAdmin.isPresent()) {
			LOGGER.info("Admin already present in system. Throwing exception....");
			throw new AdminAlreadyExistException("Admin with given username already exist");
		}
		
		LOGGER.info("Admin not found in system");
		LOGGER.info("attempting to persist admin");
		
		final String encodedPass = passwordEncoder.encode(admin.getPassword());
		
		admin.setPassword(encodedPass);
		admin.setRoles("ROLE_ADMIN");
		
		adminRepo.save(admin);
		dbSyncClient.addAuthUser(admin);
		
		return new ResponseFormat(HttpStatus.OK, "Admin added successfully!");
		
	}

	@Override
	public ResponseFormat deleteAdmin(final String username) {
		
		LOGGER.info("Finding admin with name :: {}",username);
		final Optional<Admin> findAdmin = adminRepo.findByUsername(username);
		
		if(!findAdmin.isPresent()) {
			LOGGER.info("Admin not present in system. Throwing exception....");
			throw new AdminNotFoundException("Admin with given username not found");
		}
		
		final Admin admin = findAdmin.get();
		LOGGER.info("Admin found in system");
		LOGGER.info("attempting to delete admin user..");
	
		adminRepo.delete(admin);
		dbSyncClient.deleteAuthUser(admin);
		
		return new ResponseFormat(HttpStatus.OK, "Admin removed successfully!");
		
	}
	
	
	/** Feign client API calls **/

	@Override
	public ResponseFormat addAirport(final Airport airport) {
		return airportClient.addAirport(airport);
	}

	@Override
	public ResponseFormat addFlight(final Flight flight) {
		return flightClient.addFlight(flight);
	}

	@Override
	public ResponseFormat deleteAirport(final String icao) {
		return airportClient.deleteAirport(icao);
	}

	@Override
	public ResponseFormat deleteFlight(final String planeNo) {
		return flightClient.deleteFligh(planeNo);
	}

}
