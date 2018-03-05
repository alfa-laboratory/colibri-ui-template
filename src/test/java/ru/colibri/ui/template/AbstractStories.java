package ru.colibri.ui.template;

import io.appium.java_client.AppiumDriver;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.colibri.ui.core.pages.IPage;
import ru.colibri.ui.core.pages.IPageProvider;
import ru.colibri.ui.core.reporters.AllureFormat;
import ru.colibri.ui.core.reporters.ReportPortalFormat;
import ru.colibri.ui.core.settings.DriversSettings;
import ru.colibri.ui.core.settings.IJBConfigurator;
import ru.colibri.ui.core.settings.TestSettings;
import ru.colibri.ui.core.utils.FileUtils;
import ru.colibri.ui.settings.general.PagesLoader;
import ru.colibri.ui.template.providers.NodeProvider;

import java.io.File;
import java.util.List;

import static ru.colibri.ui.core.names.ColibriStartFlags.PLATFORM;

public abstract class AbstractStories extends JUnitStories {


    protected Configuration configuration;
    @Autowired
    protected IJBConfigurator configurator;

    @Autowired
    private PagesLoader pagesLoader;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private TestSettings testSettings;
    @Autowired
    private NodeProvider nodeProvider;
    @Autowired
    private ReportPortalFormat reportPortalFormat;
    @Autowired
    private AllureFormat allureFormat;

    protected void initPageProvider() {
        File pagesDirectory = fileUtils.getFileByPath(getDriversSettings().getPagesPath());
        List<IPage> pages = pagesLoader.loadPagesFromDirectory(pagesDirectory);
        getPageProvider().addPagesObject(pages);
    }

    @After
    public void after() {
        getDriver().quit();
        nodeProvider.stopNode();
    }

    @Override
    public Configuration configuration() {
        if (configuration == null) {
            prepareConfig();
            configuredEmbedder().useMetaFilters(testSettings.getFlagsMetaFilters());
            configurator.configure( configuredEmbedder().embedderControls(), getDriversSettings());
        }
        return configuration;
    }

    private void prepareConfig() {
        if (System.getenv().get(PLATFORM).contains("ci")) {
            configuration = configurator.createConfig(Format.CONSOLE, allureFormat, reportPortalFormat);
        } else {
            configuration = configurator.createConfig();
        }
    }


    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), appContext);
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromPath(getDriversSettings().getStoryPath()),
                getDriversSettings().getStoryToInclude(), getDriversSettings().getStoryToExclude());
    }

    protected abstract DriversSettings getDriversSettings();

    protected abstract AppiumDriver getDriver();

    protected abstract IPageProvider getPageProvider();
}
