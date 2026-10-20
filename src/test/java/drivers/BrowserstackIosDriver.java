package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.Config;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserstackIosDriver implements WebDriverProvider {

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserstack.user", Config.browserStack.user());
        caps.setCapability("browserstack.key", Config.browserStack.key());

        caps.setCapability("app", Config.browserStack.iosApp());

        caps.setCapability("device", Config.browserStack.iosDeviceName());
        caps.setCapability("os_version", Config.browserStack.iosOsVersion());

        caps.setCapability("project", Config.browserStack.projectName());
        caps.setCapability("build", Config.browserStack.buildName());
        caps.setCapability("name", Config.browserStack.iosSessionName());

        try {
            return new RemoteWebDriver(
                    new URL(Config.browserStack.hubUrl()), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
