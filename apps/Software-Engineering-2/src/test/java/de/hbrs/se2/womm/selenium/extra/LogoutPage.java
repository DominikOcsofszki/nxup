package de.hbrs.se2.womm.selenium.extra;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutPage {
    private WebDriver driver;

    public LogoutPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
    }

    WebElement logoutButton;

    public void logout() {
        logoutButton = driver.findElement(By.id("womm-logout-button")); //ToDo chnge button in vaadin
        logoutButton.click();
    }
}
