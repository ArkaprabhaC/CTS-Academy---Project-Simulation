package com.cts.gateway;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class ApiGatewayApplicationTests {
	
	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testMock_checkTokenGeneration() throws Exception {
		
		mvc.perform(post("/oauth/token?grant_type=password&username=test&password=test")
				.header("Authorization", "Basic d2ViLWNsaWVudDp3ZWItY2xpZW50LXNlY3JldA=="))
				.andExpect(status().isOk());
				
	}

}
