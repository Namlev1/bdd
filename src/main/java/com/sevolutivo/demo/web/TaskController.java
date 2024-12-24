
package com.sevolutivo.demo.web;

import com.sevolutivo.demo.model.Task;
import com.sevolutivo.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task/all")
    public List<Task> greeting(){
        return taskService.getTasks();
    }
    
    @PostMapping("/task")
    public Task createTask(@RequestBody Task task){
        return taskService.addTask(task);
    }
}
