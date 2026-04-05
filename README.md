# spring-cucumber-learning-lab

> This repository is intended for learning, experimentation, and reference purposes. It is not designed as a production-grade system.

A Spring Boot REST API project that demonstrates end-to-end BDD testing with Cucumber, request lifecycle management via servlet filters, and clean layered architecture.

---

## Overview

This project implements a simple arithmetic calculator API. The calculator itself is intentionally minimal — the focus is on the **surrounding architecture**:

- A **layered REST API** (Controller → Service) with clear separation of concerns
- A **servlet filter** that intercepts, logs, and forwards every HTTP request
- **BDD-style tests** written with Cucumber and Gherkin
- **Unit tests** covering service logic in isolation
- **Integration tests** verifying the HTTP layer with Spring MockMvc

Understanding how HTTP requests flow through a Spring application — from the filter chain to the controller to the service and back — is foundational to building and debugging real web APIs.

---

## Real-World Context

The patterns in this project appear throughout production Spring Boot applications:

- **Request logging filters** are standard in microservices for audit trails, debugging, and distributed tracing integration (e.g. injecting correlation IDs into request headers).
- **BDD testing with Cucumber** is widely used when QA teams, product managers, and developers need a shared, human-readable test specification.
- **Controller/Service separation** is the baseline pattern in any maintainable Spring application.

**Example use cases where this architecture applies:**
- Internal REST APIs behind an API gateway
- Microservices that emit structured request/response logs to a central aggregator (e.g. ELK stack)
- Teams practising agile development where feature files serve as living documentation

---

## What This Repo Demonstrates

- Building a REST API with Spring Boot (`@RestController`, `@RequestMapping`, `ResponseEntity`)
- Service layer separation and constructor-based dependency injection
- Servlet filter implementation for cross-cutting concerns (logging, body caching)
- BDD testing with Cucumber, Gherkin, and the JUnit Platform runner
- Unit testing with JUnit 5, parameterized tests, and `@DisplayName`
- Spring Boot integration testing with `MockMvc`
- Lombok for compile-time boilerplate reduction
- Clean commit-ready project hygiene (`.gitignore`, MIT `LICENSE`)

---

## Architecture / Component Flow

```
HTTP Client
     │
     ▼
┌──────────────────────────────────────────┐
│               Filter Chain               │
│  RequestLoggingFilter                    │  ← logs method, URL, headers, body
│    └─ CachedBodyHttpServletRequest       │  ← wraps request so body can be re-read
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│         CalculationController            │  ← routes POST /api/calculate
│         (thin HTTP layer, no logic)      │
└──────────────────┬───────────────────────┘
                   │ delegates to
                   ▼
┌──────────────────────────────────────────┐
│          CalculationService              │  ← performs arithmetic, builds response
│          (business logic layer)          │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│          CalculationResponse             │  ← serialized to JSON and returned
└──────────────────────────────────────────┘
```

**Request lifecycle (step by step):**

1. Client sends `POST /api/calculate` with a JSON body.
2. `RequestLoggingFilter` intercepts the request, wraps it in `CachedBodyHttpServletRequest` (so the body can be read more than once), then logs the method, URL, headers, and body.
3. The request reaches `CalculationController`, which deserializes the JSON body into a `CalculationRequest`.
4. The controller delegates to `CalculationService.calculate(request)`.
5. The service dispatches to the correct arithmetic method (`add`, `subtract`, `multiply`, or `divide`) and builds a `CalculationResponse` with the result and an expression string.
6. The controller wraps the response in `ResponseEntity.ok(...)` and returns it.
7. Spring serializes the response object to JSON and writes it to the HTTP response.

---

## Tech Stack

| Technology           | Version       | Purpose                              |
|----------------------|---------------|--------------------------------------|
| Java                 | 17            | Runtime                              |
| Spring Boot          | 3.3.4         | Application framework                |
| Spring Web MVC       | (via Boot)    | REST API                             |
| Spring Data JPA      | (via Boot)    | ORM scaffolding (included, unused)   |
| H2                   | (via Boot)    | In-memory database                   |
| Cucumber             | 7.14.0        | BDD testing framework                |
| JUnit 5              | 5.10.x        | Unit and integration testing         |
| Lombok               | 1.18.26       | Boilerplate reduction                |
| Maven                | 3.x (wrapper) | Build tool                           |

---

## Project Structure

