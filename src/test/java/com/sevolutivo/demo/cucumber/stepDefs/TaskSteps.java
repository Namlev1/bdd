package com.sevolutivo.demo.cucumber.stepDefs;

import com.sevolutivo.demo.cucumber.CucumberSpringConfiguration;
import com.sevolutivo.demo.model.Task;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@CucumberContextConfiguration
public class TaskSteps extends CucumberSpringConfiguration {

    private ResponseEntity<Object> response;
    private Task task;
    private int id;

    @Autowired
    public TaskSteps(TestRestTemplate testRestTemplate) {
        super(testRestTemplate);
    }

    @Given("client has a task {string}, description {string}")
    public void clientHasATaskDescription(String title, String description) {
        task = new Task(0, title, description);
    }

    @Given("client wants to get task by id {int}")
    public void clientWantsToGetTaskById(int id) {
        this.id = id;
    }

    @Given("client wants to delete task by id {int}")
    public void clientWantsToDeleteTaskById(int id) {
        this.id = id;
    }

    @Given("client has a task {string}, description null")
    public void clientHasATaskDescriptionNull(String title) {
        task = new Task(0, title, null);
    }

    @Given("client has a task with title {string}, description {string}")
    public void clientHasATaskWithTitleAndDescription(String title, String description) {
        task = new Task(0, title, description);
    }

    @Given("client has created tasks with titles {string}, {string}, {string}")
    public void clientHasCreatedTasksWithTitles(String title1, String title2, String title3) {
        testRestTemplate.postForEntity("/task/", new Task(0, title1, ""), Object.class);
        testRestTemplate.postForEntity("/task/", new Task(0, title2, ""), Object.class);
        testRestTemplate.postForEntity("/task/", new Task(0, title3, ""), Object.class);
    }

    @When("client calls GET all tasks endpoint")
    public void clientCallsGetAllTasksEndpoint() {
        response = testRestTemplate.getForEntity("/task/all/", Object.class);
    }

    @When("client calls POST task endpoint")
    public void clientCallsPOSTTaskEndpoint() {
        response = testRestTemplate.postForEntity("/task/", task, Object.class);
    }

    @When("client calls PATCH task endpoint with id {int}")
    public void clientCallsPUTTaskEndpoint(int id) {
        response = testRestTemplate.exchange(
                "/task/" + id,
                HttpMethod.PATCH,
                new HttpEntity<>(task),
                // @formatter:off
                new ParameterizedTypeReference<Object>() {
                }
                // @formatter:on
        );
    }

    @When("client calls GET task endpoint with id")
    public void clientCallsGETTaskEndpointWithId() {
        response = testRestTemplate.getForEntity("/task/id/" + id, Object.class);
    }

    @When("client calls DELETE task endpoint with id")
    public void clientCallsDeleteTaskEndpoint() {
        response = testRestTemplate.exchange("/task/" + id,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                // @formatter:off
                new ParameterizedTypeReference<Object>() {
                }
                // @formatter:on
        );
    }

    @Then("client receives all tasks \\(empty list)")
    public void clientReceivesAllTasksEmptyList() {
        List<Task> expectedTasks = new ArrayList<>();
        Assertions.assertEquals(expectedTasks, response.getBody());
    }

    @Then("client receives task with assigned id")
    public void clientReceivesTaskWithAssignedId() {
        Task responseTask = mapToTask((LinkedHashMap<String, Object>) response.getBody());
        Assertions.assertNotEquals(0, responseTask.getId());
    }

    @Then("client receives task with id {int}")
    public void clientReceivesTaskWithId(int id) {
        Task responseTask = mapToTask((LinkedHashMap<String, Object>) response.getBody());
        Assertions.assertEquals(id, responseTask.getId());
    }

    @Then("client receives success")
    public void clientReceivesStatusCodeOK() {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("client receives error code NOT_FOUND")
    public void clientReceivesStatusCodeNOT_FOUND() {
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Then("client recieves task with id {int}, title {string}, description {string}")
    public void clientHasATaskDescription(int id, String title, String description) {
        Task expected = new Task(id, title, description);
        Task responseTask = mapToTask((LinkedHashMap<String, Object>) response.getBody());
        Assertions.assertEquals(expected, responseTask);
    }

    @Then("client receives error code BAD_REQUEST")
    public void clientReceivesStatusCodeBAD_REQUEST() {
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Then("client receives a list containing {string}, {string}, {string}")
    public void clientReceivesAListContaining(String title1, String title2, String title3) {
        List<LinkedHashMap<String, Object>> tasks = (List<LinkedHashMap<String, Object>>) response.getBody();
        List<String> titles = tasks.stream().map(t -> (String) t.get("title")).toList();
        Assertions.assertTrue(titles.containsAll(List.of(title1, title2, title3)));
    }

    private Task mapToTask(LinkedHashMap<String, Object> json) {
        Task task = new Task();
        task.setId((Integer) json.get("id"));
        task.setTitle((String) json.get("title"));
        task.setDescription((String) json.get("description"));
        return task;
    }
}
