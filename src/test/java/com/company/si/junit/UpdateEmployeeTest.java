package com.company.si.junit;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
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

public class UpdateEmployeeTest extends MockServerBaseUnitTest {

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

	/**
	 * Grabs an Employee by their employeeId
	 * 
	 * @param employeeId
	 * @return
	 * @throws Exception
	 */
	private Employee getByEmployeeId(int employeeId) throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff/" + employeeId)
				.accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.readValue(response.getContentAsByteArray(), Employee.class);

		return employee;
	}

	/**
	 * Modifies an Employee by their employeeId
	 * 
	 * @param employeeId
	 * @param modified
	 * @return
	 * @throws Exception
	 */
	private Employee modifyByEmployeeId(int employeeId, Employee modified) throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/staff/" + employeeId)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(modified))
				.accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertTrue(response.getStatus() == 200);

		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.readValue(response.getContentAsByteArray(), Employee.class);

		return employee;
	}

	@Test
	public void updateByEmployeeIdTest_01() throws Exception {
		int employeeId = 1;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setDateHired(new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-10"));
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}

	@Test
	public void updateByEmployeeIdTest_02() throws Exception {
		int employeeId = 2;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setEmail("new.email@localhost");
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}

	@Test
	public void updateByEmployeeIdTest_03() throws Exception {
		int employeeId = 3;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setFirstname("NewName");
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}

	@Test
	public void updateByEmployeeIdTest_04() throws Exception {
		int employeeId = 4;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setLastname("NewSurname");
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}

	@Test
	public void updateByEmployeeIdTest_05() throws Exception {
		int employeeId = 5;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setMiddleinitial("A");
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}

	@Test
	public void updateByEmployeeIdTest_06() throws Exception {
		int employeeId = 6;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setPhone("123-456-7890");
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}

	@Test
	public void updateByEmployeeIdTest_07() throws Exception {
		int employeeId = 7;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setPosition("Impeached");
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}

	@Test
	public void updateByEmployeeIdTest_08() throws Exception {
		int employeeId = 8;
		Employee before = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(presidents.get(employeeId - 1)));
		before.setActive(false);
		modifyByEmployeeId(employeeId, before);
		Employee after = getByEmployeeId(employeeId);
		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!after.equals(presidents.get(employeeId - 1)));
	}
}
