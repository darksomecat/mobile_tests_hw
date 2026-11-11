package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.LocalConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public abstract class LocalDriver implements WebDriverProvider {

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        return new AndroidDriver(appiumUrl(), options());
    }

    protected abstract LocalConfig config();

    protected void selectDevice(UiAutomator2Options options) {
    }

    private UiAutomator2Options options() {
        LocalConfig config = config();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName(config.deviceName());
        options.setApp(appPath(config).toString());
        options.setAppPackage(config.appPackage());
        options.setAppActivity(config.appActivity());
        options.setAppWaitActivity("*");
        options.setAutoGrantPermissions(true);
        options.setNoReset(false);
        options.setFullReset(true);
        options.setNewCommandTimeout(Duration.ofSeconds(180));

        if (!config.platformVersion().isBlank()) {
            options.setPlatformVersion(config.platformVersion());
        }
        if (!config.deviceUdid().isBlank()) {
            options.setUdid(config.deviceUdid());
        }
        if (!config.language().isBlank()) {
            options.setLanguage(config.language());
            options.setLocale(config.locale());
        }
        selectDevice(options);
        return options;
    }

    private URL appiumUrl() {
        try {
            return URI.create(config().appiumUrl()).toURL();
        } catch (Exception error) {
            throw new IllegalArgumentException("Некорректный appium.url: " + config().appiumUrl(), error);
        }
    }

    private Path appPath(LocalConfig config) {
        Path appPath = Path.of(config.appPath()).toAbsolutePath();
        if (Files.exists(appPath)) {
            return appPath;
        }
        if (config.appUrl() == null || config.appUrl().isBlank()) {
            throw new IllegalStateException("APK не найден: " + appPath);
        }

        try {
            Files.createDirectories(appPath.getParent());
            URLConnection connection = URI.create(config.appUrl()).toURL().openConnection();
            connection.setRequestProperty("User-Agent", "mobile-tests-hw");
            if (connection instanceof HttpURLConnection http && http.getResponseCode() >= 400) {
                throw new IOException("HTTP " + http.getResponseCode());
            }
            try (InputStream input = connection.getInputStream()) {
                Files.copy(input, appPath);
            }
        } catch (IOException error) {
            throw new IllegalStateException("Не удалось скачать приложение: " + config.appUrl(), error);
        }
        return appPath;
    }
}
