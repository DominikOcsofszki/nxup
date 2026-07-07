package de.hbrs.se2.womm.selenium.extra;
/***
 * Testklasse Selenium
 */

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public abstract class AbstractPrepareTestSelenium {
    protected WebDriver driver;
    protected Logger logger = Logger.getLogger("");
    protected WebDriverWait wait;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    protected abstract void setupPageBeforeEach();


    @BeforeEach
    void setupTest() {
        this.driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        setupPageBeforeEach();
    }

    @AfterEach
    void teardown() {
//        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
