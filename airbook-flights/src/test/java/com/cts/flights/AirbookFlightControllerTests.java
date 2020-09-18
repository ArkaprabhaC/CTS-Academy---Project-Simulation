package com.cts.flights;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.flights.exception.FlightAlreadyExistException;
import com.cts.flights.exception.FlightNotFoundException;
import com.cts.flights.model.Flight;
import com.cts.flights.model.ResponseFormat;
import com.cts.flights.service.IFlightService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;

@AutoConfigureMockMvc
@SpringBootTest
class AirbookFlightControllerTests {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	private IFlightService service;
	
	
	
	@Test
	public void testMvc_addFlight() throws Exception {
		Flight flight = new Flight();
		flight.setAirline("Air India");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VIDP");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VECC");
		flight.setPlaneNo("AXB661B");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(flight);
	
		when(service.addFlight(flight)).thenReturn(new ResponseFormat(HttpStatus.OK,"Flight added successfully"));
		
		mvc.perform(post("/api/v1/flight/add")
			.contentType("application/json")
			.content(object))
			.andExpect(status().isOk());
		
		verify(service).addFlight(flight);
	}
	
	@Test
	public void testMvc_addFlightAlreadyExist() throws Exception {
		Flight flight = new Flight();
		flight.setAirline("Air India");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VIDP");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VECC");
		flight.setPlaneNo("AXB661B");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(flight);
		
		when(service.addFlight(flight)).thenThrow(FlightAlreadyExistException.class);
		
		mvc.perform(post("/api/v1/flight/add")
			.contentType("application/json")
			.content(object))
			.andExpect(status().isConflict());
		
	
	}
	
	@Test
	public void testMvc_getRoutes() throws Exception {
		ArrayList<Flight> flights = new ArrayList<>();
		flights.add(new Flight(1, "airlien1", "plane123", "VECC", "VIDP", LocalDate.now(), LocalDate.now()));
		flights.add(new Flight(2, "airlien2", "plane231", "VECC", "VIDP", LocalDate.now(), LocalDate.now()));
		
		when(service.getFlights("vecc", "VIDP")).thenReturn(flights);
		
		mvc.perform(get("/api/v1/flight/get/vecc/VIDP"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testMvc_getRoutesError() throws Exception {
		
		when(service.getFlights("VECC", "VMMM")).thenThrow(FlightNotFoundException.class);
		
		mvc.perform(get("/api/v1/flight/get/VECC/VMMM"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testMvc_getFlight() throws Exception {
		Flight flight = new Flight();
		flight.setAirline("Air India");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VIDP");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VECC");
		flight.setPlaneNo("AXB661B");
		
		when(service.getOneFlight("AXB661B")).thenReturn(flight);
		
		mvc.perform(get("/api/v1/flight/get/AXB661B"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testMvc_getFlightError() throws Exception {
		
		when(service.getOneFlight("AXB999AB")).thenThrow(FlightNotFoundException.class);
		mvc.perform(get("/api/v1/flight/get/AXB999AB"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testMvc_deleteFlight() throws Exception {
		Flight flight = new Flight();
		flight.setAirline("Air India");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VIDP");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VECC");
		flight.setPlaneNo("AXB661B");
		
		when(service.deleteFlight("AXB661B")).thenReturn(new ResponseFormat(HttpStatus.OK, "Flight deleted successfully"));
		mvc.perform(delete("/api/v1/flight/delete/{planeNo}", "AXB661B"))
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void testMvc_deleteFlightNotFound() throws Exception {

		when(service.deleteFlight("AXB99999MAN")).thenThrow(FlightNotFoundException.class);

		mvc.perform(delete("/api/v1/flight/delete/AXB99999MAN"))
			.andExpect(status().isNotFound());
		
	}

}
