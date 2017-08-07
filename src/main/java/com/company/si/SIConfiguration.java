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

	private static String hostname = "192.168.79.10";
	private static int port = 27017;
	private static String database = "company";
	private static String user = "staffAdmin";

	@Bean
	public MongoClient getSIMongoClient() throws UnknownHostException {
		ServerAddress server = new ServerAddress(hostname, port);
		MongoCredential creds = MongoCredential.createCredential(user, database, "password".toCharArray());
		return new MongoClient(server, Arrays.asList(creds));
	}

	@Bean
	public MongoTemplate mongoTemplate() throws UnknownHostException {
		MongoTemplate template = new MongoTemplate(getSIMongoClient(), database);

		// Waits for acknowledgement when a write error/concern occurs
		template.setWriteConcern(WriteConcern.ACKNOWLEDGED);

		// Throw an exception on write errors
		template.setWriteResultChecking(WriteResultChecking.EXCEPTION);

		return template;
	}
}
