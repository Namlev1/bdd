package com.sevolutivo.seleniumPages;

import org.openqa.selenium.By;

public class NewTaskPage extends BasePage {
    private By titleField = By.id("title");
    private By descriptionField = By.id("description");
    private By addButton = By.xpath("/html/body/div/form/button");
    private By errorMessage = By.xpath("/html/body/div/div/p");

    public void setTitle(String title) {
        set(titleField, title);
    }

    public void setDescription(String description) {
        set(descriptionField, description);
    }

    public String getErrorMessage(){
        return find(errorMessage).getText();
    }

    public TodoListPage clickAddButton() {
        click(addButton);
        return new TodoListPage();
    }

    public TodoListPage addNewTask(String title, String description) {
        setTitle(title);
        setDescription(description);
        return clickAddButton();
    }
}
