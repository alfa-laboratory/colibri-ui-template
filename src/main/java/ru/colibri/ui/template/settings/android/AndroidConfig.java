package ru.colibri.ui.template.settings.android;

import io.appium.java_client.android.AndroidDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.colibri.ui.core.settings.AppSettings;
import ru.colibri.ui.core.settings.DriversSettings;
import ru.colibri.ui.core.settings.TestSettings;
import ru.colibri.ui.settings.loaders.ISettingsLoader;

import static ru.colibri.ui.core.names.ColibriStartFlags.*;

/**
 * Центральный класс настройки запуска проекта для Андроид
 */
@Configuration
@ComponentScan(basePackages = {"ru.colibri.ui.settings.android", "ru.colibri.ui.steps.android",
        "ru.colibri.ui.template.settings.android", "ru.colibri.ui.template.steps.android"})
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
        return (AndroidDriver) driverConfigurator.createDriver(getDriversSettings(), getAppSettings());
    }

    @Bean
    @Qualifier("android")
    public DriversSettings getDriversSettings() {
        return iSettingsLoader.loadDriverSettings(System.getenv().get(PLATFORM));
    }

    @Bean
    @Qualifier("android")
    public AppSettings getAppSettings() {
        return iSettingsLoader.loadAppSettings(System.getenv().get(USER));
    }

    @Bean
    @Qualifier("android")
    public TestSettings getTestSettings() {
        return iSettingsLoader.loadTestSettings(System.getenv().get(TEST_TYPE));
    }
}
