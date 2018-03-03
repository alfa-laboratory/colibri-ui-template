package ru.colibri.ui.template.providers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import static java.lang.String.format;
import static ru.colibri.ui.core.names.ColibriStartFlags.PLATFORM;

@Log
@Component
public class NodeProvider extends CMDProvider {

    private String DEFAULT_PATH_TEMPLATE = "src/test/resources/environment/%s/test_node.json";

    public void startNode() {
        String platform = System.getenv().get(PLATFORM);
        String port = getPort(platform);
        if (StringUtils.isEmpty(port)) {
            throw new IllegalArgumentException(format("Не найден порт выбранного устройства. Проверьте конфиг: %s", platform));
        } else {
            Process process = execCMDCommand(format("sh scripts/start_node.sh %s %s", platform, port));
            parseCommandOutput(process).forEach(System.out::println);
        }
    }

    public void stopNode() {
        String platform = System.getenv().get(PLATFORM);
        execCMDCommand(format("ps aux | pgrep %s | xargs kill", platform));
    }

    private String getPort(String platform) {
        String configPath = format(DEFAULT_PATH_TEMPLATE, platform);
        String port = "";
        try {
            JsonReader reader = new JsonReader(new FileReader(configPath));
            JsonObject testNodeData = new Gson().fromJson(reader, JsonObject.class);
            JsonObject testNodeConfig = testNodeData.get("configuration").getAsJsonObject();
            port = testNodeConfig.get("port").getAsString();

        } catch (IOException e) {
            log.log(Level.SEVERE, "Ошибка во время чтения конфига ноды, проверьте правильность выбора устройства: ", e);
        }
        return port;
    }
}
