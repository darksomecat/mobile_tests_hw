package config;

import org.aeonbits.owner.ConfigFactory;

public final class Config {

    public static final RunConfig run =
            ConfigFactory.create(RunConfig.class, System.getProperties());

    public static final BrowserStackConfig browserStack =
            ConfigFactory.create(BrowserStackConfig.class, System.getProperties());

    public static final EmulationConfig emulation =
            ConfigFactory.create(EmulationConfig.class, System.getProperties());

    public static final RealDeviceConfig realDevice =
            ConfigFactory.create(RealDeviceConfig.class, System.getProperties());

    private Config() {
    }
}
