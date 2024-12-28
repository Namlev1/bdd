
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
    Then client recieves task with id 1, title "Buy milk", description ""

  Scenario: client makes call to GET non-existing task
    Given client wants to get task by id 2
    When client calls GET task endpoint with id
    Then client receives error code NOT_FOUND

  Scenario: client makes call to PATCH existing task
    Given client has a task "Clean room", description "And chill"
    When client calls PATCH task endpoint with id 1
    Then client recieves task with id 1, title "Clean room", description "And chill"

  Scenario: client makes call to PATCH non-existing task
    Given client has a task "Clean room", description "And chill"
    When client calls PATCH task endpoint with id 2
    Then client receives error code NOT_FOUND

  Scenario: client makes call to DELETE existing task
    Given client wants to delete task by id 1
    When client calls DELETE task endpoint with id
    Then client receives success

  Scenario: client makes call to DELETE non-existing task
    Given client wants to delete task by id 2
    When client calls DELETE task endpoint with id
    Then client receives error code NOT_FOUND
