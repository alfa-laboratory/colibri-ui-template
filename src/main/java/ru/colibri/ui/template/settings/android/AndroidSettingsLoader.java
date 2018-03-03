package ru.colibri.ui.template.settings.android;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.colibri.ui.core.settings.AppSettings;
import ru.colibri.ui.core.settings.DriversSettings;
import ru.colibri.ui.settings.general.PropertyUtils;
import ru.colibri.ui.settings.loaders.AbsSettingsLoader;
import ru.colibri.ui.template.providers.NodeProvider;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;
import static ru.colibri.ui.core.names.ColibriStartFlags.BUILD_VERSION;
import static ru.colibri.ui.template.names.AndroidAppNames.*;
import static ru.colibri.ui.template.names.PropertyNames.*;

/**
 * Класс отвечающий за загрузку настроек приложения и окружения Андроид
 */
@Component
@Qualifier("android")
public class AndroidSettingsLoader extends AbsSettingsLoader {
    private static final String DEFAULT_PATH_TEMPLATE = "src/test/resources/environment/%s/environmentAndroid.properties";
    private static final String DEFAULT_APP_PROPERTIES = "src/test/resources/environment/def/app.properties";

    @Autowired
    NodeProvider nodeProvider;

    @Override
    public AppSettings loadAppSettings(String userName) {
        String pathSpecificUser = format(PATH_USER, userName);
        Properties appProperty = PropertyUtils.readProperty(DEFAULT_APP_PROPERTIES);
        Map<String, String> map = convertPropertyToMap(PropertyUtils.readProperty(pathSpecificUser));
        return AppSettings.builder()
                .packageName(appProperty.getProperty(PACKAGE_NAME))
                .startPageId(appProperty.getProperty(START_PAGE_ID))
                .activityUse(Boolean.valueOf(appProperty.getProperty(ACTIVITY_USE)))
                .userProfile(map)
                .build();
    }


    @Override
    public DriversSettings loadDriverSettings(String platformName) {
        String pathDefault = format(DEFAULT_PATH_TEMPLATE, "def");
        String pathSpecificAndroid = format(PATH_TEMPLATE, platformName);

        Properties props = PropertyUtils.readProperty(pathDefault, pathSpecificAndroid);
        loadArtifactByRemoteRepo(props);
        List<String> packageList = createPackageList();
        startAppiumNodeIfNeed(platformName);
        String appiumURL = prepareAppiumURL(props, platformName);

        return DriversSettings.builder()
                .appiumRemoteUrl(appiumURL)
                .deviceName(props.getProperty(DEVICE_NAME))
                .UDID(props.getProperty(UDID))
                .filePath(props.getProperty(FILE_PATH))
                .implicitlyWaitInSeconds(50)
                .findingTimeOutInSeconds(50)
                .newCommandTimeoutInSeconds(50)
                .storyTimeoutsInSeconds("2400")
                .stepsPackages(packageList)
                .storyPath(props.getProperty(STORY_PATH))
                .storyToInclude(props.getProperty(STORY_TO_INCLUDE))
                .storyToExclude(props.getProperty(STORY_TO_EXCLUDE))
                .pagesPath(props.getProperty(PAGES_PATH))
                .build();

    }

    private void startAppiumNodeIfNeed(String platformName) {
        if (platformName.contains("ci")) {
            nodeProvider.startNode();
        }
    }

    private String prepareAppiumURL(Properties properties, String platformName) {
        return platformName.contains("ci")
                ? properties.getProperty(APPIUM_REMOTE_URL)
                : properties.getProperty(APPIUM_LOCAL_URL);

    }

    private List<String> createPackageList() {
        return ImmutableList.of("ru.colibri.ui.template.steps.general",
                "ru.colibri.ui.template.steps.android",
                "ru.colibri.ui.steps.general",
                "ru.colibri.ui.steps.android");
    }

    private void loadArtifactByRemoteRepo(Properties props) {
        String remoteFilePath = props.getProperty(REMOTE_FILE_PATH);
        remoteFilePath = format(remoteFilePath, System.getProperty(BUILD_VERSION));
        takeArtifact(remoteFilePath, props.getProperty(FILE_PATH));
    }
}
