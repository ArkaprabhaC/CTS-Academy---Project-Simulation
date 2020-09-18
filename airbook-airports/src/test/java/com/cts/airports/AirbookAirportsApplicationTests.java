package com.cts.airports;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.cts.airports.dao.IAirportRepository;
import com.cts.airports.exception.AirportAlreadyExistException;
import com.cts.airports.exception.AirportNotFoundException;
import com.cts.airports.model.Airport;
import com.cts.airports.service.IAirportService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
class AirbookAirportsApplicationTests {

	@Autowired
	private IAirportService service;
	
	@MockBean
	private IAirportRepository mockRepo;
	
	
	@Test
	public void testService_getAllAirports() {
		ArrayList<Airport> airports = new ArrayList<>();
		airports.add(new Airport("DEL", "VIDP", "Indira Gandhi International Airport", "New Delhi"));
		airports.add(new Airport("CCU", "VECC", "Netaji Subhash Chandra Bose International Airport", "Kolkata"));
		airports.add(new Airport("AMD", "VAAH", "Ahmedabad Airport", "Ahemdabad"));
		airports.add(new Airport("AGX", "VOAT", "Agatti Island Airport", "Agatti Island"));
		airports.add(new Airport("LKO", "VILK", "Amausi Airport", "Lucknow"));
		airports.add(new Airport("BLR", "VOBG", "Bengaluru International Airport", "Bangalore"));
		
		when(mockRepo.findAll()).thenReturn(airports);
		assertEquals(6, service.getAirports().size());
		
		verify(mockRepo).findAll();
	}
	
	
	
	@Test
	public void testService_addAirport() {
		
		Airport airport = new Airport();
		airport.setCity("Agatti Island");
		airport.setIata("AGX");
		airport.setIcao("VOAT");
		airport.setName("Agatti Island Airport");
		
		when(mockRepo.findAirportByIcao(airport.getIcao())).thenReturn(Optional.empty());
		assertEquals("Airport added successfully", service.addAirport(airport).getMessage());
		
		verify(mockRepo).findAirportByIcao(airport.getIcao());
	}
	
	
	@Test
	public void testService_addAirportWithSameICAO() {
		
		Airport airport = new Airport();
		airport.setCity("Agatti Island");
		airport.setIata("AGX");
		airport.setIcao("VOAT");
		airport.setName("Agatti Island Airport");
		
		when(mockRepo.findAirportByIcao(airport.getIcao())).thenReturn(Optional.of(airport));
		assertThrows(AirportAlreadyExistException.class, () -> {
			service.addAirport(airport);
		});
		
		verify(mockRepo).findAirportByIcao(airport.getIcao());
		
	}
	
	@Test
	public void testService_getAirportByICAO() {
		Airport airport = new Airport();
		airport.setCity("Agatti Island");
		airport.setIata("AGX");
		airport.setIcao("VOAT");
		airport.setName("Agatti Island Airport");
		
		when(mockRepo.findAirportByIcao(airport.getIcao())).thenReturn(Optional.of(airport));
		assertEquals("Agatti Island Airport",service.getAirportByICAO("VOAT").getName());
		
		verify(mockRepo).findAirportByIcao(airport.getIcao());
	}
	
	
	@Test
	public void testService_getAirportByICAODoesntExist() {

		when(mockRepo.findAirportByIcao("MMMM")).thenThrow(AirportNotFoundException.class);
		assertThrows(AirportNotFoundException.class, () -> {
			service.getAirportByICAO("MMMM");
		});
		
		verify(mockRepo).findAirportByIcao("MMMM");
	}
	
	
	@Test
	public void testService_updateAirport() {
		
		Airport airport = new Airport();
		airport.setCity("Agatti Island2");
		airport.setIata("AGX2");
		airport.setIcao("VOAT");
		airport.setName("Agatti Island Airport222");
		
		when(mockRepo.findAirportByIcao(airport.getIcao())).thenReturn(Optional.of(airport));
		assertEquals("Airport updated successfully", service.updateAirport(airport).getMessage());
		
		verify(mockRepo).findAirportByIcao(airport.getIcao());
	}
	
	
	@Test
	public void testService_updateAirportDoesntExist() {
		
		Airport airport = new Airport();
		airport.setCity("Agatti Island2sadsad");
		airport.setIata("AGX2");
		airport.setIcao("MMMM");
		airport.setName("Agatti Island Airport222");
		
		when(mockRepo.findAirportByIcao("MMMM")).thenThrow(AirportNotFoundException.class);
		assertThrows(AirportNotFoundException.class, ()->{
			service.updateAirport(airport).getMessage();
		});
		
		
		verify(mockRepo).findAirportByIcao("MMMM");
	}
	
	
	@Test
	public void testService_deleteAirport() {
		
		Airport airport = new Airport();
		airport.setCity("Agatti Island2");
		airport.setIata("AGX2");
		airport.setIcao("VOAT");
		airport.setName("Agatti Island Airport222");
		
		when(mockRepo.findAirportByIcao(airport.getIcao())).thenReturn(Optional.of(airport));
		assertEquals("Airport deleted successfully", service.deleteAirport("VOAT").getMessage());
		
		verify(mockRepo).findAirportByIcao(airport.getIcao());
		
	}
	
	
	@Test
	public void testService_deleteAirportNotFound() {
		
		when(mockRepo.findAirportByIcao("AAAA")).thenThrow(AirportNotFoundException.class);
		assertThrows(AirportNotFoundException.class,() -> {
			service.deleteAirport("AAAA");
		});
		
		verify(mockRepo).findAirportByIcao("AAAA");
	}
	
	
	

}
