package ru.colibri.template.settings.ios;

import io.appium.java_client.ios.IOSDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.alfabank.autotest.core.settings.AppSettings;
import ru.alfabank.autotest.core.settings.DriversSettings;
import ru.alfabank.autotest.core.settings.TestSettings;
import ru.alfabank.autotest.settings.loaders.ISettingsLoader;

/**
 * ентральный класс настройки запуска проекта для  iOS
 */
@Configuration
@ComponentScan(basePackages = {"ru.alfabank.autotest.settings.ios", "ru.alfabank.autotest.steps.ios"})
public class IOSConfig {


    @Autowired
    @Qualifier("ios")
    private IOSDriverConfigurator driverConfigurator;

    @Autowired
    @Qualifier("ios")
    private ISettingsLoader iSettingsLoader;


    @Bean
    @Qualifier("ios")
    public IOSDriver getIOSDriver() {
        IOSDriver driver = (IOSDriver) driverConfigurator.createDriver(getDriversSettings(), getAppSettings());
        return driver;
    }

    @Bean
    @Qualifier("ios")
    public DriversSettings getDriversSettings() {
        return iSettingsLoader.loadDriverSettings(System.getProperty("platform"));
    }

    @Bean
    @Qualifier("ios")
    public AppSettings getAppSettings() {
        return iSettingsLoader.loadAppSettings(System.getProperty("user"));
    }

    @Bean
    @Qualifier("ios")
    public TestSettings getTestSettings() {
        return iSettingsLoader.loadTestSettings(System.getProperty("testType"));
    }


}
