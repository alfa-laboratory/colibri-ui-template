package ru.colibri.template;

import io.appium.java_client.AppiumDriver;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alfabank.autotest.core.pages.IPageProvider;
import ru.alfabank.autotest.core.settings.DriversSettings;
import ru.alfabank.autotest.settings.general.GeneralConfig;
import ru.colibri.template.settings.ios.IOSConfig;


/**
 * Created by dolinskiyaleksandr on 07.11.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GeneralConfig.class, IOSConfig.class})
public class IOSStories extends AbstractStories {

    @Autowired
    @Qualifier("ios")
    private IPageProvider pageProvider;

    @Autowired
    @Qualifier("ios")
    private DriversSettings driversSettings;

    @Autowired
    @Qualifier("ios")
    private AppiumDriver driver;

    public IOSStories() {
        super();
//        System.setProperty("amUser", "1907306");
//        System.setProperty("platform", "ios10");
//        System.setProperty("testType", "smoke");
//        System.setProperty("buildVersion", "8.3_7426");
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