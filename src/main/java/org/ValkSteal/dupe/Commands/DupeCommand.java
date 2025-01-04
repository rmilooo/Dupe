package org.ValkSteal.dupe.Commands;

import org.ValkSteal.dupe.Dupe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DupeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be executed by a player.");
            Dupe.Instance.MainLogger.info("A non player tried to use the dupe command.");
            return true;
        }
        if (!commandSender.hasPermission("dupe.command.dupe")) {

        }
        return false;
    }
}
