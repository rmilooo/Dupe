package org.ValkSteal.dupe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Dupe extends JavaPlugin {
    public static ItemStack[] BlackListedItems;
    public Logger Logger = getLogger();
    public static Dupe Instance;

    @Override
    public void onEnable() {
        Instance = this;


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void register(){
        // Register events and commands here


        // Commands:

        // Events:
    }
}
