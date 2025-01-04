package org.ValkSteal.dupe;

import org.ValkSteal.dupe.Commands.DupeCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Dupe extends JavaPlugin {
    public static ItemStack[] BlackListedItems;
    public Logger Logger = getLogger();
    public static Dupe Instance;

    @Override
    public void onEnable() {
        // Sets the instance that can be accessed from anywhere in the plugin
        Instance = this;


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void register(){
        // Register events and commands here

        // Commands:
        Objects.requireNonNull(getCommand("dupe")).setExecutor(new DupeCommand());

        // Permissions:
        getServer().getPluginManager().addPermission(new org.bukkit.permissions.Permission("dupe.use"));
        getServer().getPluginManager().addPermission(new org.bukkit.permissions.Permission("dupe.admin"));

        // Config:
        saveDefaultConfig();


        // Events:
    }
}
