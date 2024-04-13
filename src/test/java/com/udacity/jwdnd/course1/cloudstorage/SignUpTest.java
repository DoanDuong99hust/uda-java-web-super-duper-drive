package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpTest {

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
        // Switch to sign-up page
        driver.get("http://localhost:" + this.port + "/login");
        WebElement switchToSignUpLink = driver.findElement(By.id("switch-to-sign-up"));
        switchToSignUpLink.click();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    void signUp_withUsernameNotExists_signUpSuccessfully() {
        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        SignUpForm.signUp("du", "do", "ad", "pass", webDriverWait, new SignUpForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }

    @Test
    void signUp_withUsernameExists_signUpUnsuccessfully() {
        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        SignUpForm signUpForm = new SignUpForm(driver);
        SignUpForm.signUp("du", "do", "ad", "pass", webDriverWait, signUpForm);

        signUpForm.setFirstName("");
        signUpForm.setLastName("");
        signUpForm.setUsername("");
        SignUpForm.signUp("eric", "do", "ad", "password", webDriverWait, signUpForm);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fail-msg")));
        Assertions.assertTrue(driver.findElement(By.id("fail-msg")).getText().contains("User already existed!"));
    }
}
