
package com.sevolutivo.demo.web;

import com.sevolutivo.demo.model.ErrorMessage;
import com.sevolutivo.demo.model.Task;
import com.sevolutivo.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task/all")
    public List<Task> getAllTasks(){
        return taskService.getTasks();
    }

    @GetMapping("/task/id/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        if (!id.matches("\\d+")) { // Sprawdzamy, czy ID składa się wyłącznie z cyfr
            return new ResponseEntity<>(new ErrorMessage("Invalid task ID format: " + id), HttpStatus.BAD_REQUEST);
        }

        try {
            int taskId = Integer.parseInt(id);
            Task task = taskService.getTask(taskId);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestBody Task task){
        try {
            task = taskService.addTask(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody Task task) {
        // Sprawdzenie, czy tytuł jest pusty
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            // Zwrócenie błędu 400, jeśli tytuł jest pusty
            return new ResponseEntity<>(new ErrorMessage("Task title cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        try {
            task.setId(id);
            task = taskService.updateTask(task);  // Wywołanie usługi do aktualizacji
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // Jeśli zadanie nie istnieje
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Jeśli wystąpi inny błąd
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Integer id) {
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/task/all")
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAllTasks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
