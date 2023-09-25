Project Configuration

This project is a Spring Boot application that uses Cucumber for behavior-driven development (BDD) testing. The project is built with Maven and uses the Maven Wrapper for building the project.
Maven Configuration

The project uses Maven for dependency management and building the project. The Maven configuration is specified in the pom.xml file. The project uses the Spring Boot Starter Parent for dependency management.

The project uses Java 17, as specified in the properties section of the pom.xml file.

```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.1.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.francislainy</groupId>
	<artifactId>cucumber-spring-boilerplate</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>cucumber-spring-boilerplate</name>
	<description>cucumber-spring-boilerplate</description>
	<properties>
		<java.version>17</java.version>
		<maven.compiler.source>${java.version}</maven.compiler.source>
		<maven.compiler.target>${java.version}</maven.compiler.target>

		<testcontainers.version>1.17.4</testcontainers.version>

		<cucumber.version>7.12.0</cucumber.version>
		<extent-report.version>1.13.0</extent-report.version>

		<curl.logger.version>2.1.3</curl.logger.version>
		<lombok.version>1.18.30</lombok.version>
	</properties>
```

Cucumber Configuration

Cucumber is configured to run with Spring Boot. The CucumberSpringConfiguration class is annotated with @CucumberContextConfiguration and @SpringBootTest to integrate Cucumber with Spring Boot.

```java
package com.francislainy.cucumberspringboilerplate.steps.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {

}
```

The EntryPointITTest class is the entry point for running the Cucumber tests. It is annotated with @Suite, @SelectClasspathResource, and @ConfigurationParameter to specify the location of the feature files and the glue code.

```java
package com.francislainy.cucumberspringboilerplate;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("features")
@ConfigurationParameter(
		key = Constants.GLUE_PROPERTY_NAME,
		value = "com.francislainy.cucumberspringboilerplate.steps")
public class EntryPointITTest {
}
```

Test Configuration

The TestConfig class is annotated with @ComponentScan and @EnableAutoConfiguration to configure the Spring Boot application context for the tests.

```java
package com.francislainy.cucumberspringboilerplate.steps.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.francislainy.cucumberspringboilerplate", "com.francislainy.cucumberspringboilerplate.client"})
@EnableAutoConfiguration
public class TestConfig {

}
```

RestClient Configuration

The RestClient class is used to make HTTP requests. It uses RestAssured for making the requests and is configured to log the requests as cURL commands.

```java
package com.francislainy.cucumberspringboilerplate.client;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClient {

    public RequestSpecification getRequestSpecification() {

        /* Enables printing request as curl under the terminal as per https://github.com/dzieciou/curl-logger */
        Options options = Options.builder()
                .printMultiliner()
                .updateCurl(curl -> curl
                        .removeHeader("Host")
                        .removeHeader("User-Agent")
                        .removeHeader("Connection"))
                .build();

        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);

        return given()
                .config(config)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .urlEncodingEnabled(false)
                .when()
                .log()
                .everything();
    }

}
```

Test Reports

The test reports are generated in the test-output directory. The reports are generated in HTML and JSON formats.

Feature Files

The feature files for the Cucumber tests are located in the src/test/resources/features directory.

```gherkin
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
```

Step Definitions

Step definitions in Cucumber map the plain text Gherkin steps to their execution code. They are written in Java and are located in the com.francislainy.cucumberspringboilerplate.steps package.

Each step definition class contains methods annotated with @Given, @When, @Then, @And, or @But. These methods correspond to each step in your Gherkin scenarios. The method should contain the code to execute for that step.

Here is an example of what a step definition might look like, taking into consideration what we have inside this project:

```java
package com.francislainy.cucumberspringboilerplate.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ExampleSteps {

    @Given("the Bored API is available")
    public void theBoredAPIIsAvailable() {
        // Code to check if the API is available
    }

    @When("I send a GET request to the Bored API")
    public void iSendAGETRequestToTheBoredAPI() {
        // Code to send a GET request
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int status) {
        // Code to check the response status
    }

    @And("the response should contain a random activity")
    public void theResponseShouldContainARandomActivity() {
        // Code to check the response content
    }
}
```



