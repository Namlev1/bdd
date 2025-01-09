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
    public void clientHasATaskWithDescription(String title, String description) {
        task = new Task(0, title, description);
    }

    @Given("client has a task with title {string}, description {string}")
    public void clientHasATaskWithTitleAndDescription(String title, String description) {
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

    @Given("client has created tasks with titles {string}, {string}, {string}")
    public void clientHasCreatedTasksWithTitles(String title1, String title2, String title3) {
        testRestTemplate.postForEntity("/task/", new Task(0, title1, ""), Object.class);
        testRestTemplate.postForEntity("/task/", new Task(0, title2, ""), Object.class);
        testRestTemplate.postForEntity("/task/", new Task(0, title3, ""), Object.class);
    }

    @Given("no tasks exist")
    public void no_tasks_exist() {
        cleanUpTasks();
    }

    private void cleanUpTasks() {
        testRestTemplate.delete("/task/all");
        ResponseEntity<List> response = testRestTemplate.getForEntity("/task/all", List.class);
        Assertions.assertTrue(response.getBody().isEmpty(), "The task list is not empty after cleanup.");
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
                new ParameterizedTypeReference<Object>() {}
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
                new ParameterizedTypeReference<Object>() {}
        );
    }

    @Then("client receives task with id {int}, title {string}, description {string}")
    public void clientReceivesTaskWithIdTitleDescription(int id, String title, String description) {
        Task expected = new Task(id, title, description);
        Task responseTask = mapToTask((LinkedHashMap<String, Object>) response.getBody());
        Assertions.assertEquals(expected, responseTask);
    }

    @Then("client receives success")
    public void clientReceivesStatusCodeOK() {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("client receives error code NOT_FOUND")
    public void clientReceivesStatusCodeNOT_FOUND() {
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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

    @Then("client receives all tasks \\(empty list)")
    public void clientReceivesAllTasksEmptyList() {
        List<Task> expectedTasks = new ArrayList<>();
        Assertions.assertEquals(expectedTasks, response.getBody());
    }

    @When("client updates task with empty title")
    public void clientUpdatesTaskWithEmptyTitle() {
        task.setTitle("");
        response = testRestTemplate.exchange(
                "/task/" + task.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(task),
                new ParameterizedTypeReference<Object>() {}
        );
    }

    private Task mapToTask(LinkedHashMap<String, Object> json) {
        Task task = new Task();
        if (json.containsKey("id") && json.get("id") != null) {
            task.setId(((Number) json.get("id")).intValue());
        } else {
            throw new IllegalArgumentException("Response does not contain valid 'id'");
        }

        if (json.containsKey("title") && json.get("title") != null) {
            task.setTitle((String) json.get("title"));
        } else {
            throw new IllegalArgumentException("Response does not contain valid 'title'");
        }

        if (json.containsKey("description")) {
            task.setDescription((String) json.get("description"));
        }

        return task;
    }

    @Given("client wants to get task by id {string}")
    public void clientWantsToGetTaskById(String id) {
        try {
            this.id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            this.id = -1; // oznacza błędny format ID
        }
    }

    @When("client calls DELETE task endpoint with id {int} again")
    public void clientCallsDeleteTaskEndpointWithIdAgain(Integer id) {
        response = testRestTemplate.exchange("/task/" + id,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Object>() {}
        );
    }

    @When("client updates the description to {string} and calls PATCH task endpoint with id {int}")
    public void clientUpdatesTheDescription(String description, int id) {
        this.task.setDescription(description);
        response = testRestTemplate.exchange(
                "/task/" + id,
                HttpMethod.PATCH,
                new HttpEntity<>(task),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Then("client receives task with assigned id")
    public void clientReceivesTaskWithAssignedId() {
        Task responseTask = mapToTask((LinkedHashMap<String, Object>) response.getBody());
        Assertions.assertNotEquals(0, responseTask.getId(), "The task ID should be assigned and not 0.");
    }


}
