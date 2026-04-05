package com.demo.SpringBootCucumberDemo.model;

import lombok.Data;

/**
 * Represents an incoming calculation request.
 *
 * <p>Used as the deserialized JSON request body for {@code POST /api/calculate}.
 * Supports the four basic arithmetic operations via the {@code operation} field.</p>
 *
 * <p><b>Learning note:</b> Lombok's {@code @Data} generates getters, setters,
 * {@code equals}, {@code hashCode}, and {@code toString} at compile time,
 * reducing boilerplate without sacrificing readability.</p>
 */
@Data
public class CalculationRequest {

    /** The first operand. */
    private int a;

    /** The second operand. */
    private int b;

    /**
     * The arithmetic operation to perform.
     * Accepted values (case-insensitive): {@code "add"}, {@code "subtract"},
     * {@code "multiply"}, {@code "divide"}.
     */
    private String operation;
}
