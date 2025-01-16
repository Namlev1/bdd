package com.sevolutivo.demo.selenium;

import com.sevolutivo.seleniumPages.BasePage;
import com.sevolutivo.seleniumPages.TodoListPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;
    protected TodoListPage todoListPage;
    private String url = "http://localhost:8080";

    @BeforeClass
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(url);

        basePage = new BasePage();
        basePage.setDriver(driver);

        todoListPage = new TodoListPage();
    }

    @BeforeMethod
    public void openMainPage(){
        driver.get(url);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
