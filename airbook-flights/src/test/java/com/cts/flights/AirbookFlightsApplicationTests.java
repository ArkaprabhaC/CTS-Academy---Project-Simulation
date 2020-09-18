package com.cts.flights;



import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.flights.dao.IFlightRepository;
import com.cts.flights.exception.FlightAlreadyExistException;
import com.cts.flights.exception.FlightNotFoundException;
import com.cts.flights.feign.AirportClient;
import com.cts.flights.model.Flight;
import com.cts.flights.service.IFlightService;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
class AirbookFlightsApplicationTests {
	
	@Autowired
	private IFlightService service;
	
	@MockBean
	private IFlightRepository repoMock;
	
	@MockBean
	private AirportClient clientMock;
	
	
	
	@Test
	public void testService_addFlight() {
		Flight flight = new Flight();
		flight.setAirline("Air India");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VIDP");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VECC");
		flight.setPlaneNo("AXB661B");
		
	
		when(repoMock.findByPlaneNo(flight.getPlaneNo())).thenReturn(Optional.empty());
		assertEquals("Flight added successfully", service.addFlight(flight).getMessage());
		verify(repoMock).findByPlaneNo("AXB661B");
	}
	
	@Test
	public void testService_addFlightWithSamePlaneNo() {
		Flight flight = new Flight();
		flight.setAirline("Jet Airways");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VAAH");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VOBG");
		flight.setPlaneNo("AXB661B");
		
		when(repoMock.findByPlaneNo(flight.getPlaneNo())).thenReturn(Optional.of(flight));
		assertThrows(FlightAlreadyExistException.class, () -> {
			service.addFlight(flight).getMessage();
		});
		verify(repoMock).findByPlaneNo("AXB661B");
	}
	
	@Test
	public void testService_getRoutes() {
		ArrayList<Flight> flights = new ArrayList<>();
		flights.add(new Flight(1, "airlien1", "plane123", "VECC", "VIDP", LocalDate.now(), LocalDate.now()));
		flights.add(new Flight(2, "airlien2", "plane231", "VECC", "VIDP", LocalDate.now(), LocalDate.now()));
		flights.add(new Flight(3, "airlien3", "planeABC", "VAAH", "VECC", LocalDate.now(), LocalDate.now()));
		
		when(repoMock.findByDepartureICAOAndArrivalICAO("VECC","VIDP")).thenReturn(Optional.of(flights));
		
		assertDoesNotThrow(() -> {
			service.getFlights("VECC", "VIDP");
		});
		
		verify(repoMock).findByDepartureICAOAndArrivalICAO(Mockito.anyString(),Mockito.anyString());
	}
	
	@Test
	public void testService_getRoutesIncorrect() {
		ArrayList<Flight> flights = new ArrayList<>();
		flights.add(new Flight(1, "airlien1", "plane123", "VECC", "VIDP", LocalDate.now(), LocalDate.now()));
		flights.add(new Flight(2, "airlien2", "plane231", "VECC", "VIDP", LocalDate.now(), LocalDate.now()));
		flights.add(new Flight(3, "airlien3", "planeABC", "VAAH", "VECC", LocalDate.now(), LocalDate.now()));
		
		when(repoMock.findByDepartureICAOAndArrivalICAO("VECC","VILK")).thenThrow(FlightNotFoundException.class);
		
		assertThrows(FlightNotFoundException.class, () -> {
			service.getFlights("VECC", "VILK");
		});
		
		verify(repoMock).findByDepartureICAOAndArrivalICAO(Mockito.anyString(),Mockito.anyString());
	}
	
	
	@Test
	public void testService_getFlights() {
		Flight flight = new Flight();
		flight.setAirline("Jet Airways");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VAAH");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VOBG");
		flight.setPlaneNo("AXB661B");
		
		when(repoMock.findByPlaneNo("AXB661B")).thenReturn(Optional.of(flight));
		assertNotNull(service.getOneFlight("AXB661B"));
		
		verify(repoMock).findByPlaneNo("AXB661B");
	}
	
	@Test
	public void testService_getFlightsError() {	
		when(repoMock.findByPlaneNo("AXB9999")).thenThrow(FlightNotFoundException.class);
		assertThrows(FlightNotFoundException.class, () -> {
			service.getOneFlight("AXB9999");
		});
		
		verify(repoMock).findByPlaneNo("AXB9999");
	}
	
	@Test
	@Order(7)
	public void testService_deleteFlight() {
		Flight flight = new Flight();
		flight.setAirline("Air India");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VIDP");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VECC");
		flight.setPlaneNo("AXB661B");
		
		when(repoMock.findByPlaneNo("AXB661B")).thenReturn(Optional.of(flight));
		assertEquals("Flight deleted successfully", service.deleteFlight("AXB661B").getMessage());
		
		verify(repoMock).findByPlaneNo("AXB661B");
		
	}
	
	@Test
	@Order(8)
	public void testService_deleteFlightNotFound() {
		Flight flight = new Flight();
		flight.setAirline("Air India");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VIDP");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VECC");
		flight.setPlaneNo("AXB699999");
		
		when(repoMock.findByPlaneNo("AXB699999")).thenThrow(FlightNotFoundException.class);
		assertThrows(FlightNotFoundException.class, () -> {
			service.deleteFlight("AXB699999").getMessage();
		});
		
		verify(repoMock).findByPlaneNo("AXB699999");
		
	}
	
	
	
}
