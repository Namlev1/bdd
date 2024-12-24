
package com.sevolutivo.demo.web;

import com.sevolutivo.demo.model.Task;
import com.sevolutivo.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final TaskService taskService;

    @GetMapping("/task/all")
    public List<Task> greeting(){
        return taskService.getTasks();
    }
}
