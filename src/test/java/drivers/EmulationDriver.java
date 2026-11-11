package drivers;

import config.Config;
import config.EmulationConfig;
import config.LocalConfig;
import io.appium.java_client.android.options.UiAutomator2Options;

public class EmulationDriver extends LocalDriver {

    @Override
    protected LocalConfig config() {
        return Config.emulation;
    }

    @Override
    protected void selectDevice(UiAutomator2Options options) {
        EmulationConfig emulation = Config.emulation;
        if (emulation.deviceUdid().isBlank() && !emulation.avd().isBlank()) {
            options.setAvd(emulation.avd());
        }
    }
}
