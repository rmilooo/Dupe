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

public class BlackListCommand implements CommandExecutor {

    private final ConfigurationHandler configHandler;

    public BlackListCommand(ConfigurationHandler configHandler) {
        this.configHandler = configHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("dupe.command.blacklist")) {
            sender.sendMessage(configHandler.getMessage("messages.blacklist.noPermission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(configHandler.getMessage("messages.blacklist.usage"));
            return true;
        }

        String subcommand = args[0].toLowerCase();
        return switch (subcommand) {
            case "add" -> handleAdd(sender);
            case "remove" -> handleRemove(sender);
            case "list" -> handleList(sender);
            default -> {
                sender.sendMessage(configHandler.getMessage("messages.blacklist.invalidSubcommand"));
                yield true;
            }
        };
    }

    private boolean handleAdd(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(configHandler.getMessage("messages.blacklist.notAPlayer"));
            return true;
        }

        ItemStack handItem = ItemStandardize.standardize(player.getInventory().getItemInMainHand());
        if (handItem == null || handItem.getType() == Material.AIR || handItem.isEmpty()) {
            player.sendMessage(configHandler.getMessage("messages.blacklist.noItemInHand"));
            return true;
        }

        if (Dupe.Instance.isItemBlacklisted(handItem)) {
            player.sendMessage(configHandler.getMessage("messages.blacklist.alreadyBlacklisted"));
            return true;
        }

        Dupe.Instance.addItemToBlacklist(handItem);
        player.sendMessage(configHandler.getMessage("messages.blacklist.itemAdded"));
        return true;
    }

    private boolean handleRemove(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(configHandler.getMessage("messages.blacklist.notAPlayer"));
            return true;
        }

        ItemStack itemInHand = ItemStandardize.standardize(player.getInventory().getItemInMainHand());

        if (itemInHand == null || itemInHand.getType().isAir()) {
            player.sendMessage(configHandler.getMessage("messages.blacklist.noItemInHand"));
            return true;
        }

        if (Dupe.Instance.removeItemFromBlacklist(itemInHand)) {
            player.sendMessage(configHandler.getMessage("messages.blacklist.itemRemoved"));
        } else {
            player.sendMessage(configHandler.getMessage("messages.blacklist.itemNotBlacklisted")
                    .replace("{item}", itemDisplayNameOrDefault(itemInHand, itemInHand.getType().name())));
        }

        return true;
    }

    private boolean handleList(CommandSender sender) {
        ItemStack[] blacklistedItems = Dupe.Instance.getBlackListedItems();
        if (blacklistedItems.length == 0) {
            sender.sendMessage(configHandler.getMessage("messages.blacklist.noBlacklistedItems"));
        } else {
            sender.sendMessage(configHandler.getMessage("messages.blacklist.listHeader"));
            for (ItemStack item : blacklistedItems) {
                sender.sendMessage("- " + itemDisplayNameOrDefault(item, item.getType().name()));
            }
        }
        return true;
    }

    public String itemDisplayNameOrDefault(ItemStack stack, String def) {
        if (stack.getItemMeta() == null || stack.getItemMeta().getDisplayName().trim().isBlank()) {
            return def;
        }
        return stack.getItemMeta().getDisplayName();
    }
}
