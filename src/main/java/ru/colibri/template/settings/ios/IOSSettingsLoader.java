package ru.colibri.template.settings.ios;

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
@Qualifier("ios")
public class IOSSettingsLoader extends AbsSettingsLoader {
    private static final String DEFAULT_PATH_TEMPLATE = "src/test/resources/environment/%s/environmentIOS.properties";

    @Override
    public AppSettings loadAppSettings(String userName) {
        String pathSpecificUser = format(PATH_USER, userName);
        Map<String, String> map = convertPropertyToMap(PropertyUtils.readProperty(pathSpecificUser));
        return AppSettings.builder()
                .userProfile(map)
                .build();
    }

    @Override
    public DriversSettings loadDriverSettings(String platformName) {
        String pathDefault = format(DEFAULT_PATH_TEMPLATE, "def");
        String pathSpecificIOS = format(PATH_TEMPLATE, platformName);

        Properties props = PropertyUtils.readProperty(pathDefault, pathSpecificIOS);
        loadArtifactByRemoteRepo(props);
        List<String> packageList = createPackageList();

        return DriversSettings.builder()
                .appiumRemoteUrl(props.getProperty("remoteUrl"))
                .deviceName(props.getProperty("deviceName"))
                .filePath(props.getProperty("filePath"))
                .implicitlyWaitInSeconds(20)
                .findingTimeOutInSeconds(20)
                .newCommandTimeoutInSeconds(20)
                .storyTimeoutsInSeconds("7200")
                .stepsPackages(packageList)
                .storyPath(props.getProperty("storyPath"))
                .storyToInclude(props.getProperty("storyToInclude"))
                .storyToExclude(props.getProperty("storyToExclude"))
                .pagesPath(props.getProperty("pagesPath"))
                .UDID(props.getProperty("UDID"))
                .wdaLocalPort(Integer.parseInt(props.getProperty("wdaLocalPort")))
                .build();
    }

    private List<String> createPackageList() {
        List<String> packageList = new ArrayList<>(2);
        packageList.add("ru.alfabank.autotest.steps.general");
        packageList.add("ru.alfabank.autotest.steps.ios");
        return packageList;
    }

    private void loadArtifactByRemoteRepo(Properties props) {
        String remoteFilePath = props.getProperty("remoteFilePath");
        remoteFilePath = format(remoteFilePath, System.getProperty("buildVersion"));
        takeArtifact(remoteFilePath, props.getProperty("filePath"));
    }
}