```
spring-cucumber-learning-lab/
├── src/
│   ├── main/
│   │   ├── java/com/demo/SpringBootCucumberDemo/
│   │   │   ├── SpringCucumberLabApplication.java      # Entry point
│   │   │   ├── controller/
│   │   │   │   └── CalculationController.java         # POST /api/calculate
│   │   │   ├── service/
│   │   │   │   └── CalculationService.java            # Business logic (add/subtract/multiply/divide)
│   │   │   ├── model/
│   │   │   │   ├── CalculationRequest.java            # JSON request model
│   │   │   │   └── CalculationResponse.java           # JSON response model
│   │   │   └── filter/
│   │   │       ├── RequestLoggingFilter.java          # Logs all incoming requests
│   │   │       ├── CachedBodyHttpServletRequest.java  # Enables body re-reading in filters
│   │   │       └── RequestLoggingFilterConfig.java    # Registers filter with Spring
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/demo/SpringBootCucumberDemo/
│       │   ├── CucumberTest.java                      # JUnit Platform Suite runner
│       │   ├── CucumberSpringConfiguration.java       # Bridges Cucumber with Spring context
│       │   ├── ApplicationContextTest.java            # Smoke test: context loads
│       │   ├── controller/
│       │   │   └── CalculationControllerTest.java     # MockMvc integration tests
│       │   ├── service/
│       │   │   └── CalculationServiceTest.java        # Unit tests (no Spring context)
│       │   └── steps/
│       │       └── StepDefinitions.java               # Cucumber step implementations
│       └── resources/
│           └── features/
│               └── calculation.feature                # BDD scenarios in Gherkin
├── .gitignore
├── LICENSE
├── pom.xml
└── README.md
```

---

## How to Run Locally

**Prerequisites:** Java 17+, no other tools needed (Maven Wrapper is included).

```bash
# Clone the repository
git clone https://github.com/<your-username>/spring-cucumber-learning-lab.git
cd spring-cucumber-learning-lab

# Build and start the application
./mvnw spring-boot:run
```

The API starts on **http://localhost:8080**.

<details>
<summary>Windows (Command Prompt / PowerShell)</summary>

```bat
mvnw.cmd spring-boot:run
```

</details>

<details>
<summary>Build a JAR and run it directly</summary>

```bash
./mvnw clean package -DskipTests
java -jar target/spring-cucumber-learning-lab-0.0.1-SNAPSHOT.jar
```

</details>

---

## How to Run Tests

```bash
# Run all tests: unit + MockMvc integration + Cucumber BDD
./mvnw clean test
```

After the run, test reports are available at:

| Report                           | Location                            |
|----------------------------------|-------------------------------------|
| JUnit XML results                | `target/surefire-reports/`          |
| Cucumber HTML report             | `target/cucumber-reports.html`      |

---

## Example Usage

**Add two numbers:**

```bash
curl -X POST http://localhost:8080/api/calculate \
  -H "Content-Type: application/json" \
  -d '{"a": 5, "b": 3, "operation": "add"}'
```

**Response:**

```json
{
  "a": 5,
  "b": 3,
  "operation": "add",
  "result": 8,
  "expression": "5 + 3 = 8"
}
```

**Supported operations:** `"add"`, `"subtract"`, `"multiply"`, `"divide"`

```bash
# Multiply
curl -X POST http://localhost:8080/api/calculate \
  -H "Content-Type: application/json" \
  -d '{"a": 6, "b": 7, "operation": "multiply"}'

# Divide
curl -X POST http://localhost:8080/api/calculate \
  -H "Content-Type: application/json" \
  -d '{"a": 10, "b": 2, "operation": "divide"}'
```

---

## Learning Outcomes

After exploring this project you should understand:

- How HTTP requests flow through a Spring Boot application (filter → controller → service)
- Why servlet filters exist and when to use them for cross-cutting concerns like logging
- The difference between a thin controller and a service layer, and why the separation matters
- How to write BDD tests with Cucumber and Gherkin and wire them to injected Spring beans
- How to test a Spring MVC controller in-process using `MockMvc` without starting a real server
- How to write isolated, parameterized unit tests with JUnit 5

---

## Limitations

This is a **learning project**. The following aspects are intentionally simplified:

- **No input validation** — The API does not validate missing or null fields. A production API would use `@Valid` with Bean Validation constraints.
- **No structured error handling** — Invalid operations or division by zero return a generic 500 error. A production API would use `@ControllerAdvice` for consistent error responses.
- **No persistence** — Spring Data JPA is included as a dependency but no entities or repositories are implemented.
- **No security** — There is no authentication or authorization on any endpoint.
- **Integer arithmetic only** — Division truncates decimals. A production calculator would use `BigDecimal` for precision.
- **Single module, single layer** — No multi-module Maven structure, no caching, no async processing.

---

## Future Improvements

- Add `@ControllerAdvice` with structured JSON error responses
- Add `@Valid` input validation with `jakarta.validation` constraints
- Add a `/history` endpoint backed by H2 JPA persistence (`CalculationEntity`, `CalculationRepository`)
- Add floating-point support via `BigDecimal`
- Integrate Spring Actuator for health and metrics endpoints
- Add OpenAPI / Swagger documentation via SpringDoc

---

## Suggested Repository Details

| Field               | Value                                                                                         |
|---------------------|-----------------------------------------------------------------------------------------------|
| **Repository name** | `spring-cucumber-learning-lab`                                                                |
| **Description**     | Spring Boot REST API demonstrating Cucumber BDD testing, servlet filters, and layered architecture — educational reference project |

---

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
