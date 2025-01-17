package com.sevolutivo.demo.jmh;

import org.openjdk.jmh.annotations.*;

import com.sevolutivo.demo.model.Task;
import com.sevolutivo.demo.service.TaskService;

@State(Scope.Thread)
public class UpdateTasksState {
    public TaskService taskService;

    @Setup(Level.Invocation)
    public void setUp() {
        taskService = new TaskService();
        taskService.deleteAllTasks();

        for (var i = 1; i <= 10000; i++) {
            taskService.addTask(new Task(
                    0,
                    String.format("Title %d", i),
                    String.format("Description %d", i)));
        }
    }
}
