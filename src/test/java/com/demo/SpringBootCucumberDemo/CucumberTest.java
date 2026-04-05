package com.demo.SpringBootCucumberDemo;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit Platform Suite that runs all Cucumber feature files.
 *
 * <p>This class is the entry point for the BDD test suite. It uses the JUnit 5
 * Platform launcher to discover and run {@code .feature} files found on the
 * classpath under {@code features/}.</p>
 *
 * <p><b>Learning note:</b> The {@code @Suite} + {@code @IncludeEngines("cucumber")}
 * approach is the modern (JUnit 5) replacement for the older
 * {@code @RunWith(Cucumber.class)} JUnit 4 runner. It integrates seamlessly
 * with Maven Surefire 3.x and IDE test runners without requiring JUnit Vintage.</p>
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key   = Constants.GLUE_PROPERTY_NAME,
        value = "com.demo.SpringBootCucumberDemo"
)
@ConfigurationParameter(
        key   = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty,html:target/cucumber-reports.html"
)
public class CucumberTest {
}
