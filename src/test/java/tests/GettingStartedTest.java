package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static io.qameta.allure.Allure.step;
import static locators.WikipediaLocators.ACCEPT;
import static locators.WikipediaLocators.FORWARD;
import static locators.WikipediaLocators.REJECT;
import static locators.WikipediaLocators.SEARCH;

@DisplayName("Знакомство с приложением")
public class GettingStartedTest extends TestBase {

    @Test
    @DisplayName("Переход на четвёртый экран и отказ от анонимных данных")
    void gettingStartedTest() {
        step("Проверить первый экран: свободная энциклопедия", () -> {
            text("The Free Encyclopedia");
            text("in over 300 languages");
            $(FORWARD).click();
        });

        step("Проверить второй экран: лента Explore", () -> {
            text("New ways to explore");
            text("Explore feed");
            $(FORWARD).click();
        });

        step("Проверить третий экран: списки для чтения", () -> {
            text("Reading lists with sync");
            text("sync your reading lists");
            $(FORWARD).click();
        });

        step("На четвёртом экране нажать Reject", () -> {
            text("Send anonymous data");
            text("Data collected is anonymous");
            $(REJECT).shouldBe(visible).click();
        });

        step("После отказа открылся главный экран", () ->
                $(SEARCH).shouldBe(visible));
    }

    @Test
    @DisplayName("Прохождение четырёх экранов и согласие на анонимные данные")
    void rejectAnonymousData() {
        step("Проверить первый экран: свободная энциклопедия", () -> {
            text("The Free Encyclopedia");
            text("in over 300 languages");
            $(FORWARD).click();
        });

        step("Проверить второй экран: лента Explore", () -> {
            text("New ways to explore");
            text("Explore feed");
            $(FORWARD).click();
        });

        step("Проверить третий экран: списки для чтения", () -> {
            text("Reading lists with sync");
            text("sync your reading lists");
            $(FORWARD).click();
        });

        step("На четвёртом экране нажать Accept", () -> {
            text("Send anonymous data");
            text("Data collected is anonymous");
            $(ACCEPT).shouldBe(visible).click();
        });

        step("После согласия открылся главный экран", () ->
                $(SEARCH).shouldBe(visible));
    }

    private void text(String value) {
        $(androidUIAutomator("new UiSelector().textContains(\"" + value + "\")")).shouldBe(visible);
    }
}
