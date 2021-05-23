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

@SpringBootTest//(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SearchDetailControllerTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void indexTest() throws Exception {
		mockMvc.perform(get("/search/index"))
		.andExpect(view().name("search"));
	}
	
	@Test
	public void multipleResultTest() throws Exception {
		mockMvc.perform(post("/search/result").content("Nhà hàng"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray());
	}
	
	@Test
	public void singleResultTest() throws Exception {
		mockMvc.perform(post("/search/result").content("Nhà hàng"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value("1"))
		.andExpect(jsonPath("$[0].restaurantName").value("Nhà hàng tiện lợi TIKI"))
		.andExpect(jsonPath("$[0].email").value("tikirestaurant@gmail.com"))
		.andExpect(jsonPath("$[0].phone").value("0979222345"))
		.andExpect(jsonPath("$[0].address").value("256 Dương Quảng Hàm, Phường 5, Quận Gò Vấp"));
	}
}
