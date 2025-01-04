package org.ValkSteal.dupe.Commands;

import org.ValkSteal.dupe.Dupe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlackListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender.hasPermission("dupe.command.blacklist"))) {
            commandSender.sendMessage("§4You do not have permission to use this command.");
            return true;
        }
        if (!(commandSender instanceof Player player)){
            commandSender.sendMessage("§4This command can only be executed by a player.");
            return true;
        }

        ItemStack handItem = player.getInventory().getItemInMainHand();
        Dupe.Instance.addItemToBlacklist(handItem);

        player.sendMessage("§aItem added to the blacklist.");
        return false;
    }
}
