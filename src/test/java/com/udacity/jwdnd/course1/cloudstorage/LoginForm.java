package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginForm {
    private WebDriver driver;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setInputUsername(String inputUsername) {
        this.inputUsername.clear();
        this.inputUsername.sendKeys(inputUsername);
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword.clear();
        this.inputPassword.sendKeys(inputPassword);
    }

    public void submit() {
        this.loginButton.click();
    }

    public static void login(String username, String password, WebDriverWait webDriverWait, LoginForm loginForm) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        loginForm.setInputUsername(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        loginForm.setInputPassword(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        loginForm.submit();
    }
}
