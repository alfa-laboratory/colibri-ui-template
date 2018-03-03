package ru.colibri.ui.template.providers;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static java.lang.String.format;

@Log
abstract class CMDProvider {

    private Runtime runtime = Runtime.getRuntime();

    @SneakyThrows
    protected Process execCMDCommand(String command) {
        return runtime.exec(command);
    }

    protected List<String> parseCommandOutput(Process process) {
        List<String> commandOutputResult = new ArrayList<String>();
        try (BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String str = stream.readLine();
            while (str != null) {
                commandOutputResult.add(str);
                str = stream.readLine();
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, format("Ошибка во время чтения вывода консоли process: %s", process), e);
        }
        return commandOutputResult;
    }
}
