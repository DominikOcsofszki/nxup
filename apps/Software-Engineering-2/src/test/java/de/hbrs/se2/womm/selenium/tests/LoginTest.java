package de.hbrs.se2.womm.selenium.tests;

import de.hbrs.se2.womm.selenium.extra.AbstractPrepareTestSelenium;
import de.hbrs.se2.womm.selenium.extra.LOCATORS;
import de.hbrs.se2.womm.selenium.extra.LogoutPage;
import de.hbrs.se2.womm.selenium.pages.LoginPage;

public class LoginTest extends AbstractPrepareTestSelenium {
    LoginPage loginPage;
    LogoutPage logoutPages;

    @Override
    protected void setupPageBeforeEach() {
        loginPage = new LoginPage(driver, wait);
//        this.logoutPages = new LogoutPages(driver);
        loginPage.goToWebsiteAndWaitUntilTitlePresent();
    }

//    String name = "selenium";
//    String surname = "test";
//    String username = "seleniumtest";
//    String email = "seleniumtest@web.de";
//    String password = "seleniumtest";
//    String confirmPassword = "seleniumtest";
//    String bday = "01.01.2000";
//    String location = "world";
//    registerPage.register(name, surname, username, email, password, confirmPassword, bday, location);

//    @Test
    void testLogin() {
        loginPage.login("seleniumtest", "seleniumtest");
        assert(driver.findElement(LOCATORS.LOGOUT_BUTTON).isDisplayed());
        if(driver.findElement(LOCATORS.LOGOUT_BUTTON).isDisplayed()){
            driver.findElement(LOCATORS.LOGOUT_BUTTON).click();
        }
    }



}
