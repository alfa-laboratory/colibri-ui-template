package ru.colibri.template;

import io.appium.java_client.AppiumDriver;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alfabank.autotest.core.pages.IPageProvider;
import ru.alfabank.autotest.core.settings.DriversSettings;
import ru.alfabank.autotest.settings.general.GeneralConfig;
import ru.colibri.template.settings.android.AndroidConfig;

@Log
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GeneralConfig.class, AndroidConfig.class})
public class AndroidStories extends AbstractStories {

    @Autowired
    @Qualifier("android")
    private IPageProvider pageProvider;

    @Autowired
    @Qualifier("android")
    private DriversSettings driversSettings;

    @Autowired
    @Qualifier("android")
    private AppiumDriver driver;

    public AndroidStories() {
        super();
        System.setProperty("amUser", "6016680");
        System.setProperty("platform", "android6");
        System.setProperty("testType", "smoke");
        System.setProperty("buildVersion", "8.4.0.7");
    }

    @Before
    public void before() {
        initPageProvider();
    }

    @Override
    protected IPageProvider getPageProvider() {
        return pageProvider;
    }

    @Override
    protected DriversSettings getDriversSettings() {
        return driversSettings;
    }

    @Override
    protected AppiumDriver getDriver() {
        return driver;
    }


}