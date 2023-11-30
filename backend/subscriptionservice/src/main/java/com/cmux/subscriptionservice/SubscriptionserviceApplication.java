package com.cmux.subscriptionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@ComponentScan("com.cmux.controller")
@EntityScan("com.cmux.entity")
@EnableNeo4jRepositories("com.cmux.repository")
public class SubscriptionserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionserviceApplication.class, args);
	}

}
