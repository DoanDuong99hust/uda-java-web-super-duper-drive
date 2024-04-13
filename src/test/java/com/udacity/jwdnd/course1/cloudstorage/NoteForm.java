package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NoteForm {

    private WebDriver driver;

    @FindBy(id = "notes-title")
    private WebElement noteTitle;

    @FindBy(id = "notes-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note")
    private WebElement saveChangesBtn;

    @FindBy(id = "close-note")
    private WebElement closeNoteBtn;

    public NoteForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
    }

    public void saveChange() {
        this.saveChangesBtn.click();
    }

    public static void save(String noteTitle, String noteDescription, WebDriverWait webDriverWait, NoteForm noteForm) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-title")));
        noteForm.setNoteTitle(noteTitle);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-description")));
        noteForm.setNoteDescription(noteDescription);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note")));
        noteForm.saveChange();
    }
}
