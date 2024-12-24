
Feature: the task endpoint can be retrieved
  Scenario: client makes call to GET all tasks (empty list)
    When client calls GET all tasks endpoint
    Then client receives all tasks (empty list)

  Scenario: client makes call to POST new task
    Given client has a task "Buy milk", description ""
    When client calls POST task endpoint
    Then client receives task with assigned id

  Scenario: client makes call to GET existing task
    Given client wants to get task by id 1
    When client calls GET task endpoint with id
    Then client receives task with id 1
    