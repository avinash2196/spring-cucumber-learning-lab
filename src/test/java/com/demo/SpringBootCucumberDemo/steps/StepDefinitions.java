package com.demo.SpringBootCucumberDemo.steps;

import com.demo.SpringBootCucumberDemo.service.CalculationService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Cucumber step definitions that map Gherkin steps to application logic.
 *
 * <p>Each method corresponds to a step in a {@code .feature} file via the
 * {@code @Given}, {@code @When}, and {@code @Then} annotations. Spring beans
 * (including {@link CalculationService}) are injected by Cucumber's Spring
 * integration, configured in
 * {@link com.demo.SpringBootCucumberDemo.CucumberSpringConfiguration}.</p>
 *
 * <p><b>Learning note:</b> BDD (Behaviour-Driven Development) encourages
 * writing test scenarios in plain language (Gherkin) so that both technical and
 * non-technical stakeholders can understand what is being tested. Cucumber
 * translates those natural-language steps into executable Java methods here.</p>
 *
 * <p><b>Design note:</b> Step definitions should remain thin — they capture
 * inputs, invoke a service or controller method, and assert the outcome. Complex
 * assertion logic should live in helper methods or shared test utilities.</p>
 */
public class StepDefinitions {

    @Autowired
    private CalculationService calculationService;

    private int a;
    private int b;
    private int result;

    @Given("I have two numbers {int} and {int}")
    public void i_have_two_numbers(int num1, int num2) {
        a = num1;
        b = num2;
    }

    @When("I add them")
    public void i_add_them() {
        result = calculationService.add(a, b);
    }

    @When("I subtract them")
    public void i_subtract_them() {
        result = calculationService.subtract(a, b);
    }

    @When("I multiply them")
    public void i_multiply_them() {
        result = calculationService.multiply(a, b);
    }

    @When("I divide them")
    public void i_divide_them() {
        result = calculationService.divide(a, b);
    }

    @Then("I should get {int}")
    public void i_should_get(int expected) {
        assertEquals(expected, result);
    }
}
