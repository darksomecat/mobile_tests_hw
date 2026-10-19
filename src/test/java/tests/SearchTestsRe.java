package tests;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;

public class SearchTestsRe extends  TestBase {
    @Test
    void successfulSearchTest(){

//        WebElement searchElement = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
//                ExpectedConditions.elementToBeClickable(
//                        accessibilityId("Search Wikipedia")));
//        searchElement.click();
        step("Type search", () -> {

        $(accessibilityId("Search Wikipedia")).click();

//        WebElement insertTextElement = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
//                ExpectedConditions.elementToBeClickable(
//                        id("org.wikipedia.alpha:id/search_src_text")));
//        insertTextElement.sendKeys("Appium");
//
        $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Appium");
//
//        Thread.sleep(5000);
//        List<WebElement> allProductsName = driver.findElements(AppiumBy.className(
//                "android.widget.TextView"));
//        assert (allProductsName.size() > 0);
        });
        step("Verify content found", () ->
        $$(className("android.widget.TextView")).shouldHave(sizeGreaterThan(0)));

        // Invoke driver.quit() after the test is done to indicate that the test is completed.
//        driver.quit();

    }
}
