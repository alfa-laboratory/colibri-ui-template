package ru.colibri.template.settings.android;

import io.appium.java_client.android.AndroidDriver;
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
 * Центральный класс настройки запуска проекта для Андроид
 */
@Configuration
@ComponentScan(basePackages = {"ru.alfabank.autotest.settings.android", "ru.alfabank.autotest.steps.android"})
public class AndroidConfig {

    @Autowired
    @Qualifier("android")
    private AndroidDriverConfigurator driverConfigurator;

    @Autowired
    @Qualifier("android")
    private ISettingsLoader iSettingsLoader;


    @Bean
    @Qualifier("android")
    public AndroidDriver getAndroidDriver() {
        AndroidDriver driver = (AndroidDriver) driverConfigurator.createDriver(getDriversSettings(), getAppSettings());
        return driver;
    }

    @Bean
    @Qualifier("android")
    public DriversSettings getDriversSettings() {
        return iSettingsLoader.loadDriverSettings(System.getProperty("platform"));
    }

    @Bean
    @Qualifier("android")
    public AppSettings getAppSettings() {
        return iSettingsLoader.loadAppSettings(System.getProperty("user"));
    }

    @Bean
    @Qualifier("android")
    public TestSettings getTestSettings() {
        return iSettingsLoader.loadTestSettings(System.getProperty("testType"));
    }
}
