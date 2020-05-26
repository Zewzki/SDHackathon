package client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class Logger {

    private static final String logPath = "ZelleMoMe/src/rsc/clientLog.txt";
    private BufferedReader br;
    private BufferedWriter bw;

    private boolean logToConsole;

    public Logger() {

        clearLog();
        logToConsole = false;

    }

    public void log(String msg) {

        try {

            String fullMessage = msg + " @ " + LocalDateTime.now() + System.lineSeparator();
            Files.write(Paths.get(logPath), fullMessage.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            if(logToConsole) System.out.print(fullMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clearLog() {

        File f = new File(logPath);

        if(f.exists()) {
            f.delete();
        }

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setLogToConsole(boolean logToConsole) { this.logToConsole = logToConsole; }

}
