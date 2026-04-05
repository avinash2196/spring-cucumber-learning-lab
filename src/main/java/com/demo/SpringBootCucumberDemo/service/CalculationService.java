package com.demo.SpringBootCucumberDemo.service;

import com.demo.SpringBootCucumberDemo.model.CalculationRequest;
import com.demo.SpringBootCucumberDemo.model.CalculationResponse;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for all arithmetic computations.
 *
 * <p>This class encapsulates the core business logic of the calculator feature.
 * Keeping logic in a dedicated service (rather than in the controller) enables
 * independent unit testing and makes it straightforward to extend or swap
 * the implementation later.</p>
 *
 * <p><b>Design note:</b> In a more complex system, this service could be backed by
 * a JPA repository to persist calculation history, or extended to support
 * floating-point arithmetic via {@code BigDecimal}. For this learning project,
 * integer arithmetic keeps the focus on the architectural patterns.</p>
 *
 * <p><b>Learning note:</b> The {@code @Service} annotation marks this class as a
 * Spring-managed component (a specialisation of {@code @Component}), enabling
 * dependency injection throughout the application context.</p>
 */
@Service
public class CalculationService {

    /**
     * Adds two integers.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts {@code b} from {@code a}.
     *
     * @param a the first operand
     * @param b the value to subtract
     * @return the difference
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two integers.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the product
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides {@code a} by {@code b} using integer division.
     *
     * <p><b>Note:</b> Decimal parts are truncated. This is intentional for this
     * learning project — a production API would use {@code BigDecimal} for precision.</p>
     *
     * @param a the dividend
     * @param b the divisor (must not be zero)
     * @return the integer quotient
     * @throws IllegalArgumentException if {@code b} is zero
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a / b;
    }

    /**
     * Executes a full calculation described by a {@link CalculationRequest}.
     *
     * <p>Dispatches to the appropriate arithmetic method based on the
     * {@code operation} field (case-insensitive) and returns an enriched
     * {@link CalculationResponse} that includes the computed result and a
     * human-readable expression string (e.g. {@code "5 + 3 = 8"}).</p>
     *
     * <p><b>Design note:</b> Using a {@code switch} expression here keeps the
     * dispatch concise while remaining easy to extend with new operations.</p>
     *
     * @param request the incoming calculation request
     * @return the result with expression details
     * @throws IllegalArgumentException if the operation is unrecognized or divisor is zero
     */
    public CalculationResponse calculate(CalculationRequest request) {
        int a = request.getA();
        int b = request.getB();
        String operation = request.getOperation().toLowerCase();

        int result = switch (operation) {
            case "add"      -> add(a, b);
            case "subtract" -> subtract(a, b);
            case "multiply" -> multiply(a, b);
            case "divide"   -> divide(a, b);
            default -> throw new IllegalArgumentException("Unsupported operation: " + operation);
        };

        String symbol = switch (operation) {
            case "add"      -> "+";
            case "subtract" -> "-";
            case "multiply" -> "*";
            case "divide"   -> "/";
            default         -> "?";
        };

        CalculationResponse response = new CalculationResponse();
        response.setA(a);
        response.setB(b);
        response.setOperation(operation);
        response.setResult(result);
        response.setExpression(a + " " + symbol + " " + b + " = " + result);
        return response;
    }
}

