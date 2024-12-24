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
public class TaskSteps extends CucumberSpringConfiguration {

    private ResponseEntity response;
    private Task task;
    private int id;

    @Given("client has a task {string}, description {string}")
    public void clientHasATaskDescription(String title, String description) {
        task = new Task(0, title, description);
    }

    @Given("client wants to get task by id {int}")
    public void clientWantsToGetTaskById(int id) {
        this.id = id;
    }

    @When("client calls GET all tasks endpoint")
    public void clientCallsGetAllTasksEndpoint() {
        response = testRestTemplate.getForEntity("/task/all/", List.class);
    }

    @When("client calls POST task endpoint")
    public void clientCallsPOSTTaskEndpoint() {
        response = testRestTemplate.postForEntity("/task/", task, Task.class);
    }

    @When("client calls GET task endpoint with id")
    public void clientCallsGETTaskEndpointWithId() {
        response = testRestTemplate.getForEntity("/task/id/" + id, Task.class);
    }

    @Then("client receives all tasks \\(empty list)")
    public void clientReceivesAllTasksEmptyList() {
        List<Task> expectedTasks = new ArrayList<>();
        Assertions.assertEquals(expectedTasks, response.getBody());
    }

    @Then("client receives task with assigned id")
    public void clientReceivesTaskWithAssignedId() {
        Assertions.assertNotEquals(0, ((Task) response.getBody()).getId());
    }

    @Then("client receives task with id {int}")
    public void clientReceivesTaskWithId(int id) {
        Assertions.assertEquals(id, ((Task) response.getBody()).getId());
    }
}
