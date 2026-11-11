package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.DriverFactory;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static locators.WikipediaLocators.ACCEPT;
import static locators.WikipediaLocators.FORWARD;
import static locators.WikipediaLocators.SEARCH;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.browser = DriverFactory.androidDriver().getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;
        Configuration.pageLoadTimeout = -1;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
        dismissPageSizeWarning();
    }

    private void dismissPageSizeWarning() {
        By dontShowAgain = androidUIAutomator("new UiSelector().text(\"Don't Show Again\")");
        long deadline = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < deadline) {
            if (!getWebDriver().findElements(dontShowAgain).isEmpty()) {
                $(dontShowAgain).click();
                return;
            }
            Selenide.sleep(300);
        }
    }

    protected void skipOnboardingIfPresent() {
        By forward = FORWARD;
        By search = SEARCH;
        Wait().withTimeout(Duration.ofSeconds(20))
                .withMessage("Не открылся ни онбординг, ни поиск Wikipedia")
                .until(driver -> !driver.findElements(forward).isEmpty()
                        || !driver.findElements(search).isEmpty());

        if (getWebDriver().findElements(forward).isEmpty()) {
            return;
        }

        for (int screen = 0; screen < 3; screen++) {
            $(forward).click();
        }
        $(ACCEPT).click();
        $(search).shouldBe(visible);
    }

    @AfterEach
    void addAttachments() {
        String sessionId = null;
        try {
            sessionId = Selenide.sessionId().toString();
            Attach.safely("Screenshot", () -> Attach.screenshotAs("Last screenshot"));
            Attach.safely("Page source", Attach::pageSource);
        } finally {
            closeWebDriver();
        }

        if (sessionId != null && DriverFactory.isBrowserstack()) {
            String id = sessionId;
            Attach.safely("Video", () -> Attach.addVideo(id));
            Attach.safely("Text logs", () -> Attach.textLogs(id));
            Attach.safely("Appium logs", () -> Attach.appiumLogs(id));
            Attach.safely("Device logs", () -> Attach.deviceLogs(id));
        }
    }
}
