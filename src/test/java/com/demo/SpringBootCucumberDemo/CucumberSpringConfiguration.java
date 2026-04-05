package com.demo.SpringBootCucumberDemo;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Bridges Cucumber's context management with Spring Boot's test context.
 *
 * <p>This class is required exactly once in the glue path when using
 * {@code cucumber-spring}. It tells Cucumber to bootstrap a
 * {@link SpringBootTest} application context and share it across all step
 * definition classes — meaning beans (like {@link com.demo.SpringBootCucumberDemo.service.CalculationService})
 * can be {@code @Autowired} directly in step classes.</p>
 *
 * <p><b>Learning note:</b> Without this class, Cucumber would not know how to
 * start a Spring context, and {@code @Autowired} in step definitions would fail.
 * Having it as a separate class (rather than on the runner) keeps concerns
 * clearly separated.</p>
 */
@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {
}
