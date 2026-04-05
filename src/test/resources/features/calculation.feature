Feature: Arithmetic calculations

  As a developer learning Spring Boot and Cucumber BDD
  I want to verify that CalculationService handles the four basic arithmetic operations
  So that I can trust the core business logic before testing the HTTP layer

  Scenario Outline: Adding two numbers
    Given I have two numbers <a> and <b>
    When I add them
    Then I should get <result>

    Examples:
      | a   | b  | result |
      | 5   | 3  | 8      |
      | 0   | 0  | 0      |
      | -1  | 1  | 0      |
      | 100 | 50 | 150    |

  Scenario: Subtracting two numbers
    Given I have two numbers 10 and 4
    When I subtract them
    Then I should get 6

  Scenario: Multiplying two numbers
    Given I have two numbers 3 and 4
    When I multiply them
    Then I should get 12

  Scenario: Dividing two numbers
    Given I have two numbers 10 and 2
    When I divide them
    Then I should get 5
