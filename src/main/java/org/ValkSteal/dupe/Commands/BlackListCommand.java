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

public class BlackListCommand implements CommandExecutor {

    private final ConfigurationHandler configHandler;

    public BlackListCommand(ConfigurationHandler configHandler) {
        this.configHandler = configHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("dupe.command.blacklist")) {
            commandSender.sendMessage(configHandler.getMessage("messages.blacklist.noPermission"));
            return true;
        }

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(configHandler.getMessage("messages.blacklist.notAPlayer"));
            return true;
        }

        ItemStack handItem = ItemStandardize.standardize(player.getInventory().getItemInMainHand());
        if (handItem == null || handItem.getType() == Material.AIR || handItem.isEmpty()) {
            player.sendMessage(configHandler.getMessage("messages.blacklist.noItemInHand"));
            Dupe.Instance.MainLogger.warning(player.getName() + " tried to use the blacklist command without an item in hand.");
            return true;
        }

        if (Arrays.stream(Dupe.BlackListedItems).anyMatch(itemStack -> itemStack.isSimilar(handItem))) {
            player.sendMessage(configHandler.getMessage("messages.blacklist.alreadyBlacklisted"));
            Dupe.Instance.MainLogger.warning(player.getName() + " tried to blacklist an already blacklisted item.");
            return true;
        }

        Dupe.Instance.addItemToBlacklist(handItem);
        player.sendMessage(configHandler.getMessage("messages.blacklist.itemAdded"));
        Dupe.Instance.MainLogger.info(player.getName() + " added " + handItem.getType().name() + " to the blacklist.");
        return true;
    }
}
