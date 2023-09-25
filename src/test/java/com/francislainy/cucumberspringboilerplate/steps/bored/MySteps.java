package com.francislainy.cucumberspringboilerplate.steps.bored;

import com.francislainy.cucumberspringboilerplate.client.RestClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
public class MySteps {

    private final RestClient restClient;
    private Response response;

    @Given("the Bored API is available")
    public void theBoredAPIIsAvailable() {
        response = restClient.getRequestSpecification().get("https://www.boredapi.com/api/activity");
        assertEquals(200, response.getStatusCode());
    }

    @When("I send a GET request to the Bored API")
    public void iSendAGETRequestToTheBoredAPI() {
        response = restClient.getRequestSpecification().get("https://www.boredapi.com/api/activity");
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int arg0) {
        assertEquals(arg0, response.getStatusCode());
    }

    @And("the response should contain a random activity")
    public void theResponseShouldContainARandomActivity() {
        assertNotNull(response.jsonPath().getString("activity"));
    }

    @When("I send a POST request to the Bored API with a new activity")
    public void iSendAPOSTRequestToTheBoredAPIWithANewActivity() {
        // Assuming 'activity' is a Map object containing the details of the new activity
        Map<String, Object> activity = new HashMap<>();
        // Fill 'activity' with details of the new activity
        response = restClient.getRequestSpecification().body(activity).post("https://www.boredapi.com/api/activity");
    }

    @And("the response should contain the new activity")
    public void theResponseShouldContainTheNewActivity() {
        // Assuming 'activity' is a Map object containing the details of the new activity
        Map<String, Object> activity = new HashMap<>();
        // Fill 'activity' with details of the new activity
        for (Map.Entry<String, Object> entry : activity.entrySet()) {
            assertEquals(entry.getValue(), response.jsonPath().get(entry.getKey()));
        }
    }

    @And("an existing activity from the Bored API")
    public void anExistingActivityFromTheBoredAPI() {
        // Assuming 'activityId' is the ID of an existing activity
        String activityId = "1234";
        response = restClient.getRequestSpecification().get("https://www.boredapi.com/api/activity/" + activityId);
        assertEquals(200, response.getStatusCode());
    }

    @When("I send a PUT request to the Bored API with the updated activity")
    public void iSendAPUTRequestToTheBoredAPIWithTheUpdatedActivity() {
        // Assuming 'activity' is a Map object containing the details of the updated activity
        Map<String, Object> activity = new HashMap<>();
        // Fill 'activity' with details of the updated activity
        // Assuming 'activityId' is the ID of an existing activity
        String activityId = "1234";
        response = restClient.getRequestSpecification().body(activity).put("https://www.boredapi.com/api/activity/" + activityId);
    }

    @And("the response should contain the updated activity")
    public void theResponseShouldContainTheUpdatedActivity() {
        // Assuming 'activity' is a Map object containing the details of the updated activity
        Map<String, Object> activity = new HashMap<>();
        // Fill 'activity' with details of the updated activity
        for (Map.Entry<String, Object> entry : activity.entrySet()) {
            assertEquals(entry.getValue(), response.jsonPath().get(entry.getKey()));
        }
    }

    @When("I send a DELETE request to the Bored API for the activity")
    public void iSendADELETERequestToTheBoredAPIForTheActivity() {
        // Assuming 'activityId' is the ID of an existing activity
        String activityId = "1234";
        response = restClient.getRequestSpecification().delete("https://www.boredapi.com/api/activity/" + activityId);
    }

    @And("the activity should be removed from the Bored API")
    public void theActivityShouldBeRemovedFromTheBoredAPI() {
        // Assuming 'activityId' is the ID of an existing activity
        String activityId = "1234";
        response = restClient.getRequestSpecification().get("https://www.boredapi.com/api/activity/" + activityId);
        assertEquals(200, response.getStatusCode());
    }
}

