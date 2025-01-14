package com.sevolutivo.demo.service;

import com.sevolutivo.demo.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final List<Task> tasks;
    private int id;

    public TaskService() {
        tasks = new ArrayList<>();
        id = 0;
        addTask(new Task(id++, "Buy milk", "Go to the store and just buy it"));
        addTask(new Task(id++, "Work out", "There is a sale at local gym"));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task addTask(Task task) {
        throwIfTaskIsInvalid(task);
        for(Task t : tasks) {
            if (t.getTitle().equals(task.getTitle())) {
                throw new IllegalArgumentException("Task title already exists");
            }
        }
        task.setId(++id);
        tasks.add(task);
        return task;
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
        throwIfTaskIsInvalid(task);
        int index = -1;
        for (int i = 0; i < tasks.size(); ++i) {
            if (tasks.get(i).getId() == task.getId()) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new NoSuchElementException("No such Task");
        }

        tasks.set(index, task);
        return task;
    }

    public void deleteTask(int id) {
        int index = -1;
        for (int i = 0; i < tasks.size(); ++i) {
            if (tasks.get(i).getId() == id) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new NoSuchElementException("No such Task");
        }

        tasks.remove(index);
    }

    private void throwIfTaskIsInvalid(Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        if (task.getTitle().length() > 50) {
            throw new IllegalArgumentException("Task title is longer than 50");
        }
        if (!task.getDescription().isEmpty() && task.getDescription().length() > 50) {
            throw new IllegalArgumentException("Task description is longer than 50");
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }


}
