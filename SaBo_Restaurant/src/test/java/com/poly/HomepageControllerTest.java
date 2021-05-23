package com.poly;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

@SpringBootTest//(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HomepageControllerTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	//@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	void loginFormTest() throws Exception {
		mockMvc.perform(get("/login"))
		.andExpect(view().name("login"));
	}
	
	@Test
	//@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	void indexTest() throws Exception {
		mockMvc.perform(get("/login"))
		.andExpect(view().name("login"));
	}
	
	@Test
	void topRestaurantTest() throws Exception {
		mockMvc.perform(post("/topRestaurant"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
	}
}
