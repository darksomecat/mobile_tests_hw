package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static io.qameta.allure.Allure.step;
import static locators.WikipediaLocators.SEARCH;
import static locators.WikipediaLocators.SEARCH_INPUT;
import static locators.WikipediaLocators.SEARCH_RESULT;

@DisplayName("Поиск")
public class SearchTests extends TestBase {

    @BeforeEach
    void openSearchScreen() {
        skipOnboardingIfPresent();
    }

    @Test
    @DisplayName("Успешный поиск")
    void successfulSearchTest() {
        step("Ввести поисковый запрос", () -> {
            $(SEARCH).click();
            $(SEARCH_INPUT).sendKeys("Appium");
        });
        step("Проверить, что результаты найдены", () ->
                $$(SEARCH_RESULT).shouldHave(sizeGreaterThan(0)));
    }

    @Test
    @DisplayName("Поиск и открытие статьи Selenide")
    void searchSelenideTest() {
        step("Ввести поисковый запрос", () -> {
            $(SEARCH).click();
            $(SEARCH_INPUT).sendKeys("selenide");
        });
        step("Проверить, что статья найдена", () ->
                $(SEARCH_RESULT).shouldHave(text("Selenide")));
        step("Открыть статью", () ->
                $(SEARCH_RESULT).click());

        step("Проверить заголовок статьи", () ->
                $(androidUIAutomator("new UiSelector().text(\"Selenide\")")).shouldBe(visible));

    }
}