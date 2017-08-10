package com.company.si.junit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ErrorResponseTest extends MockServerBaseUnitTest {

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
	public void getNotFoundError() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertTrue(result.getResponse().getStatus() == 404);
	}
}
