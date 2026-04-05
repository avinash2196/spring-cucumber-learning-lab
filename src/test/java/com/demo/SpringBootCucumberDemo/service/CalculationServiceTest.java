package com.demo.SpringBootCucumberDemo.service;

import com.demo.SpringBootCucumberDemo.model.CalculationRequest;
import com.demo.SpringBootCucumberDemo.model.CalculationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CalculationService}.
 *
 * <p>These tests are isolated — no Spring context is loaded — making them fast
 * and focused purely on business logic. Each test covers a single behaviour
 * following the Arrange / Act / Assert pattern.</p>
 *
 * <p><b>Learning note:</b> Parameterized tests ({@code @ParameterizedTest} +
 * {@code @CsvSource}) let you verify the same logic across many input combinations
 * without duplicating test methods. This is especially useful for arithmetic
 * operations where edge cases (zero, negative values) matter.</p>
 */
@DisplayName("CalculationService")
class CalculationServiceTest {

    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
    }

    // -------------------------------------------------------------------------
    // add
    // -------------------------------------------------------------------------

    @ParameterizedTest(name = "add({0}, {1}) = {2}")
    @CsvSource({"5, 3, 8", "0, 0, 0", "-1, 1, 0", "100, -50, 50"})
    @DisplayName("add: returns correct sum for various inputs")
    void add_returnsCorrectSum(int a, int b, int expected) {
        assertEquals(expected, calculationService.add(a, b));
    }

    // -------------------------------------------------------------------------
    // subtract
    // -------------------------------------------------------------------------

    @ParameterizedTest(name = "subtract({0}, {1}) = {2}")
    @CsvSource({"10, 4, 6", "0, 5, -5", "-3, -3, 0", "7, 10, -3"})
    @DisplayName("subtract: returns correct difference for various inputs")
    void subtract_returnsCorrectDifference(int a, int b, int expected) {
        assertEquals(expected, calculationService.subtract(a, b));
    }

    // -------------------------------------------------------------------------
    // multiply
    // -------------------------------------------------------------------------

    @ParameterizedTest(name = "multiply({0}, {1}) = {2}")
    @CsvSource({"3, 4, 12", "0, 99, 0", "-2, 5, -10", "-3, -4, 12"})
    @DisplayName("multiply: returns correct product for various inputs")
    void multiply_returnsCorrectProduct(int a, int b, int expected) {
        assertEquals(expected, calculationService.multiply(a, b));
    }

    // -------------------------------------------------------------------------
    // divide
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("divide: returns correct integer quotient")
    void divide_returnsCorrectQuotient() {
        assertEquals(5, calculationService.divide(10, 2));
    }

    @Test
    @DisplayName("divide: truncates decimal result (integer division)")
    void divide_truncatesDecimalResult() {
        // 7 / 2 = 3.5 in real arithmetic; integer division yields 3
        assertEquals(3, calculationService.divide(7, 2));
    }

    @Test
    @DisplayName("divide: throws IllegalArgumentException when divisor is zero")
    void divide_throwsOnDivisionByZero() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> calculationService.divide(10, 0)
        );
        assertEquals("Division by zero is not allowed.", ex.getMessage());
    }

    // -------------------------------------------------------------------------
    // calculate (full dispatch + response building)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("calculate: builds enriched response for addition")
    void calculate_buildsEnrichedResponseForAdd() {
        CalculationRequest request = buildRequest(5, 3, "add");

        CalculationResponse response = calculationService.calculate(request);

        assertEquals(8,       response.getResult());
        assertEquals("5 + 3 = 8", response.getExpression());
        assertEquals("add",   response.getOperation());
        assertEquals(5,       response.getA());
        assertEquals(3,       response.getB());
    }

    @Test
    @DisplayName("calculate: builds enriched response for subtraction")
    void calculate_buildsEnrichedResponseForSubtract() {
        CalculationResponse response = calculationService.calculate(buildRequest(10, 4, "subtract"));
        assertEquals(6, response.getResult());
        assertEquals("10 - 4 = 6", response.getExpression());
    }

    @Test
    @DisplayName("calculate: builds enriched response for multiplication")
    void calculate_buildsEnrichedResponseForMultiply() {
        CalculationResponse response = calculationService.calculate(buildRequest(3, 4, "multiply"));
        assertEquals(12, response.getResult());
        assertEquals("3 * 4 = 12", response.getExpression());
    }

    @Test
    @DisplayName("calculate: operation matching is case-insensitive")
    void calculate_operationIsCaseInsensitive() {
        CalculationResponse response = calculationService.calculate(buildRequest(6, 2, "ADD"));
        assertEquals(8, response.getResult());
    }

    @Test
    @DisplayName("calculate: throws IllegalArgumentException for unrecognised operation")
    void calculate_throwsForUnknownOperation() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculationService.calculate(buildRequest(1, 1, "modulo"))
        );
    }

    // -------------------------------------------------------------------------
    // helpers
    // -------------------------------------------------------------------------

    private CalculationRequest buildRequest(int a, int b, String operation) {
        CalculationRequest request = new CalculationRequest();
        request.setA(a);
        request.setB(b);
        request.setOperation(operation);
        return request;
    }
}
