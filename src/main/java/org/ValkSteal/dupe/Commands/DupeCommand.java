package org.ValkSteal.dupe.Commands;

import org.ValkSteal.dupe.Dupe;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DupeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("§4This command can only be executed by a player.");
            Dupe.Instance.MainLogger.info("A non player tried to use the dupe command.");
            return true;
        }

        if (!commandSender.hasPermission("dupe.command.dupe")) {
            commandSender.sendMessage("§4You do not have permission to use this command.");
            Dupe.Instance.MainLogger.info(commandSender.getName() + " tried to use the dupe command without permission.");
            return true;
        }

        // Check if the player's inventory is full
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage("§4Your inventory is full.");
            Dupe.Instance.MainLogger.info(player.getName() + "'s inventory is full.");
            return true;
        }

        // Check if the player has any items in their hand
        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.isEmpty() || handItem.getType().equals(Material.AIR)) {
            player.sendMessage("§4You don't have any items in your hand.");
            Dupe.Instance.MainLogger.info(player.getName() + " tried to use the dupe command but didn't have an item in their hand.");
            return true;
        }


        // Standardize the item
        ItemStack standardizedItem = handItem.clone();
        standardizedItem.setAmount(1);

        // Ensure the item meta is not null and supports Damageable
        ItemMeta meta = standardizedItem.getItemMeta();
        if (meta instanceof Damageable standardizedMeta) {
            standardizedMeta.setDamage(0);
            standardizedItem.setItemMeta(standardizedMeta);
        } else {
            // Handle non-damageable items if needed
            throw new IllegalArgumentException("Item is not damageable");
        }

        boolean isBlacklisted = Arrays.stream(Dupe.BlackListedItems).anyMatch(itemStack -> itemStack.isSimilar(standardizedItem));
        if (isBlacklisted){
            player.sendMessage("§4This item is blacklisted.");
            Dupe.Instance.MainLogger.info(player.getName() + " tried to use the dupe command on a blacklisted item.");
            return true;
        }

        // Dupe the item and add it to the player's inventory
        player.getInventory().addItem(handItem.clone());

        return true;
    }
}
