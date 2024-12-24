
package com.sevolutivo.demo.web;

import com.sevolutivo.demo.model.ErrorMessage;
import com.sevolutivo.demo.model.Task;
import com.sevolutivo.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task/all")
    public List<Task> getAllTasks(){
        return taskService.getTasks();
    }
    
    @GetMapping("/task/id/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id){
        Task task;
        try {
            task = taskService.getTask(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
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
}
