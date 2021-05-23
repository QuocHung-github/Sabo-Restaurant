package com.poly;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.format.DateTimeFormatter;

import org.codehaus.jettison.json.JSONObject;
import org.junit.experimental.results.ResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entities.Booking;
import com.poly.entities.User;
import com.poly.entities.UserAccount;

@SpringBootTest // (webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestExecutionListeners(WithSecurityContextTestExecutionListener.class)
public class CustomerControllerTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Test
	public void registerViewTest() throws Exception {
		mockMvc.perform(get("/customer/regsiter")).andExpect(view().name("register"));
	}

	@Test
	public void successfulRegisterTest() throws Exception {
		String testString = "{\r\n" + "  	\"id\": 3,\r\n" + "    \"firstName\": \"Trung\",\r\n"
				+ "    \"lastName\": \"Thành Nguyễn\",\r\n" + "    \"birthday\": \"1976-09-05\",\r\n"
				+ "    \"gender\": \"1\",\r\n" + "    \"email\": \"trung@gmail.com\",\r\n"
				+ "    \"phone\": \"0124998002\",\r\n" + "    \"address\": \"23A Phạm Ngũ Lão\",\r\n"
				+ "    \"active\": true,\r\n" + "    \"account\": {\r\n" + "        \"username\": \"test\",\r\n"
				+ "        \"password\": \"123\",\r\n" + "        \"active\": true,\r\n" + "        \"role\": {\r\n"
				+ "        \"id\": 1\r\n" + "    	}\r\n" + "    }\r\n" + "}";

		mockMvc.perform(
				post("/customer/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(testString))
				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void whenPhoneIsNull_ThenRegisterReturn400() throws Exception {
		String testString = "{\r\n" + "  	\"id\": 3,\r\n" + "    \"firstName\": \"Trung\",\r\n"
				+ "    \"lastName\": \"Thành Nguyễn\",\r\n" + "    \"birthday\": \"1976-09-05\",\r\n"
				+ "    \"gender\": \"1\",\r\n" + "    \"email\": \"trung@gmail.com\",\r\n" + "    \"phone\": null,\r\n"
				+ "    \"address\": \"23A Phạm Ngũ Lão\",\r\n" + "    \"active\": true,\r\n" + "    \"account\": {\r\n"
				+ "        \"username\": \"test\",\r\n" + "        \"password\": \"123\",\r\n"
				+ "        \"active\": true,\r\n" + "        \"role\": {\r\n" + "        \"id\": 1\r\n" + "    	}\r\n"
				+ "    }\r\n" + "}";

		mockMvc.perform(
				post("/customer/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(testString))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	public void currentUserTest() throws Exception {
		String testString = "{\r\n"
				+ "    \"id\": \"2\",\r\n"
				+ "    \"firstName\": \"Phong\",\r\n"
				+ "    \"lastName\": \"Nguyễn\",\r\n"
				+ "    \"birthday\": \"1999-09-13\",\r\n"
				+ "    \"gender\": \"1\",\r\n"
				+ "    \"email\": \"phongcnc@gmail.com\",\r\n"
				+ "    \"phone\": \"0978665412\",\r\n"
				+ "    \"address\": \"108/7A Nguyễn Bỉnh Khiêm\""
				+ "}";

		User compare = objectMapper.readValue(testString, User.class);

		mockMvc.perform(post("/customer/user"))
			    .andExpect(status().isOk()).andExpect(jsonPath("$").exists())
				.andExpect(jsonPath("$.id").value(compare.getId()))
				.andExpect(jsonPath("$.firstName").value(compare.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(compare.getLastName()))
				.andExpect(jsonPath("$.birthday").value(compare.getBirthday().toString()))
				.andExpect(jsonPath("$.gender").value(compare.getGender()))
				.andExpect(jsonPath("$.email").value(compare.getEmail()))
				.andExpect(jsonPath("$.phone").value(compare.getPhone()))
				.andExpect(jsonPath("$.address").value(compare.getAddress()));
	}

	@Test
	@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	public void bookingTest() throws Exception {
		String testString = "{\"createdDate\":\"2021-05-07T10:38:00\"," + "\"bookingDate\":\"2021-05-07T19:30:00\","
				+ "\"persons\":\"5\"," + "\"status\":\"2\"," + "\"restaurant\":{\"id\":\"1\"},"
				+ "\"user\":{\"id\":\"2\"}}";

		Booking compare = objectMapper.readValue(testString, Booking.class);

		mockMvc.perform(post("/customer/checkout").contentType(MediaType.APPLICATION_JSON_VALUE).content(testString))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.createdDate")
						.value(compare.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$.bookingDate")
						.value(compare.getBookingDate().format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$.persons").value(compare.getPersons()))
				.andExpect(jsonPath("$.status").value(compare.getStatus()))
				.andExpect(jsonPath("$.restaurant.id").value(compare.getRestaurant().getId()))
				.andExpect(jsonPath("$.user.id").value(compare.getUser().getId()));
	}

	@Test
	@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	public void historyTest() throws Exception {
		String testString = "{\r\n" + "    \"id\": \"5\",\r\n" + "    \"restaurant\": {\r\n"
				+ "        \"id\": \"2\"\r\n" + "    },\r\n" + "    \"user\": {\r\n" + "        \"id\": \"2\"\r\n"
				+ "    },\r\n" + "    \"createdDate\": \"2021-05-11T10:30:00\",\r\n"
				+ "    \"bookingDate\": \"2021-05-11T20:30:00\",\r\n" + "    \"persons\": \"2\",\r\n"
				+ "    \"status\": \"1\"\r\n" + "}";

		Booking compare = objectMapper.readValue(testString, Booking.class);

		mockMvc.perform(get("/customer/history")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(compare.getId()))
				.andExpect(jsonPath("$[0].restaurant.id").value(compare.getRestaurant().getId()))
				.andExpect(jsonPath("$[0].user.id").value(compare.getUser().getId()))
				.andExpect(jsonPath("$[0].createdDate")
						.value(compare.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$[0].bookingDate")
						.value(compare.getBookingDate().format(DateTimeFormatter.ISO_DATE_TIME)))
				.andExpect(jsonPath("$[0].persons").value(compare.getPersons()))
				.andExpect(jsonPath("$[0].status").value(compare.getStatus()));
	}
	
	@Test(groups = {"updateUser"})
	@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	public void successUpdateUserTest() throws Exception {
		String testString = "{\"id\":\"2\","
				+ "\"firstName\":\"Phong\","
				+ "\"lastName\":\"Nguyễn\","
				+ "\"birthday\":\"1999-09-13\","
				+ "\"gender\":\"1\","
				+ "\"email\":\"phongcnc@gmail.com\","
				+ "\"phone\":\"0978665412\","
				+ "\"address\":\"108/7ANguyễnBỉnhKhiêm\"";
		
		User compare = objectMapper.readValue(testString, User.class);
		
		mockMvc.perform(put("/customer/updateInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(testString))
		.andExpect(jsonPath("$.id").value(compare.getId()))
		.andExpect(jsonPath("$.firstName").value(compare.getFirstName()))
		.andExpect(jsonPath("$.lastName").value(compare.getLastName()))
		.andExpect(jsonPath("$.birthday").value(compare.getBirthday().toString()))
		.andExpect(jsonPath("$.gender").value(compare.getGender()))
		.andExpect(jsonPath("$.email").value(compare.getEmail()))
		.andExpect(jsonPath("$.phone").value(compare.getPhone()))
		.andExpect(jsonPath("$.address").value(compare.getAddress()));
	}

	
	@Test
	@WithMockUser(username = "phong", password = "123", roles = "CUSTOMER")
	public void whenIdNotExists_ThenUpdateUserReturn404() throws Exception {
		String testString = "{\"id\":\"5\","
				+ "\"firstName\":\"Phong\","
				+ "\"lastName\":\"NguyễnTrần\","
				+ "\"birthday\":\"1999-09-13\","
				+ "\"gender\":\"1\","
				+ "\"email\":\"phong9x@gmail.com\","
				+ "\"phone\":\"0978665412\","
				+ "\"address\":\"108/7ANguyễnBỉnhKhiêm\","
				+ "\"account\":{\"username\":\"phong\","
				+ "\"password\":\"123\","
				+ "\"active\":true},"
				+ "\"role\":{\"id\":3}}";
		
		mockMvc.perform(put("/customer/updateInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(testString))
		.andExpect(status().isNotFound());
	}
	
	@AfterMethod(onlyForGroups = {"updateUser"})
	public void cleanup(ITestResult result) throws Exception {
		String testString = "{\"id\":\"2\","
				+ "\"firstName\":\"Phong\","
				+ "\"lastName\":\"Nguyễn\","
				+ "\"birthday\":\"1999-09-13\","
				+ "\"gender\":\"1\","
				+ "\"email\":\"phongcnc@gmail.com\","
				+ "\"phone\":\"0978665412\","
				+ "\"address\":\"108/7ANguyễnBỉnhKhiêm\","
				+ "\"account\":{\"username\":\"phong\","
				+ "\"password\":\"123\","
				+ "\"active\":true},"
				+ "\"role\":{\"id\":3}}";
		
		if (result.getStatus() == ITestResult.SUCCESS) {
			mockMvc.perform(put("/customer/updateInfo")
					.contentType(MediaType.APPLICATION_JSON)
					.content(testString));
	   }
	}
}
