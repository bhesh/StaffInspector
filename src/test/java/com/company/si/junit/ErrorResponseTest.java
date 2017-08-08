package com.company.si.junit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.company.si.SIErrorController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = SIErrorController.class, secure = false)
public class ErrorResponseTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getNotFoundError() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff/500").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertTrue(result.getResponse().getStatus() == 404);
	}
}
