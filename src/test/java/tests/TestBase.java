package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserstackDrivers;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeAll
    public static void beforeAll() {
        Configuration.browser = BrowserstackDrivers.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;

    }

    @BeforeEach
    public void initTestData() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }
    @AfterEach
    void addAttachments() {
        if (!Selenide.webdriver().driver().hasWebDriverStarted()) {
            return;
        }

    }
}
