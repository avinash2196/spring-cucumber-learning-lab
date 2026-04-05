package com.demo.SpringBootCucumberDemo.controller;

import com.demo.SpringBootCucumberDemo.model.CalculationRequest;
import com.demo.SpringBootCucumberDemo.model.CalculationResponse;
import com.demo.SpringBootCucumberDemo.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes the calculator API.
 *
 * <p>This controller handles HTTP concerns only: routing the request, delegating
 * computation to {@link CalculationService}, and returning a structured response.
 * It contains no business logic.</p>
 *
 * <p><b>Design note:</b> Keeping controllers thin ("thin controller, fat service") is
 * a key principle for maintainability. Business logic in controllers is a common code
 * smell — it makes the logic harder to unit-test and harder to reuse.</p>
 *
 * <p><b>Learning note:</b> {@code @RestController} combines {@code @Controller} and
 * {@code @ResponseBody}, automatically serializing return values to JSON.
 * Constructor injection (rather than field injection) is preferred because it makes
 * dependencies explicit and simplifies unit testing.</p>
 */
@RestController
@RequestMapping("/api")
public class CalculationController {

    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    /**
     * Performs an arithmetic calculation and returns the result.
     *
     * <p>Accepts a JSON body specifying two integer operands and an operation
     * ({@code "add"}, {@code "subtract"}, {@code "multiply"}, {@code "divide"}).
     * Returns the result with a human-readable expression string.</p>
     *
     * <p><b>Example request:</b> {@code {"a": 5, "b": 3, "operation": "add"}}<br>
     * <b>Example response:</b> {@code {"a": 5, "b": 3, "operation": "add", "result": 8, "expression": "5 + 3 = 8"}}</p>
     *
     * @param request the calculation request containing operands and operation name
     * @return {@code 200 OK} with the calculation result
     */
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        CalculationResponse response = calculationService.calculate(request);
        return ResponseEntity.ok(response);
    }
}

