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
public class NoteTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
    }

    // TODO: Sửa test về kiểu cũ
    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    void clear() {
        driver.close();
    }

    @Test
    void addNote_inputTitleAndDescription_redirectToResultPageWithSuccessfulStatus() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        doMockSignUpAndLoginAndSwitchToNoteTab("du", "do", "user1", "pass1", webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-note")));
        WebElement addNewNoteBtn = driver.findElement(By.id("add-new-note"));
        addNewNoteBtn.click();

        NoteForm.save("demo title", "note description", webDriverWait, new NoteForm(driver));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        Assertions.assertEquals("http://localhost:" + this.port + "/result", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    @Test
    void updateNote_changeInputTitleAndDescription_redirectToResultPageWithSuccessfulStatus() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 1);
        doMockSignUpAndLoginAndSwitchToNoteTab("du", "do", "user2", "pass2", webDriverWait);

        createTwoNote(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note-1")));
        WebElement editNoteBtn = driver.findElement(By.id("edit-note-1"));
        editNoteBtn.click();

        NoteForm.save("demo title 1 update", "note description 1 update", webDriverWait, new NoteForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        Assertions.assertEquals("http://localhost:" + this.port + "/result", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    @Test
    void deleteNote() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        doMockSignUpAndLoginAndSwitchToNoteTab("du", "do", "user3", "pass3", webDriverWait);

        createTwoNote(webDriverWait);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-2")));
        WebElement editNoteBtn = driver.findElement(By.id("delete-note-2"));
        editNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        Assertions.assertEquals("http://localhost:" + this.port + "/result", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    private void createTwoNote(WebDriverWait webDriverWait) {
        for (int i = 1; i <= 2; i++) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-note")));
            WebElement addNewNoteBtn = driver.findElement(By.id("add-new-note"));
            addNewNoteBtn.click();

            NoteForm.save("demo title " + i, "note description" + i, webDriverWait, new NoteForm(driver));

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("redirect-success")));
            WebElement backToHomeBtn = driver.findElement(By.id("redirect-success"));
            backToHomeBtn.click();

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
            WebElement noteTabBtn = driver.findElement(By.id("nav-notes-tab"));
            noteTabBtn.click();
        }
    }

    private void doMockSignUpAndLoginAndSwitchToNoteTab(String firstName, String lastname,
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

        LoginForm.login(username, password, webDriverWait, new LoginForm(driver));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteTabBtn = driver.findElement(By.id("nav-notes-tab"));
        noteTabBtn.click();
    }
}
