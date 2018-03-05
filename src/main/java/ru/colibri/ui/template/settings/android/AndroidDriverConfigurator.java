package ru.colibri.ui.template.settings.android;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.extern.java.Log;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.colibri.ui.core.settings.AppSettings;
import ru.colibri.ui.settings.android.base.BaseAndroidDriverConfigurator;

/**
 * Класс настройки драйвера для андроид
 */
@Log
@Component
@Qualifier("android")
public class AndroidDriverConfigurator extends BaseAndroidDriverConfigurator {

    protected void additionalAndroidCapabilities(AppSettings appSettings, DesiredCapabilities capabilities) {
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
        capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, appSettings.getPackageName() + appSettings.getStartPageId());
        capabilities.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2"); //спорная настройка, но для андроидов 7+ ускоряет прогон
    }
}
