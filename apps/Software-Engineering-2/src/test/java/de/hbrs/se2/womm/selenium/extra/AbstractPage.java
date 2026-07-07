package de.hbrs.se2.womm.selenium.extra;

public abstract class AbstractPage {
    protected static final String BASE_URL = "http://localhost:8080/vaadin/";
    protected String websiteTitle;
    protected String websiteUrl;
    protected AbstractPage(String websiteTitle, String websiteUrl) {
        this.websiteTitle = websiteTitle;
        this.websiteUrl = websiteUrl;
    }
    public String getWebsiteTitle() {
        return websiteTitle;
    }
    public String getWebsiteUrl() {
        return BASE_URL + ""+websiteUrl;
    }

}
