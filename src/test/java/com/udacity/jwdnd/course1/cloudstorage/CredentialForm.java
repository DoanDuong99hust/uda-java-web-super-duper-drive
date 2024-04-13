package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialForm {

    private WebDriver driver;

    @FindBy(id = "credentials-url")
    private WebElement credentialsUrl;

    @FindBy(id = "credentials-username")
    private WebElement credentialsUsername;

    @FindBy(id = "credentials-password")
    private WebElement credentialsPassword;

    @FindBy(id = "save-credentials")
    private WebElement saveCredentials;

    public CredentialForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setCredentialsUrl(String url) {
        this.credentialsUrl.clear();
        this.credentialsUrl.sendKeys(url);
    }

    public void setCredentialsUsername(String username) {
        this.credentialsUsername.clear();
        this.credentialsUsername.sendKeys(username);
    }

    public void setCredentialsPassword(String password) {
        this.credentialsPassword.clear();
        this.credentialsPassword.sendKeys(password);
    }

    public void saveChange() {
        this.saveCredentials.click();
    }

    public static void save(String url, String username, String password, WebDriverWait webDriverWait, CredentialForm credentialForm) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-url")));
        credentialForm.setCredentialsUrl(url);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-username")));
        credentialForm.setCredentialsUsername(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-password")));
        credentialForm.setCredentialsPassword(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credentials")));
        credentialForm.saveChange();
    }
}
