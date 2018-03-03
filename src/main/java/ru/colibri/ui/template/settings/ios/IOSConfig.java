package ru.colibri.ui.template.settings.ios;

import io.appium.java_client.ios.IOSDriver;
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
 * Центральный класс настройки запуска проекта для  iOS
 */
@Configuration
@ComponentScan(basePackages = {"ru.colibri.ui.settings.ios", "ru.colibri.ui.steps.ios",
        "ru.colibri.ui.template.settings.ios", "ru.colibri.ui.template.steps.ios"})
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
        return (IOSDriver) driverConfigurator.createDriver(getDriversSettings(), getAppSettings());
    }

    @Bean
    @Qualifier("ios")
    public DriversSettings getDriversSettings() {
        return iSettingsLoader.loadDriverSettings(System.getenv().get(PLATFORM));
    }

    @Bean
    @Qualifier("ios")
    public AppSettings getAppSettings() {
        return iSettingsLoader.loadAppSettings(System.getenv().get(USER));
    }

    @Bean
    @Qualifier("ios")
    public TestSettings getTestSettings() {
        return iSettingsLoader.loadTestSettings(System.getenv().get(TEST_TYPE));
    }


}
