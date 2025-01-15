package com.sevolutivo.demo.selenium;

import com.sevolutivo.seleniumPages.NewTaskPage;
import com.sevolutivo.seleniumPages.TaskDetailsPage;
import com.sevolutivo.seleniumPages.TodoListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Tests extends BaseTest{

    @Test
    public void addNewTaskTest(){
        String title = "Buy apples";
        String description = "Buy 20kg of red apples";

        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        TodoListPage todoListPage = newTaskPage.addNewTask(title, description);
        Assert.assertTrue(todoListPage.containsTask(title, description));
    }

    @Test
    public void addExistingTaskTest(){
        String title = "Read a book";
        String description = "Spend 20 minutes reading a motivational book";
        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);


        todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);

        String message;

        try{
            message = newTaskPage.getErrorMessage();
        }catch (Exception e){
            message = "";
        }

        Assert.assertEquals(message ,"Task title already exists");
    }

    @Test
    public void addTaskWithTitleLongerThan50CharactersTest(){
        String title = "Organize and Declutter Your Entire Home, Room by Room";
        String description = "Tidy up each space individually.";

        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);


        String message;

        try{
            message = newTaskPage.getErrorMessage();
        }catch (Exception e){
            message = "";
        }

        Assert.assertEquals(message, "Task title is longer than 50");
    }

    @Test
    public void addTaskWithDescriptionLongerThan50CharactersTest(){
        String title = "Backup Important Files";
        String description = "Create a backup of your most important digital files on an external drive or cloud storage.";

        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);


        String message;

        try{
            message = newTaskPage.getErrorMessage();
        }catch (Exception e){
            message = "";
        }

        Assert.assertEquals(message, "Task description is longer than 50");
    }

    @Test
    public void addNewTaskWithoutDescriptionTest(){
        String title = "Pay the bills";
        String description = "";

        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        TodoListPage todoListPage = newTaskPage.addNewTask(title, description);
        Assert.assertTrue(todoListPage.containsTask(title, description));
    }

    @Test
    public void deleteTaskTest(){
        String title = "Sell my car";
        String description = "Sell my opel astra on facebook marketplace";
        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);

        todoListPage.deleteTask(title, description);

        Assert.assertFalse(todoListPage.containsTask(title, description));
    }

    @Test
    public void displayTaskDetailsTest(){
        String title = "Go for a run";
        String description = "Run 11 miles through the nearby forest.";
        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);

        TaskDetailsPage taskDetailsPage = todoListPage.taskDetails(title, description);

        Assert.assertTrue(taskDetailsPage.getTitleFieldValue().equals(title)
                && taskDetailsPage.getDescriptionFieldValue().equals(description));
    }


    @Test
    public void editTaskDetailsTest(){
        String title = "Water plants";
        String description = "Water all indoor plants";

        String newTitle = "Water the garden";
        String newDescription = "Water all outdoor plants";
        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);

        TaskDetailsPage taskDetailsPage = todoListPage.taskDetails(title, description);

        taskDetailsPage.setTitleField(newTitle);
        taskDetailsPage.setDescriptionField(newDescription);
        taskDetailsPage.clickSaveChangesButton();


        Assert.assertTrue(!todoListPage.containsTask(title, description)
                && todoListPage.containsTask(newTitle, newDescription));
    }

    @Test
    public void editTaskDescriptionWithOneThatIsLongerThan50CharactersTest(){
        String title = "Try a New Recipe";
        String description = "Explore a new recipe";

        String newDescription = "Explore a new recipe, gather ingredients, and prepare a delicious homemade dish to expand your cooking skills.";
        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);

        TaskDetailsPage taskDetailsPage = todoListPage.taskDetails(title, description);

        taskDetailsPage.setDescriptionField(newDescription);
        taskDetailsPage.clickSaveChangesButton();

        String message;

        try{
            message = taskDetailsPage.getErrorMessage();
        }catch (Exception e){
            message = "";
        }

        Assert.assertEquals(message, "Task description is longer than 50");
    }

    @Test
    public void removeTaskDescriptionTest(){
        String title = "Organize Cables";
        String description = "Tidy up loose cables";

        NewTaskPage newTaskPage = todoListPage.clickNewTaskButton();
        newTaskPage.addNewTask(title, description);

        TaskDetailsPage taskDetailsPage = todoListPage.taskDetails(title, description);

        taskDetailsPage.setDescriptionField("");
        taskDetailsPage.clickSaveChangesButton();


        Assert.assertTrue(!todoListPage.containsTask(title, description)
                && todoListPage.containsTask(title, ""));
    }


}
