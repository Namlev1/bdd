package com.sevolutivo.demo.jmh;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import com.sevolutivo.demo.service.TaskService;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Threads(4)
public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        URLClassLoader classLoader = (URLClassLoader) BenchmarkRunner.class.getClassLoader();
        StringBuilder classpath = new StringBuilder();
        for (URL url : classLoader.getURLs()) {
            classpath.append(url.getPath()).append(File.pathSeparator);
        }
        System.setProperty("java.class.path", classpath.toString());

        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void benchmarkGetTasks(Blackhole blackhole) {
        final var taskService = new TaskService();

        for (var i = 0; i < 10000; i++) {
            blackhole.consume(taskService.getTasks());
        }
    }

    @Benchmark
    public void benchmarkGetFirstTaskById(GetTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            blackhole.consume(state.taskService.getTask(3));
        }
    }

    @Benchmark
    public void benchmarkGetFirstTaskByTitle(GetTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            blackhole.consume(state.taskService.getTask("Title 1"));
        }
    }

    @Benchmark
    public void benchmarkGetLastTaskById(GetTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            blackhole.consume(state.taskService.getTask(10000));
        }
    }

    @Benchmark
    public void benchmarkGetLastTaskByTitle(GetTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            blackhole.consume(state.taskService.getTask("Title 10000"));
        }
    }

    @Benchmark
    public void benchmarkGetTaskByIdSequentially(GetTasksState state, Blackhole blackhole) {
        for (var i = 3; i <= 10000; i++) {
            blackhole.consume(state.taskService.getTask(i));
        }
    }

    @Benchmark
    public void benchmarkGetTaskByTitleSequentially(GetTasksState state, Blackhole blackhole) {
        for (var i = 1; i <= 10000; i++) {
            blackhole.consume(state.taskService.getTask(
                    String.format("Title %d", i)));
        }
    }

    @Benchmark
    public void benchmarkGetTaskByIdReverseSequentially(GetTasksState state, Blackhole blackhole) {
        for (var i = 10000; i > 2; i--) {
            blackhole.consume(state.taskService.getTask(i));
        }
    }

    @Benchmark
    public void benchmarkGetTaskByTitleReverseSequentially(GetTasksState state, Blackhole blackhole) {
        for (var i = 10000; i > 0; i--) {
            blackhole.consume(state.taskService.getTask(
                    String.format("Title %d", i)));
        }
    }

    @Benchmark
    public void benchmarkGetTaskByIdRandomly(GetTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            blackhole.consume(state.taskService.getTask((int) Math.floor(Math.random() * 10000) + 3));
        }
    }

    @Benchmark
    public void benchmarkGetTaskByTitleRandomly(GetTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            blackhole.consume(state.taskService.getTask(
                    String.format("Title %d", (int) Math.floor(Math.random() * 10000) + 1)));
        }
    }

    @Benchmark
    public void benchmarkDeleteTasksSequentially(DeleteTasksState state) {
        for (var i = 0; i < 10000; i++) {
            state.taskService.deleteTask(i + 3);
        }
    }

    @Benchmark
    public void benchmarkDeleteTasksReverseSequentially(DeleteTasksState state) {
        for (var i = 10000; i > 0; i--) {
            state.taskService.deleteTask(i + 2);
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void benchmarkDeleteAllTasks(DeleteTasksState state) {
        state.taskService.deleteAllTasks();
    }

    @Benchmark
    public void benchmarkUpdateTaskTitles(UpdateTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            final var task = state.taskService.getTask(i + 3);
            task.setTitle(String.format("Updated Title %d", task.getId()));
            blackhole.consume(state.taskService.updateTask(task));
        }
    }

    @Benchmark
    public void benchmarkUpdateTaskDescriptions(UpdateTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            final var task = state.taskService.getTask(i + 3);
            task.setDescription(String.format("Updated description %d", task.getId()));
            blackhole.consume(state.taskService.updateTask(task));
        }
    }

    @Benchmark
    public void benchmarkUpdateInvalidTaskTitles(UpdateTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            final var task = state.taskService.getTask(i + 3);
            task.setTitle("");
            try {
                blackhole.consume(state.taskService.updateTask(task));
            } catch (Exception e) {
            }
        }
    }

    @Benchmark
    public void benchmarkUpdateInvalidTaskDescriptions(UpdateTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            final var task = state.taskService.getTask(i + 3);
            task.setDescription("");
            try {
                blackhole.consume(state.taskService.updateTask(task));
            } catch (Exception e) {
            }
        }
    }

    @Benchmark
    public void benchmarkUpdateTaskTitlesAndDescriptions(UpdateTasksState state, Blackhole blackhole) {
        for (var i = 0; i < 10000; i++) {
            final var task = state.taskService.getTask(i + 3);
            task.setTitle(String.format("Updated Title %d", task.getId()));
            task.setDescription(String.format("Updated description %d", task.getId()));
            blackhole.consume(state.taskService.updateTask(task));
        }
    }
}
