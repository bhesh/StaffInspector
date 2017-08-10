package com.company.si;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

/**
 * SIConfiguration
 * 
 * Staff Inspector configuration
 * 
 * This class pulls the configuration details out from the configuration file.
 * Then Spring creates the beans with the provided information.
 * 
 * @author Brian Hession
 *
 */
@Configuration
public class SIConfiguration {

	private static final String HOSTNAME;
	private static final int PORT;
	private static final String DATABASE;
	private static final String USER;
	static {
		HOSTNAME = System.getProperty("com.company.si.hostname", "localhost");
		int port;
		try {
			port = Integer.parseInt(System.getProperty("com.company.si.port", "27017"));
		} catch (NumberFormatException e) {
			port = 27017;
		}
		PORT = port;
		DATABASE = System.getProperty("com.company.si.database", "company");
		USER = System.getProperty("com.company.si.database.user", "admin");
	}

	@Bean
	public MongoClient mongo() throws UnknownHostException {
		String password = System.getProperty("com.company.si.database.password", "");
		ServerAddress server = new ServerAddress(HOSTNAME, PORT);
		MongoCredential creds = MongoCredential.createCredential(USER, DATABASE, password.toCharArray());
		return new MongoClient(server, Arrays.asList(creds));
	}

	@Bean
	public MongoTemplate mongoTemplate() throws UnknownHostException {
		MongoTemplate template = new MongoTemplate(mongo(), DATABASE);

		// Waits for acknowledgement when a write error/concern occurs
		template.setWriteConcern(WriteConcern.ACKNOWLEDGED);

		// Throw an exception on write errors
		template.setWriteResultChecking(WriteResultChecking.EXCEPTION);

		return template;
	}
}
