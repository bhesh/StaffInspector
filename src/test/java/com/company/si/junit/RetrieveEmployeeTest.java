package com.company.si.junit;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.company.si.Employee;

public class RetrieveEmployeeTest extends MockServerBaseUnitTest {

	@Autowired
	private MockMvc mockMvc;

	private static List<Employee> presidents;

	private static boolean setupFlag = false;

	/**
	 * Clean the database between each testing class. Import presidents.json
	 * 
	 * @throws Exception
	 */
	@Before
	public void init() throws Exception {
		if (!setupFlag) {
			staffRepo.deleteAll();
			staffIndexRepo.deleteAll();

			// Load all the presidents into the database
			File file = new File(getClass().getResource("/presidents.json").getFile());
			FileInputStream fstream = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			int length = fstream.read(data);
			Assert.assertTrue(length == file.length());
			fstream.close();

			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff/import")
					.contentType(MediaType.APPLICATION_JSON).content(data).accept(MediaType.APPLICATION_JSON);

			MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
			Assert.assertTrue(response.getStatus() == 200);

			// Create a List of all of the presidents in the database
			ObjectMapper mapper = new ObjectMapper();
			presidents = mapper.readValue(response.getContentAsByteArray(),
					mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
			Assert.assertTrue(presidents.size() == 45);

			setupFlag = true;
		}
	}

	@Test
	public void getByEmployeeIdTest() throws Exception {
		for (int i = 0; i < presidents.size(); ++i) {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff/" + (i + 1))
					.accept(MediaType.APPLICATION_JSON);

			MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
			Assert.assertTrue(response.getStatus() == 200);

			ObjectMapper mapper = new ObjectMapper();
			Employee employee = mapper.readValue(response.getContentAsByteArray(), Employee.class);

			Assert.assertTrue(employee.equals(presidents.get(i)));
		}
	}

	@Test
	public void getAllEmployeesTest() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		ObjectMapper mapper = new ObjectMapper();
		List<Employee> employees = mapper.readValue(response.getContentAsByteArray(),
				mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));

		Assert.assertTrue(employees.size() == presidents.size());
		for (int i = 0; i < employees.size(); ++i)
			Assert.assertTrue(employees.get(i).equals(presidents.get(i)));
	}
}
