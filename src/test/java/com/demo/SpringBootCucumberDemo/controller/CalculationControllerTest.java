package com.demo.SpringBootCucumberDemo.controller;

import com.demo.SpringBootCucumberDemo.model.CalculationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for {@link CalculationController}.
 *
 * <p>Uses {@code @SpringBootTest} + {@code @AutoConfigureMockMvc} to exercise the full
 * Spring MVC stack — including the filter chain (request logging), JSON serialization,
 * and service wiring — without starting an actual HTTP server.</p>
 *
 * <p><b>Learning note:</b> {@link MockMvc} simulates HTTP requests in-process. This is
 * faster than a full {@code @SpringBootTest(webEnvironment = RANDOM_PORT)} test while
 * still verifying that routing, JSON binding, and the filter chain work correctly end
 * to end. It is the recommended approach for controller-level integration tests in
 * Spring Boot projects.</p>
 *
 * <p><b>Design note:</b> These tests complement the unit tests in
 * {@link com.demo.SpringBootCucumberDemo.service.CalculationServiceTest} by verifying
 * the HTTP layer in addition to the business logic.</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CalculationController")
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/calculate - add: returns 200 with correct result and expression")
    void calculate_add_returns200WithResult() throws Exception {
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest(5, 3, "add"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(8))
                .andExpect(jsonPath("$.expression").value("5 + 3 = 8"))
                .andExpect(jsonPath("$.operation").value("add"));
    }

    @Test
    @DisplayName("POST /api/calculate - subtract: returns 200 with correct result")
    void calculate_subtract_returns200WithResult() throws Exception {
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest(10, 4, "subtract"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(6))
                .andExpect(jsonPath("$.expression").value("10 - 4 = 6"));
    }

    @Test
    @DisplayName("POST /api/calculate - multiply: returns 200 with correct result")
    void calculate_multiply_returns200WithResult() throws Exception {
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest(3, 4, "multiply"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(12))
                .andExpect(jsonPath("$.expression").value("3 * 4 = 12"));
    }

    @Test
    @DisplayName("POST /api/calculate - divide: returns 200 with correct result")
    void calculate_divide_returns200WithResult() throws Exception {
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest(10, 2, "divide"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5))
                .andExpect(jsonPath("$.expression").value("10 / 2 = 5"));
    }

    @Test
    @DisplayName("POST /api/calculate - response includes all fields: a, b, operation, result, expression")
    void calculate_responseContainsAllFields() throws Exception {
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest(6, 7, "multiply"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.a").value(6))
                .andExpect(jsonPath("$.b").value(7))
                .andExpect(jsonPath("$.operation").value("multiply"))
                .andExpect(jsonPath("$.result").value(42))
                .andExpect(jsonPath("$.expression").value("6 * 7 = 42"));
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
