package com.sevolutivo.seleniumPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TodoListPage extends BasePage {
    private By newTaskButton = By.xpath("/html/body/div/a");
    private By taskList = By.className("tasks");
    private By task = By.className("task");
    private By taskTitle = By.id("task-title");
    private By taskDescription = By.id("task-description");
    private By taskDetailsButton = By.id("task-details");
    private By taskDeleteButton = By.id("task-delete");

    public NewTaskPage clickNewTaskButton(){
        click(newTaskButton);
        return new NewTaskPage();
    }

    public List<WebElement> getTaskList(){
        return find(taskList).findElements(task);
    }

    public String getTaskTitle(WebElement task){
        return task.findElement(taskTitle).getText();
    }

    public String getTaskDescription(WebElement task){
        return task.findElement(taskDescription).getText();
    }

    public boolean containsTask(String title, String description){
        List<WebElement> taskList = getTaskList();
        for(WebElement task : taskList){
            if (getTaskTitle(task).equals(title) && getTaskDescription(task).equals(description)){
                return true;
            }
        }
        return false;
    }

    public void deleteTask(String title, String description){
        List<WebElement> taskList = getTaskList();
        for(WebElement task : taskList){
            if (getTaskTitle(task).equals(title) && getTaskDescription(task).equals(description)){
                task.findElement(taskDeleteButton).click();
            }
        }
    }

    public TaskDetailsPage taskDetails(String title, String description){
        List<WebElement> taskList = getTaskList();

        for(WebElement task : taskList){
            if (getTaskTitle(task).equals(title) && getTaskDescription(task).equals(description)){
                task.findElement(taskDetailsButton).click();
                return new TaskDetailsPage();
            }
        }

        return null;
    }
}
