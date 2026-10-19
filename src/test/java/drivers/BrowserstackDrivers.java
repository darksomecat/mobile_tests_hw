package drivers;

import com.codeborne.selenide.WebDriverProvider;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BrowserstackDrivers implements WebDriverProvider {

    @Override
    public WebDriver createDriver(@NonNull Capabilities capabilities) {
        MutableCapabilities caps = new MutableCapabilities();

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", "annamonik_G31eEQ");
        bstackOptions.put("accessKey", "ny7qSv9fQozCkNjm6m5U");
        bstackOptions.put("projectName", "First Java Project");
        bstackOptions.put("buildName", "browserstack-build-1");
        bstackOptions.put("sessionName", "first_test");
        caps.setCapability("bstack:options", bstackOptions);

        caps.setCapability("platformName", "android");
        caps.setCapability("appium:deviceName", "Google Pixel 6");
        caps.setCapability("appium:platformVersion", "12.0");
        caps.setCapability("appium:app", "bs://c700ce60cf13ae8ed97705a55b8e022f13c5827c");

        // Initialise the remote Webdriver using BrowserStack remote URL
        // and desired capabilities defined above
        try {
            return new RemoteWebDriver(
                    new URL("https://hub.browserstack.com/wd/hub"), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}