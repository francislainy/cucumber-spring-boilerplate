Feature: Bored API CRUD operations

  Scenario: Read operation on Bored API
    Given the Bored API is available
    When I send a GET request to the Bored API
    Then the response status should be 200
    And the response should contain a random activity

  Scenario: Create operation on Bored API
    Given the Bored API is available
    When I send a POST request to the Bored API with a new activity
    Then the response status should be 200
    And the response should contain the new activity

  Scenario: Update operation on Bored API
    Given the Bored API is available
    And an existing activity from the Bored API
    When I send a PUT request to the Bored API with the updated activity
    Then the response status should be 200
    And the response should contain the updated activity

  Scenario: Delete operation on Bored API
    Given the Bored API is available
    And an existing activity from the Bored API
    When I send a DELETE request to the Bored API for the activity
    Then the response status should be 200
    And the activity should be removed from the Bored API
