package org.ValkSteal.dupe;

import org.ValkSteal.dupe.Commands.BlackListCommand;
import org.ValkSteal.dupe.Commands.DupeCommand;
import org.ValkSteal.dupe.Config.ConfigurationHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public final class Dupe extends JavaPlugin {

    // Array to store blacklisted items
    public static ItemStack[] BlackListedItems;
    // Singleton instance of Dupe
    public static Dupe Instance;
    // Logger instance for the plugin, logs to 'log.txt' with append mode enabled
    public org.ValkSteal.dupe.Logger MainLogger = new org.ValkSteal.dupe.Logger(this, "DupeLog.txt", true);
    // YMLHandler instance for handling the blacklisted file configuration
    public YMLHandler BlackListedFileConfig = new YMLHandler("blackListed.yml", getDataFolder().getPath());
    // Default logger provided by the Paper server
    public Logger PaperLogger = getLogger();
    // Default Text Configuration Handler
    public ConfigurationHandler configHandler;


    @Override
    public void onEnable() {

        // Sets the instance that can be accessed from anywhere in the plugin
        Instance = this;

        // Initialize the configuration handler
        configHandler = new ConfigurationHandler();
        configHandler.initializeConfig();

        // Start Message
        MainLogger.info("Dupe has been enabled!");

        // Register events and commands
        register();

        // Load blacklisted items from the yml file
        loadItemStackArray(BlackListedFileConfig);

        MainLogger.debug(Arrays.toString(BlackListedItems));
    }

    @Override
    public void onDisable() {

        // Stop Message
        MainLogger.info("Dupe has been disabled!");

        // Save Config and Blacklisted items to the yml file
        saveConfig();
        BlackListedFileConfig.save();

        // Last Message
        MainLogger.info("Config saved successfully!  Goodbye!  :)");
    }

    public void register() {
        // Register events and commands here

        // Commands:
        Objects.requireNonNull(getCommand("dupe")).setExecutor(new DupeCommand(configHandler));
        Objects.requireNonNull(getCommand("blacklist")).setExecutor(new BlackListCommand(configHandler));

        // Config:
        saveDefaultConfig();
    }

    public void loadItemStackArray(YMLHandler ymlHandler) {
        BlackListedItems = ymlHandler.getItemStackArray("BlacklistedItems");
    }

    public void addItemToBlacklist(ItemStack itemStack) {
        BlackListedItems = Arrays.copyOf(BlackListedItems, BlackListedItems.length + 1);
        BlackListedItems[BlackListedItems.length - 1] = itemStack;
        BlackListedFileConfig.set("BlacklistedItems", BlackListedItems);
        BlackListedFileConfig.save();
    }
}