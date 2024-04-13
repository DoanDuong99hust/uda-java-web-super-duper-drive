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
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CredentialTest {

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

    @AfterEach
    void clear() {
        driver.close();
    }

    @Test
    void addCredential_inputUrlUsernamePassword_redirectToResultPageWithSuccessfulStatus() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        doMockSignUpAndLoginAndSwitchToCredentialTab("du", "do", "user1", "pass1", webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential")));
        WebElement addNewCredentialBtn = driver.findElement(By.id("add-new-credential"));
        addNewCredentialBtn.click();

        CredentialForm.save("http:/demo.com", "user","pass", webDriverWait, new CredentialForm(driver));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        Assertions.assertEquals("http://localhost:" + this.port + "/result", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    @Test
    void updateCredential_inputUrlUsernamePassword_redirectToResultPageWithSuccessfulStatus() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        doMockSignUpAndLoginAndSwitchToCredentialTab("du", "do", "user2", "pass2", webDriverWait);

        createCredential(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-2")));
        WebElement editNoteBtn = driver.findElement(By.id("edit-credential-2"));
        editNoteBtn.click();

        CredentialForm.save("http:/demo.com", "user update","pass update", webDriverWait, new CredentialForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        Assertions.assertEquals("http://localhost:" + this.port + "/result", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    @Test
    void deleteCredential() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        doMockSignUpAndLoginAndSwitchToCredentialTab("du", "do", "user3", "pass3", webDriverWait);

        createCredential(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential-2")));
        WebElement editNoteBtn = driver.findElement(By.id("delete-credential-2"));
        editNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        Assertions.assertEquals("http://localhost:" + this.port + "/result", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    private void createCredential(WebDriverWait webDriverWait) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential")));
        WebElement addNewCredentialBtn = driver.findElement(By.id("add-new-credential"));
        addNewCredentialBtn.click();

        CredentialForm.save("http:/demo.com", "user","pass", webDriverWait, new CredentialForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("redirect-success")));
        WebElement backToHomeBtn = driver.findElement(By.id("redirect-success"));
        backToHomeBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement noteTabBtn = driver.findElement(By.id("nav-credentials-tab"));
        noteTabBtn.click();
    }

    private void doMockSignUpAndLoginAndSwitchToCredentialTab(String firstName, String lastname,
                                                        String username, String password,
                                                        WebDriverWait webDriverWait) {
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
        LoginForm.login(username, password, webDriverWait, new LoginForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement noteTabBtn = driver.findElement(By.id("nav-credentials-tab"));
        noteTabBtn.click();
    }
}
