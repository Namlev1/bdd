
Feature: the task endpoint can be retrieved
  Scenario: client makes call to GET all tasks (empty list)
    Given client wants to get all tasks
    When client calls GET all tasks endpoint
    Then client receives all tasks (empty list)