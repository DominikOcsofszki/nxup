package de.hbrs.se2.womm.selenium.pages;

import de.hbrs.se2.womm.selenium.extra.AbstractPage;
import de.hbrs.se2.womm.selenium.extra.LOCATORS;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractPage {
    private WebDriver driver;
    private WebDriverWait wait;
    WebElement usernameField;
    WebElement passwordField;
    WebElement loginButton;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super("LoginView", ROUTING.ALL.LoginView);
        this.driver = driver;
        this.wait = wait;
    }

    public void goToWebsiteAndWaitUntilTitlePresent() {
        driver.get(getWebsiteUrl());
        wait.until(webDriver -> webDriver.getTitle().equals(getWebsiteTitle()));
    }
    private void setUp() {
        usernameField = driver.findElement(LOCATORS.TEXT_FIELD_BUILDER_1);
        passwordField = driver.findElement(LOCATORS.PASSWORDFIELD_BUILDER_1);
        loginButton = driver.findElement(LOCATORS.BUTTON_BUILDER_1);
    }


    public void login(String username, String password) {
        setUp();
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        wait.until(webDriver -> !(webDriver.getTitle().equals(getWebsiteTitle())));
    }
}
