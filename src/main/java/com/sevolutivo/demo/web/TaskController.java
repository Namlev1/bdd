
package com.sevolutivo.demo.web;

import com.sevolutivo.demo.model.Task;
import com.sevolutivo.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task/all")
    public List<Task> greeting(){
        return taskService.getTasks();
    }
    
    @GetMapping("/task/id/{id}")
    public Task getTaskById(@PathVariable Integer id){
        return taskService.getTask(id);
    }
    
    @PostMapping("/task")
    public Task createTask(@RequestBody Task task){
        return taskService.addTask(task);
    }
}
