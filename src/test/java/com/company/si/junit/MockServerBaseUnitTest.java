package com.company.si.junit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.si.SIErrorController;
import com.company.si.SIRestController;
import com.company.si.StaffIndexRepository;
import com.company.si.StaffRepository;
import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbConfiguration;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.SpringMongoDbRule;
import com.mongodb.MockMongoClient;
import com.mongodb.MongoClient;

/**
 * All of the JUnit tests will extend MockServerBaseUnitTest
 * 
 * It overrides the default Mongo configuration with FongoDBConfiguration and
 * starts the mock mvc spring server using the 2 REST controllers defined in
 * com.company.si
 * 
 * @author Brian Hession
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FongoDBConfiguration.class })
@WebMvcTest(controllers = { SIRestController.class,
		SIErrorController.class }, secure = false, excludeAutoConfiguration = { MongoAutoConfiguration.class,
				MongoDataAutoConfiguration.class })
public abstract class MockServerBaseUnitTest {

	@ClassRule
	public static InMemoryMongoDb inMemoryMongoDb = InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule()
			.build();

	@Rule
	public MongoDbRule mongoDbRule = getSpringMongoDbRule();

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected StaffRepository staffRepo;

	@Autowired
	protected StaffIndexRepository staffIndexRepo;

	/**
	 * This method should not be necessary and is a bit redundant. However, without
	 * it, Spring would try (and fail) to connect to localhost:27017
	 * 
	 * @return
	 */
	private SpringMongoDbRule getSpringMongoDbRule() {
		MongoDbConfiguration mongoDbConfiguration = new MongoDbConfiguration();
		mongoDbConfiguration.setDatabaseName("si-db");
		MongoClient mongo = MockMongoClient.create(new Fongo("si-server"));
		mongoDbConfiguration.setMongo(mongo);
		return new SpringMongoDbRule(mongoDbConfiguration);
	}
}
