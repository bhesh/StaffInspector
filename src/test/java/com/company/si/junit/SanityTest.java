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

public class SanityTest extends MockServerBaseUnitTest {

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
	public void errorTest_01() throws Exception {
		int employeeId = 101;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff/" + employeeId)
				.accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 404);
	}

	@Test
	public void errorTest_02() throws Exception {
		int employeeId = 102;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/staff/" + employeeId)
				.contentType(MediaType.APPLICATION_JSON).content("{}").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 404);
	}

	@Test
	public void errorTest_03() throws Exception {
		int employeeId = 103;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/staff/" + employeeId)
				.accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 404);
	}

	@Test
	public void errorTest_04() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/nonexistant")
				.contentType(MediaType.APPLICATION_JSON).content("{}").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 404);
	}

	@Test
	public void errorTest_05() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/nonexistant").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 404);
	}

	@Test
	public void errorTest_06() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/nonexistant")
				.contentType(MediaType.APPLICATION_JSON).content("{}").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 404);
	}

	@Test
	public void errorTest_07() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/nonexistant")
				.accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 404);
	}
}
