package org.ValkSteal.dupe;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private final JavaPlugin plugin;
    private final String logFileName;
    private final boolean logToFile;
    private final SimpleDateFormat dateFormat;

    public Logger(JavaPlugin plugin, String logFileName, boolean logToFile) {
        this.plugin = plugin;
        this.logFileName = logFileName;
        this.logToFile = logToFile;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (logToFile) {
            initializeLogFile();
        }
    }

    private void initializeLogFile() {
        File logFile = new File(plugin.getDataFolder(), logFileName);
        if (!logFile.exists()) {
            try {
                if (logFile.getParentFile() != null && logFile.getParentFile().mkdirs()) {
                    logFile.createNewFile();
                }
            } catch (IOException e) {
                Dupe.Instance.PaperLogger.warning("[PaperLogger] Failed to create log file: " + e.getMessage());
            }
        }
    }

    public void log(LogLevel level, String message) {
        String timeStamp = dateFormat.format(new Date());
        String logMessage = String.format("[%s] [%s] %s", timeStamp, level.name(), message);

        // Print to console
        switch (level) {
            case INFO:
                Dupe.Instance.PaperLogger.info(logMessage);
                break;
            case WARNING:
                Dupe.Instance.PaperLogger.warning(logMessage);
                break;
            case ERROR:
                Dupe.Instance.PaperLogger.severe(logMessage);
                break;
            case DEBUG:
                if (plugin.getConfig().getBoolean("debug-mode", false)) {
                    Dupe.Instance.PaperLogger.info("[DEBUG] " + logMessage);
                }
                break;
        }

        // Write to log file
        if (logToFile) {
            writeToFile(logMessage);
        }
    }

    private void writeToFile(String message) {
        try (FileWriter writer = new FileWriter(new File(plugin.getDataFolder(), logFileName), true)) {
            writer.write(message + System.lineSeparator());
        } catch (IOException e) {
            Dupe.Instance.PaperLogger.warning("[PaperLogger] Failed to write to log file: " + e.getMessage());
        }
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public enum LogLevel {
        INFO, WARNING, ERROR, DEBUG
    }
}
