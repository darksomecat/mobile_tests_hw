package tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.qameta.allure.Allure.step;

public class IosTests extends TestBaseIos {
    @Test
    void searchSelenideTest() {
        step("Open text screen", () ->
                $(accessibilityId("Text")).click());
        step("Verify text output", () ->
                $(accessibilityId("Text Output")).shouldHave(text("Waiting for text input.")));
        step("Type search", () ->
                $(accessibilityId("Text Input")).sendKeys("Selenide"));
        step("Press search", () ->
                $(accessibilityId("Text Input")).pressEnter());
        step("Verify text output", () ->
                $(accessibilityId("Text Output")).shouldHave(text("Selenide")));
    }
}
