package ru.colibri.template.settings.ios;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.alfabank.autotest.core.settings.DriversSettings;
import ru.alfabank.autotest.settings.ios.base.BaseIOSDriverConfigurator;


/**
 * Класс настройки драйвера для iOS
 */
@Component
@Qualifier("ios")
public class IOSDriverConfigurator extends BaseIOSDriverConfigurator {

    protected void additionalCapabilities(DriversSettings driversSettings, DesiredCapabilities capabilities) {
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.2");
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, driversSettings.getWdaLocalPort());
        capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        capabilities.setCapability(MobileCapabilityType.LANGUAGE, "RU");
        capabilities.setCapability("autoLaunch", true);
    }

}
