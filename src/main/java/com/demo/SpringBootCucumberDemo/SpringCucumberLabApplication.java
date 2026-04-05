package com.demo.SpringBootCucumberDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Cucumber Learning Lab application.
 *
 * <p>This project demonstrates layered REST API design, servlet filter-based request
 * logging, and end-to-end BDD testing with Cucumber. It is intended as a
 * learning reference, not a production-grade system.</p>
 *
 * <p><b>Learning note:</b> {@code @SpringBootApplication} is a convenience annotation
 * that combines {@code @Configuration}, {@code @EnableAutoConfiguration}, and
 * {@code @ComponentScan}. Spring Boot's auto-configuration wires up the embedded
 * Tomcat server, Jackson JSON serializer, and all detected Spring beans automatically.</p>
 */
@SpringBootApplication
public class SpringCucumberLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCucumberLabApplication.class, args);
	}
}
