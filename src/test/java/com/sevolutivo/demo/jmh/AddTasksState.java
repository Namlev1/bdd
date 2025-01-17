package com.sevolutivo.demo.jmh;

import org.openjdk.jmh.annotations.*;

import com.sevolutivo.demo.service.TaskService;

@State(Scope.Thread)
public class AddTasksState {
    public TaskService taskService;

    @Setup(Level.Invocation)
    public void setUp() {
        taskService = new TaskService();
        taskService.deleteAllTasks();
    }
}
