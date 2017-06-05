package ru.colibri.ui.template;

import io.appium.java_client.AppiumDriver;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.colibri.ui.core.names.ColibriStartFlags;
import ru.colibri.ui.core.pages.IPageProvider;
import ru.colibri.ui.core.settings.DriversSettings;
import ru.colibri.ui.settings.general.GeneralConfig;
import ru.colibri.ui.template.settings.ios.IOSConfig;


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
        System.setProperty(ColibriStartFlags.USER, "1907306");
        System.setProperty(ColibriStartFlags.PLATFORM, "ios10");
        System.setProperty(ColibriStartFlags.TEST_TYPE, "smoke");
        System.setProperty(ColibriStartFlags.BUILD_VERSION, "8.3_7426");
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