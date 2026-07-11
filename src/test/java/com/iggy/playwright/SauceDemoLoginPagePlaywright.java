package com.iggy.playwright;

import com.microsoft.playwright.Page;

public class SauceDemoLoginPagePlaywright {

    private final Page page;

    public SauceDemoLoginPagePlaywright(Page page) {
        this.page = page;
    }


    private static final String USERNAME = "[data-test='username']";
    private static final String PASSWORD = "[data-test='password']";
    private static final String LOGIN_BUTTON = "[data-test='login-button']";
    private static final String ERROR_MESSAGE = "[data-test='error']";


    public void enterUsername(String username) {
        page.fill(USERNAME, username);
    }

    public void enterPassword(String password) {
        page.fill(PASSWORD, password);
    }

    public void clickLoginButton() {
        page.click(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return page.locator(ERROR_MESSAGE).textContent();
    }

    public String getCurrentUrl() {
        return page.url();
    }
}