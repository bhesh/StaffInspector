package com.company.si.junit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.company.si.StaffIndexRepository;
import com.company.si.StaffRepository;
import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackageClasses = { StaffRepository.class, StaffIndexRepository.class })
@ComponentScan(basePackages = { "com.company.si" })
public class FongoConfiguration extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "si-db";
	}

	@Bean
	@Override
	public MongoClient mongo() {
		return new Fongo("si-server").getMongo();
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.company.si";
	}
}
