package org.ValkSteal.dupe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
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
    private final MiniMessage miniMessage;

    public Logger(JavaPlugin plugin, String logFileName, boolean logToFile) {
        this.plugin = plugin;
        this.logFileName = logFileName;
        this.logToFile = logToFile;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.miniMessage = MiniMessage.miniMessage();
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
        String logMessage = String.format("%s", message);

        // Print to console with color
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
