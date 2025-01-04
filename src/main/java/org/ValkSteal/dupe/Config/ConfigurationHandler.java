package org.ValkSteal.dupe.Config;


import org.ValkSteal.dupe.Dupe;
import org.ValkSteal.dupe.YMLHandler;

/**
 * Handles the configuration setup and management for the application.
 */
public class ConfigurationHandler {

    private final YMLHandler configHandler;

    /**
     * Initializes the configuration handler with the specified configuration file.
     */
    public ConfigurationHandler() {
        this.configHandler = new YMLHandler("TextConfig.yml", Dupe.Instance.getDataFolder().getPath());
    }

    /**
     * Loads default configuration values and saves them if not already present.
     * 
     */
    public void initializeConfig() {
        configHandler.getConfig().addDefault("messages.dupe.noPermission", "§4§l[Dupe] You do not have permission to use this command.");
        configHandler.getConfig().addDefault("messages.dupe.notAPlayer", "§4§l[Dupe] This command can only be executed by a player.");
        configHandler.getConfig().addDefault("messages.dupe.inventoryFull", "§4§l[Dupe] Your inventory is full.");
        configHandler.getConfig().addDefault("messages.dupe.noItemInHand", "§4§l[Dupe] You don't have any items in your hand.");
        configHandler.getConfig().addDefault("messages.dupe.blacklistedItem", "§4§l[Dupe] This item is blacklisted.");

        configHandler.getConfig().addDefault("messages.blacklist.noPermission", "§4§l[Dupe] You do not have permission to use this command.");
        configHandler.getConfig().addDefault("messages.blacklist.notAPlayer", "§4§l[Dupe] This command can only be executed by a player.");
        configHandler.getConfig().addDefault("messages.blacklist.noItemInHand", "§4§l[Dupe] You don't have any items in your hand.");
        configHandler.getConfig().addDefault("messages.blacklist.alreadyBlacklisted", "§4§l[Dupe] That item is already blacklisted.");
        configHandler.getConfig().addDefault("messages.blacklist.itemAdded", "§a§l[Dupe] Item added to the blacklist.");

        configHandler.getConfig().options().copyDefaults(true);
        configHandler.save();
    }

    /**
     * Retrieves a message from the configuration.
     *
     * @param key The key of the message in the configuration.
     * @return The message, or a default error message if not found.
     */
    public String getMessage(String key) {
        if (configHandler.getConfig().contains(key)) {
            return configHandler.getConfig().getString(key);
        }
        Dupe.Instance.MainLogger.error("Message key not found: " + key);
        return "§4§l[Dupe] An error occurred while retrieving a message.";
    }
}
