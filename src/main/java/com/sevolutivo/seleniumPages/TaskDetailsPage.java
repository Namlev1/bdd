package com.sevolutivo.seleniumPages;

import org.openqa.selenium.By;

public class TaskDetailsPage extends BasePage {
    private By titleField = By.name("title");
    private By descriptionField = By.name("description");
    private By saveChangesButton = By.className("btn");
    private By errorMessage = By.xpath("/html/body/div/div/p");

    public void setTitleField(String title) {
        set(titleField, title);
    }

    public void setDescriptionField(String description) {
        set(descriptionField, description);
    }

    public TodoListPage clickSaveChangesButton() {
        click(saveChangesButton);
        return new TodoListPage();
    }

    public String getErrorMessage(){
        return find(errorMessage).getText();
    }

    public String getTitleFieldValue() {
        return find(titleField).getAttribute("value");
    }

    public String getDescriptionFieldValue() {
        return find(descriptionField).getAttribute("value");
    }

}
