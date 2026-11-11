package locators;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public final class WikipediaLocators {

    private static final String APP_PACKAGE = "org.wikipedia.alpha";

    public static final By FORWARD = id("fragment_onboarding_forward_button");
    public static final By ACCEPT = id("acceptButton");
    public static final By REJECT = id("rejectButton");
    public static final By SEARCH = AppiumBy.accessibilityId("Search Wikipedia");
    public static final By SEARCH_INPUT = id("search_src_text");
    public static final By SEARCH_RESULT = id("page_list_item_title");

    private WikipediaLocators() {
    }

    private static By id(String name) {
        return AppiumBy.id(APP_PACKAGE + ":id/" + name);
    }
}
