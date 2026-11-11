package drivers;

import config.Config;
import config.LocalConfig;
import io.appium.java_client.android.options.UiAutomator2Options;

public class RealDeviceDriver extends LocalDriver {

    @Override
    protected LocalConfig config() {
        return Config.realDevice;
    }

    @Override
    protected void selectDevice(UiAutomator2Options options) {
        if (Config.realDevice.deviceUdid().isBlank()) {
            throw new IllegalArgumentException(
                    "Для стенда real (RealDevice) укажите -Ddevice.udid=<серийный номер из adb devices>");
        }
    }
}
