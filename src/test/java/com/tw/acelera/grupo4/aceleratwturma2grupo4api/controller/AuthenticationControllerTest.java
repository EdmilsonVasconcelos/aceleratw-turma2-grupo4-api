package com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller;

import java.net.URI;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@org.junit.jupiter.api.Test
	public void shouldReturn400WhenInvalidAuthenticationData() throws Exception {
		
		URI uri = new URI("/auth");
		String json = "{\"email\":invalido@email.com, \"password\":teste}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));
		
		
	}

}
