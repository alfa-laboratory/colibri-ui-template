package ru.colibri.template.settings.android;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.alfabank.autotest.core.settings.AppSettings;
import ru.alfabank.autotest.core.settings.DriversSettings;
import ru.alfabank.autotest.settings.general.PropertyUtils;
import ru.alfabank.autotest.settings.loaders.AbsSettingsLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;

/**
 * Класс отвечающий за загрузку настроек приложения и окружения Андроид
 */
@Component
@Qualifier("android")
public class AndroidSettingsLoader extends AbsSettingsLoader {
    private static final String DEFAULT_PATH_TEMPLATE = "src/test/resources/environment/%s/environmentAndroid.properties";
    private static final String DEFAULT_APP_PROPERTIES = "src/test/resources/environment/def/app.properties";

    @Override
    public AppSettings loadAppSettings(String userName) {
        String pathSpecificUser = format(PATH_USER, userName);
        Properties appProperty = PropertyUtils.readProperty(DEFAULT_APP_PROPERTIES);
        Map<String, String> map = convertPropertyToMap(PropertyUtils.readProperty(pathSpecificUser));
        return AppSettings.builder()
                .packageName(appProperty.getProperty("packageName"))
                .startPageId(appProperty.getProperty("startPageId"))
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

        return DriversSettings.builder()
                .appiumRemoteUrl(props.getProperty("appiumRemoteUrl"))
                .deviceName(props.getProperty("deviceName"))
                .UDID(props.getProperty("UDID"))
                .filePath(props.getProperty("filePath"))
                .implicitlyWaitInSeconds(50)
                .findingTimeOutInSeconds(50)
                .newCommandTimeoutInSeconds(50)
                .storyTimeoutsInSeconds("280")
                .stepsPackages(packageList)
                .storyPath(props.getProperty("storyPath"))
                .storyToInclude(props.getProperty("storyToInclude"))
                .storyToExclude(props.getProperty("storyToExclude"))
                .pagesPath(props.getProperty("pagesPath"))
                .build();
    }

    private List<String> createPackageList() {
        List<String> packageList = new ArrayList<>(2);
        packageList.add("ru.alfabank.autotest.steps.general");
        packageList.add("ru.alfabank.autotest.steps.android");
        return packageList;
    }

    private void loadArtifactByRemoteRepo(Properties props) {
        String remoteFilePath = props.getProperty("remoteFilePath");
        remoteFilePath = format(remoteFilePath, System.getProperty("buildVersion"));
        takeArtifact(remoteFilePath, props.getProperty("filePath"));
    }
}
