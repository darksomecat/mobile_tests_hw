package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.Config;

import java.util.Locale;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static Class<? extends WebDriverProvider> androidDriver() {
        String deviceHost = Config.run.deviceHost().toLowerCase(Locale.ROOT);
        return switch (deviceHost) {
            case "browserstack" -> BrowserstackAndroidDrivers.class;
            case "emulation" -> EmulationDriver.class;
            case "real" -> RealDeviceDriver.class;
            default -> throw new IllegalArgumentException(
                    "Неизвестный deviceHost: " + Config.run.deviceHost()
                            + ". Допустимые значения: browserstack, emulation, real");
        };
    }

    public static boolean isBrowserstack() {
        return "browserstack".equalsIgnoreCase(Config.run.deviceHost());
    }
}
