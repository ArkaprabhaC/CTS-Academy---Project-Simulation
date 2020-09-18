package com.cts.airports;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.airports.exception.AirportAlreadyExistException;
import com.cts.airports.exception.AirportNotFoundException;
import com.cts.airports.model.Airport;
import com.cts.airports.model.ResponseFormat;
import com.cts.airports.service.IAirportService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class AirbookAirportsControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IAirportService mockService;
	
	
	
	@Test
	public void testMvcWithFailureSupport_getAllAirports() throws Exception {
		ArrayList<Airport> airports = new ArrayList<>();
		airports.add(new Airport("DEL", "VIDP", "Indira Gandhi International Airport", "New Delhi"));
		airports.add(new Airport("CCU", "VECC", "Netaji Subhash Chandra Bose International Airport", "Kolkata"));
		airports.add(new Airport("AMD", "VAAH", "Ahmedabad Airport", "Ahemdabad"));
		airports.add(new Airport("AGX", "VOAT", "Agatti Island Airport", "Agatti Island"));
		airports.add(new Airport("LKO", "VILK", "Amausi Airport", "Lucknow"));
		airports.add(new Airport("BLR", "VOBG", "Bengaluru International Airport", "Bangalore"));
		
		
		when(mockService.getAirports()).thenReturn(airports);
		mvc.perform(get("/api/v1/airport"))
			.andExpect(status().isOk());
		
		verify(mockService).getAirports();
		
	}
	
	@Test
	public void testMvc_getAirportByIcao() throws Exception {
		
		when(mockService.getAirportByName("vecc")).thenThrow(AirportNotFoundException.class);
		mvc.perform(get("/api/v1/airport/vecc"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testMvc_addAirport() throws Exception {
		
		Airport airport = new Airport();
		airport.setCity("kolkata");
		airport.setIata("ccu");
		airport.setIcao("VECC");
		airport.setName("kolkata");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(airport);
		
		when(mockService.addAirport(airport)).thenReturn(new ResponseFormat(HttpStatus.OK, "Airport added successfully"));
		mvc.perform(post("/api/v1/airport/add").contentType("application/json").content(object))
			.andExpect(status().isOk());
		
	}
	
	
	@Test
	public void testMvc_addAirportAlreadyExist() throws Exception {
		
		Airport airport = new Airport();
		airport.setCity("kolkata");
		airport.setIata("ccu");
		airport.setIcao("VECC");
		airport.setName("kolkata");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(airport);
		
		when(mockService.addAirport(airport)).thenThrow(AirportAlreadyExistException.class);
		mvc.perform(post("/api/v1/airport/add").contentType("application/json").content(object))
			.andExpect(status().isConflict());
		
	}
	
	@Test
	public void testMvc_updateAirport() throws Exception {
		
		Airport airport = new Airport();
		airport.setCity("kolkata updated");
		airport.setIata("ccu updated");
		airport.setIcao("VECC");
		airport.setName("Kolkata updated");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(airport);
		
		when(mockService.updateAirport(airport)).thenReturn(new ResponseFormat(HttpStatus.OK, "Airport updated successfully"));
		mvc.perform(put("/api/v1/airport/update").contentType("application/json").content(object))
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void testMvc_updateAirportNotExist() throws Exception {
		
		Airport airport = new Airport();
		airport.setCity("kolkata updated");
		airport.setIata("ccu updated");
		airport.setIcao("MMMMLLL");
		airport.setName("Kolkata updated");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(airport);
		
		when(mockService.updateAirport(airport)).thenThrow(AirportNotFoundException.class);
		mvc.perform(put("/api/v1/airport/update").contentType("application/json").content(object))
			.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void testMvc_getAirportByName() throws Exception {
		Airport airport = new Airport();
		airport.setCity("kolkata updated");
		airport.setIata("ccu updated");
		airport.setIcao("MMMMLLL");
		airport.setName("Kolkata updated");
		
		when(mockService.getAirportByName("Kolkata updated")).thenReturn(airport);
		mvc.perform(get("/api/v1/airport/name/Kolkata updated"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testMvc_getAirportByNameNotExist() throws Exception {
		when(mockService.getAirportByName("test")).thenThrow(AirportNotFoundException.class);
		mvc.perform(get("/api/v1/airport/name/test"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testMvc_getAirportByICAO() throws Exception {
		Airport airport = new Airport();
		airport.setCity("kolkata updated");
		airport.setIata("ccu updated");
		airport.setIcao("VECC");
		airport.setName("Kolkata updated");
		
		when(mockService.getAirportByICAO("VECC")).thenReturn(airport);
		mvc.perform(get("/api/v1/airport/icao/VECC"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testMvc_getAirportByICAONotExist() throws Exception {
		when(mockService.getAirportByICAO("test")).thenThrow(AirportNotFoundException.class);
		mvc.perform(get("/api/v1/airport/icao/test"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testMvc_deleteAirport() throws Exception {
		
		when(mockService.deleteAirport("vecc")).thenReturn(new ResponseFormat(HttpStatus.OK, "Airport deleted successfully"));
		mvc.perform(delete("/api/v1/airport/delete/vecc"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testMvc_deleteAirportNotFound() throws Exception {
		when(mockService.deleteAirport("lllll")).thenThrow(AirportNotFoundException.class);
		mvc.perform(delete("/api/v1/airport/delete/lllll"))
		.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void testMvcFailure_getAirport() throws Exception {
		when(mockService.getAirportByICAO("mmmmm")).thenThrow(AirportNotFoundException.class);
		mvc.perform(get("/api/v1/airport/icao/mmmmm"))
			.andExpect(status().isNotFound());
		
	}
	
	
	
	
	
}
