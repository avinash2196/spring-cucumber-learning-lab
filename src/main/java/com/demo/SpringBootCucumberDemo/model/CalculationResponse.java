package com.demo.SpringBootCucumberDemo.model;

import lombok.Data;

/**
 * Represents the result of a calculation request.
 *
 * <p>Returned as the JSON response body from {@code POST /api/calculate}.
 * In addition to the numeric result it includes the human-readable expression
 * string, making the response self-describing for clients and logs.</p>
 *
 * <p><b>Design note:</b> Returning a structured object instead of a bare integer
 * allows the API to evolve — new fields (e.g. duration, precision info) can be
 * added without breaking existing consumers.</p>
 */
@Data
public class CalculationResponse {

    /** The first operand as submitted in the request. */
    private int a;

    /** The second operand as submitted in the request. */
    private int b;

    /** The operation that was performed. */
    private String operation;

    /** The computed result. */
    private int result;

    /** Human-readable expression, e.g. {@code "5 + 3 = 8"}. */
    private String expression;
}
