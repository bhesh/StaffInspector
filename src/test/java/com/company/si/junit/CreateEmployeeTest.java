package com.company.si.junit;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.company.si.Address;
import com.company.si.Employee;

public class CreateEmployeeTest extends MockServerBaseUnitTest {

	@Autowired
	private MockMvc mockMvc;

	private static boolean setupFlag = false;

	/**
	 * Clean the database between each testing class
	 */
	@Before
	public void init() {
		if (!setupFlag) {
			staffRepo.deleteAll();
			staffIndexRepo.deleteAll();
			setupFlag = true;
		}
	}

	@Test
	public void basicNewEmployeeTest_01() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff").contentType(MediaType.APPLICATION_JSON)
				.content("{}").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		Employee employee = new Employee(1);

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(employee), response.getContentAsString(), false);
	}

	@Test
	public void basicNewEmployeeTest_02() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonMap = mapper.createObjectNode();
		jsonMap.put("firstname", "John");
		jsonMap.put("lastname", "Adams");
		jsonMap.put("active", true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(jsonMap)).accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		Employee employee = new Employee(2);
		employee.setFirstname("John");
		employee.setLastname("Adams");
		employee.setActive(true);

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(employee), response.getContentAsString(), false);
	}

	@Test
	public void basicNewEmployeeTest_03() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonMap = mapper.createObjectNode();
		jsonMap.put("firstname", "Thomas");
		jsonMap.put("lastname", "Jefferson");
		jsonMap.put("email", "thomas.jefferson@mail.mil");
		jsonMap.put("active", false);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(jsonMap)).accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		Employee employee = new Employee(3);
		employee.setFirstname("Thomas");
		employee.setLastname("Jefferson");
		employee.setEmail("thomas.jefferson@mail.mil");
		employee.setActive(false);

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(employee), response.getContentAsString(), false);
	}

	@Test
	public void basicNewEmployeeTest_04() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonMap = mapper.createObjectNode();
		jsonMap.put("firstname", "James");
		jsonMap.put("lastname", "Madison");
		jsonMap.put("phone", "301-225-0000");
		jsonMap.put("active", true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(jsonMap)).accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		Employee employee = new Employee(4);
		employee.setFirstname("James");
		employee.setLastname("Madison");
		employee.setPhone("301-225-0000");
		employee.setActive(true);

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(employee), response.getContentAsString(), false);
	}

	@Test
	public void basicNewEmployeeTest_05() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonMap = mapper.createObjectNode();
		jsonMap.put("firstname", "James");
		jsonMap.put("lastname", "Monroe");
		ObjectNode addrMap = mapper.createObjectNode();
		addrMap.put("address1", "1600 Pennsylvania Ave NW");
		addrMap.put("city", "Washington");
		addrMap.put("state", "DC");
		addrMap.put("zipcode", 20500);
		jsonMap.put("address", addrMap);
		jsonMap.put("active", false);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(jsonMap)).accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		Employee employee = new Employee(5);
		employee.setFirstname("James");
		employee.setLastname("Monroe");
		employee.setAddress(new Address("1600 Pennsylvania Ave NW", "Washington", "DC", 20500));
		employee.setActive(false);

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(employee), response.getContentAsString(), false);
	}

	@Test
	public void basicNewEmployeeTest_06() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonMap = mapper.createObjectNode();
		jsonMap.put("firstname", "John");
		jsonMap.put("lastname", "Adams");
		jsonMap.put("middleinitial", "Q");
		jsonMap.put("dateHired", "1825-01-20");
		jsonMap.put("active", true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(jsonMap)).accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		/**
		 * Spring will serialize Date properly but ObjectMapper will return the date in
		 * a Long format
		 */
		ObjectNode expected = mapper.createObjectNode();
		expected.put("employeeId", 6);
		expected.put("firstname", "John");
		expected.put("lastname", "Adams");
		expected.put("middleinitial", "Q");
		expected.putNull("email");
		expected.putNull("phone");
		expected.putNull("position");
		expected.putNull("address");
		expected.put("dateHired", "1825-01-20");
		expected.put("active", true);

		JSONAssert.assertEquals(mapper.writeValueAsString(expected), response.getContentAsString(), false);
	}
}
