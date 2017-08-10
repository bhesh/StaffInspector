package com.company.si.junit;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.company.si.Employee;

public class ImportEmployeesTest extends MockServerBaseUnitTest {

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
	public void basicImportEmployeesTest_01() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff/import")
				.contentType(MediaType.APPLICATION_JSON).content("[{},{},{},{},{}]").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		List<Employee> employees = new ArrayList<Employee>();
		for (int i = 1; i <= 5; ++i)
			employees.add(new Employee(i));

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(employees), response.getContentAsString(), false);
	}
}
