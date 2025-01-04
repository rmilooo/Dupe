package org.ValkSteal.dupe.Commands;

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

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender.hasPermission("dupe.command.blacklist"))) {
            commandSender.sendMessage("§4§l[Dupe] You do not have permission to use this command.");
            return true;
        }
        if (!(commandSender instanceof Player player)){
            commandSender.sendMessage("§4§l[Dupe] This command can only be executed by a player.");
            return true;
        }

        ItemStack handItem = ItemStandardize.standardize(player.getInventory().getItemInMainHand());

        if (handItem == null || handItem.getType() == Material.AIR || handItem.isEmpty()) {
            player.sendMessage("§4§l[Dupe] You don't have any items in your hand.");
            Dupe.Instance.MainLogger.warning(player.getName() + " tried to use the blacklist command but didn't have an item in their hand.");
            return true;
        }
        if (Arrays.stream(Dupe.BlackListedItems).anyMatch(itemStack -> itemStack.isSimilar(handItem))) {
            player.sendMessage("§4§l[Dupe] That item is already blacklisted.");
            Dupe.Instance.MainLogger.warning(player.getName() + " tried to use the blacklist command on an already blacklisted item.");
            return true;
        }

        Dupe.Instance.addItemToBlacklist(handItem);
        player.sendMessage("§a§l[Dupe] Item added to the blacklist.");
        Dupe.Instance.MainLogger.info(player.getName() + " added " + handItem.getType().name() + " to the blacklist.");
        return true;
    }
}
