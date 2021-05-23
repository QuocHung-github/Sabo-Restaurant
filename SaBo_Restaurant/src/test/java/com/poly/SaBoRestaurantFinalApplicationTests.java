package com.poly;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.poly.entities.Booking;

@SpringBootTest//(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@Import(SpringSecurityTestConfig.class)
class SaBoRestaurantFinalApplicationTests {

	//@LocalServerPort
	//private int port;
	
//	@Autowired
//	private RestTemplate testRestTemplate;
	
	//@Autowired
	//private TestRestTemplate testTemplate;

	@Autowired
	private MockMvc mockMvc;

//	@Mock
//	private CustomerService mockCustomerService;

	@Test
	@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	void contextLoads() throws Exception {
		Booking bk = new Booking();
		bk.setStatus(1);
		
		//UriComponents comp = UriComponentsBuilder.newInstance().scheme("https").host("localhost").port(port).path("/customer/history").build();
		
		//ResponseEntity<List> getList = testTemplate.postForEntity(comp.toString(), null, List.class);
		
		//assertThat(getList.getBody()).isNull();
		 mockMvc.perform(post("/customer/history"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty());
		//.andExpect(jsonPath("$.status", Matchers.is(1)));
	}

}
