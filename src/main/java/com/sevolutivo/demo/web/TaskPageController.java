package com.sevolutivo.demo.web;

import com.sevolutivo.demo.model.Task;
import com.sevolutivo.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TaskPageController {
    private final TaskService service;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("tasks", service.getTasks());
        return "home";
    }

    @GetMapping("/new")
    public String newTask() {
        return "new";
    }

    @PostMapping("/new")
    public String addTask(@ModelAttribute Task task, Model model) {
        try {
            service.addTask(task);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "new";
        }
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        model.addAttribute("task", service.getTask(id));
        return "details";
    }

    @PostMapping("/details")
    public String edit(@ModelAttribute Task task, Model model) {
        try {
            service.updateTask(task);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "details";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        service.deleteTask(id);
        return "redirect:/";
    }
}
