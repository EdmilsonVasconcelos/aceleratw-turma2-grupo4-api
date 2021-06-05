package com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.user.request.SaveUserDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.user.response.UserSavedDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.service.UserService;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.utils.ConverterToJson;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	private static final String PATH_API = "/user";
	
	@Autowired
    MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	@DisplayName("POST /user")
	@WithMockUser
    public void shouldSaveBrand() throws Exception {
			
		SaveUserDTO request = new SaveUserDTO("Edmilson", "edmilson.vasconcelos89@gmail.com", "123123");
		UserSavedDTO response = new UserSavedDTO(1L, "Edmilson", "edmilson.vasconcelos89@gmail.com");
		
		doReturn(response).when(userService).saveUser(request);
		
		mockMvc.perform(post(PATH_API)
				.contentType(MediaType.APPLICATION_JSON)
                .content(ConverterToJson.asJsonString(request)))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Edmilson")))
		.andExpect(jsonPath("$.email", is("edmilson.vasconcelos89@gmail.com")));
	}

	@Test
	@DisplayName("GET /user")
	@WithMockUser
    public void shouldGetAllUsers() throws Exception {
			
		UserSavedDTO userOne = new UserSavedDTO(1L, "Edmilson", "edmilson.vasconcelos89@gmail.com");
		UserSavedDTO userTwo = new UserSavedDTO(2L, "Joao", "joao@gmail.com");
		List<UserSavedDTO> response = Arrays.asList(userOne, userTwo);
		
		doReturn(response).when(userService).getAllUsers();
		
		mockMvc.perform(get(PATH_API)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].name", is("Edmilson")))
		.andExpect(jsonPath("$[0].email", is("edmilson.vasconcelos89@gmail.com")))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].name", is("Joao")))
		.andExpect(jsonPath("$[1].email", is("joao@gmail.com")));
	}

}
