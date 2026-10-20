package tests;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserstackAndroidDrivers;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        Configuration.browser = BrowserstackAndroidDrivers.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void addAttachments() {
        String sessionId = Selenide.sessionId().toString();
        System.out.println(sessionId);

        Attach.safely("Screenshot", () -> Attach.screenshotAs("Last screenshot"));
        Attach.safely("Page source", Attach::pageSource);
        closeWebDriver();

        Attach.safely("Video", () -> Attach.addVideo(sessionId));
        Attach.safely("Text logs", () -> Attach.textLogs(sessionId));
        Attach.safely("Appium logs", () -> Attach.appiumLogs(sessionId));
        Attach.safely("Device logs", () -> Attach.deviceLogs(sessionId));
    }

    }

