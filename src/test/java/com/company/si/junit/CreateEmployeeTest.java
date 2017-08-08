package com.company.si.junit;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.company.si.Employee;
import com.company.si.SIRestController;
import com.company.si.SIService;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = SIRestController.class, secure = false)
@ContextConfiguration(classes = { FongoConfiguration.class })
public class CreateEmployeeTest {

	@ClassRule
	public static InMemoryMongoDb inMemoryMongoDb = InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule()
			.build();

	@Rule
	public MongoDbRule mongoDbRule = MongoDbRule.MongoDbRuleBuilder.newMongoDbRule().defaultSpringMongoDb("si-db");

	@SuppressWarnings("unused")
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SIService service;

	@Test
	public void basicNewEmployeeTest_01() throws Exception {

		Employee employee = new Employee(2);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/staff").contentType(MediaType.APPLICATION_JSON)
				.content("{}").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(employee),
				result.getResponse().getContentAsString(), false);
	}
}
