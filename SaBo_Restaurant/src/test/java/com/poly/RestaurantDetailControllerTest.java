package com.poly;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantDetailControllerTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test(enabled = false)
	public void indexTest() throws Exception {
		mockMvc.perform(get("/restaurantinfo/detail"))
		.andExpect(view().name("details"));
	}
	
	@Test
	public void whenIdEquals1_ThenReturnRestaurant() throws Exception {
		mockMvc.perform(post("/restaurantinfo/detail").param("id", "1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value("1"))
		.andExpect(jsonPath("$.restaurantName").value("Nhà hàng tiện lợi TIKI"))
		.andExpect(jsonPath("$.email").value("tikirestaurant@gmail.com"))
		.andExpect(jsonPath("$.phone").value("0979222345"))
		.andExpect(jsonPath("$.address").value("256 Dương Quảng Hàm, Phường 5, Quận Gò Vấp"));
	}
	
	@Test
	public void whenIdEquals0_ThenReturn404() throws Exception {
		mockMvc.perform(post("/restaurantinfo/detail").param("id", "0"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void whenIdMissing_ThenReturn400() throws Exception {
		mockMvc.perform(post("/restaurantinfo/detail"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void WhenIdIsNotInteger_ThenReturn400() throws Exception {
		mockMvc.perform(post("/restaurantinfo/detail").param("id", "a"))
		.andExpect(status().isBadRequest());
	}
}
