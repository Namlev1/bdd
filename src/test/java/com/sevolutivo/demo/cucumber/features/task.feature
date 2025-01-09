Feature: The task endpoint can be retrieved and managed

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
    Then client receives task with id 1, title "Buy milk", description ""

  Scenario: client makes call to GET non-existing task
    Given client wants to get task by id 2
    When client calls GET task endpoint with id
    Then client receives error code NOT_FOUND

  Scenario: client makes call to PATCH existing task
    Given client has a task "Clean room", description "And chill"
    When client calls PATCH task endpoint with id 1
    Then client receives task with id 1, title "Clean room", description "And chill"

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

  Scenario: client makes call to POST with invalid task (empty title)
    Given client has a task "", description "Description of the task"
    When client calls POST task endpoint
    Then client receives error code BAD_REQUEST

  Scenario: client makes call to POST with invalid task (name longer than 50)
    Given client has a task with title "This title is intentionally made longer than fifty characters for testing purposes", description "Valid description"
    When client calls POST task endpoint
    Then client receives error code BAD_REQUEST

  Scenario: client makes call to POST with invalid task (description longer than 50)
    Given client has a task with title "Valid title", description "This description is intentionally made longer than fifty characters for testing purposes"
    When client calls POST task endpoint
    Then client receives error code BAD_REQUEST

  Scenario: client retrieves tasks when there are multiple tasks
    Given client has created tasks with titles "Task 1", "Task 2", "Task 3"
    When client calls GET all tasks endpoint
    Then client receives a list containing "Task 1", "Task 2", "Task 3"

  Scenario: client makes call to POST with duplicate task title
    Given client has a task "Duplicate Title", description "First task"
    And client calls POST task endpoint
    And client has a task "Duplicate Title", description "Second task"
    When client calls POST task endpoint
    Then client receives error code BAD_REQUEST

  # New scenarios

  Scenario: client tries to retrieve all tasks when there are no tasks
    Given no tasks exist
    When client calls GET all tasks endpoint
    Then client receives all tasks (empty list)

  Scenario: client tries to retrieve task with invalid ID format
    Given client wants to get task by id "abc"
    When client calls GET task endpoint with id
    Then client receives error code BAD_REQUEST

  Scenario: client creates a task with a valid but very long title
    Given client has a task with title "A task with a title that is very long and exceeds the usual limits of a task title field", description "Valid description"
    When client calls POST task endpoint
    Then client receives error code BAD_REQUEST

  Scenario: client tries to create a task with missing title
    Given client has a task "", description "Valid description"
    When client calls POST task endpoint
    Then client receives error code BAD_REQUEST


  Scenario: client tries to update a task that doesn’t exist
    Given client has a task "Laundry", description "Do it soon"
    When client calls PATCH task endpoint with id 999
    Then client receives error code NOT_FOUND

  Scenario: client tries to delete task that has already been deleted
    Given client wants to delete task by id 1
    And client calls DELETE task endpoint with id
    When client calls DELETE task endpoint with id 1 again
    Then client receives error code NOT_FOUND

  Scenario: client updates task with empty title
    Given client has a task "Go to gym", description "Evening session"
    When client updates task with empty title
    Then client receives error code BAD_REQUEST
