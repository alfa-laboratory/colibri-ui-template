package ru.colibri.ui.template.settings.ios;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.colibri.ui.core.settings.DriversSettings;
import ru.colibri.ui.settings.ios.base.BaseIOSDriverConfigurator;


/**
 * Класс настройки драйвера для iOS
 */
@Component
@Qualifier("ios")
public class IOSDriverConfigurator extends BaseIOSDriverConfigurator {

    protected void additionalCapabilities(DriversSettings driversSettings, DesiredCapabilities capabilities) {
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.1.2"); //в случае работы с симулятором версию ос для симулятора возьмет отсюда
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, driversSettings.getWdaLocalPort());
        capabilities.setCapability("autoLaunch", true);
        capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        capabilities.setCapability(IOSMobileCapabilityType.CONNECT_HARDWARE_KEYBOARD, true);
        capabilities.setCapability(MobileCapabilityType.LANGUAGE, "RU");
        capabilities.setCapability(MobileCapabilityType.LOCALE, "ru_RU");
        capabilities.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);
    }

}
