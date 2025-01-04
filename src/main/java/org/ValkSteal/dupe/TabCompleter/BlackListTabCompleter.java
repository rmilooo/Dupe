package org.ValkSteal.dupe.TabCompleter;

import org.ValkSteal.dupe.Dupe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlackListTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            // Suggest main subcommands: add, remove, list
            return Arrays.asList("add", "remove", "list");
        }
        // Default to an empty list if no matches
        return new ArrayList<>();
    }
}
