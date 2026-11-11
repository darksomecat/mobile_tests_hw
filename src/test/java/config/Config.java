package config;

import org.aeonbits.owner.ConfigFactory;

public class Config {

    public static final BrowserStackConfig browserStack =
            ConfigFactory.create(BrowserStackConfig.class, System.getProperties());
}
