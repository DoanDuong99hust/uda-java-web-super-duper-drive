package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginLogoutTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @Test
    void login_signedInUsernameAndPassword_loginSuccessfully() throws InterruptedException {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        doMockSignUpAndLogin("du", "do", "ad", "pass" , webDriverWait);
        LoginForm.login("ad", "pass", webDriverWait, new LoginForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("home-page")));
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
    }

    @Test
    void login_unsignedInAccount_loginUnsuccessfully() {
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        LoginForm.login("ad", "pass", webDriverWait, new LoginForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("invalid-account-error")));
        Assertions.assertTrue(driver.findElement(By.id("invalid-account-error")).getText().contains("Invalid username or password"));
    }

    @Test
    void pressLogoutButton_homePageWithLogoutButton_redirectToLoginPageWithLogoutStatus() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        doMockSignUpAndLogin("eric", "smith", "admin1", "password" , webDriverWait);
        LoginForm.login("admin1", "password", webDriverWait, new LoginForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-status")));
        Assertions.assertEquals("http://localhost:" + this.port + "/login?logout=true", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("logout-status")).getText().contains("You have been logged out"));
    }

    private void doMockSignUpAndLogin(String firstName, String lastname, String username, String password, WebDriverWait webDriverWait) {
        // Switch to sign-up page
        driver.get("http://localhost:" + this.port + "/login");
        WebElement switchToSignUpLink = driver.findElement(By.id("switch-to-sign-up"));
        switchToSignUpLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        SignUpForm.signUp(firstName, lastname, username, password, webDriverWait, new SignUpForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));

        WebElement switchToLogin = driver.findElement(By.id("switch-to-login"));
        switchToLogin.click();
    }
}
