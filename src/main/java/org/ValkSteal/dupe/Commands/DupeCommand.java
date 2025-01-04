package org.ValkSteal.dupe.Commands;


import org.ValkSteal.dupe.Config.ConfigurationHandler;
import org.ValkSteal.dupe.Dupe;
import org.ValkSteal.dupe.ItemStandardize;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DupeCommand implements CommandExecutor {

    private final ConfigurationHandler configHandler;

    public DupeCommand(ConfigurationHandler configHandler) {
        this.configHandler = configHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(configHandler.getMessage("messages.dupe.notAPlayer"));
            Dupe.Instance.MainLogger.info("A non-player tried to use the dupe command.");
            return true;
        }

        if (!commandSender.hasPermission("dupe.command.dupe")) {
            commandSender.sendMessage(configHandler.getMessage("messages.dupe.noPermission"));
            Dupe.Instance.MainLogger.info(commandSender.getName() + " tried to use the dupe command without permission.");
            return true;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(configHandler.getMessage("messages.dupe.inventoryFull"));
            Dupe.Instance.MainLogger.info(player.getName() + "'s inventory is full.");
            return true;
        }

        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.isEmpty() || handItem.getType().equals(Material.AIR)) {
            player.sendMessage(configHandler.getMessage("messages.dupe.noItemInHand"));
            Dupe.Instance.MainLogger.info(player.getName() + " tried to use the dupe command without an item in hand.");
            return true;
        }

        ItemStack standardizedItem = ItemStandardize.standardize(handItem);

        if (Dupe.Instance.isItemBlacklisted(standardizedItem)) {
            player.sendMessage(configHandler.getMessage("messages.dupe.blacklistedItem"));
            Dupe.Instance.MainLogger.info(player.getName() + " tried to use the dupe command on a blacklisted item.");
            return true;
        }

        player.getInventory().addItem(handItem.clone());
        return true;
    }
}
