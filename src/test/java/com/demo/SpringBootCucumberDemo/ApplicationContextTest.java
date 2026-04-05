package com.demo.SpringBootCucumberDemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test that verifies the Spring application context loads without errors.
 *
 * <p>If this test fails, it typically indicates a misconfigured bean, a missing
 * dependency, or a broken auto-configuration — making it a useful first signal
 * in CI pipelines.</p>
 */
@SpringBootTest
class ApplicationContextTest {

	@Test
	void contextLoads() {
	}
}
