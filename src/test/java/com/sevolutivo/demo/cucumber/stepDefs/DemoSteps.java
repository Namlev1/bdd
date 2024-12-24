package com.sevolutivo.demo.cucumber.stepDefs;

import com.sevolutivo.demo.cucumber.CucumberSpringConfiguration;
import com.sevolutivo.demo.model.Task;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@CucumberContextConfiguration
public class DemoSteps extends CucumberSpringConfiguration {

    private ResponseEntity response;

    @Given("client wants to get all tasks")
    public void clientWantsToGetAllTasks() {
    }

    @When("client calls GET all tasks endpoint")
    public void clientCallsGetAllTasksEndpoint() {
        response = testRestTemplate.getForEntity("/task/all/", List.class);
    }

    @Then("client receives all tasks \\(empty list)")
    public void clientReceivesAllTasks() {
        List<Task> expectedTasks = new ArrayList<>();
        Assertions.assertEquals(expectedTasks, response.getBody());
    }
}
