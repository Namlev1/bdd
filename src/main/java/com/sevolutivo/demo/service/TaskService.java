package com.sevolutivo.demo.service;

import com.sevolutivo.demo.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int id = 0;

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        throwIfTaskIsInvalid(task);
        for(Task t : tasks) {
            if (t.getTitle().equals(task.getTitle())) {
                throw new IllegalArgumentException("Task title already exists");
            }
        }
        task.setId(++id);
        tasks.add(task);
    }

    public Task getTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        throw new NoSuchElementException("No such Task");
    }

    public Task getTask(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        throw new NoSuchElementException("No such Task");
    }

    public Task updateTask(Task task) {
        int index = tasks.indexOf(task);
        if (index == -1) {
            throw new NoSuchElementException("No such Task");
        }
        tasks.set(index, task);
        return task;
    }

    private void throwIfTaskIsInvalid(Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        if (task.getTitle().length() > 50) {
            throw new IllegalArgumentException("Task title is longer than 50");
        }
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
        if (task.getDescription().length() > 50) {
            throw new IllegalArgumentException("Task description is longer than 50");
        }
    }
}
