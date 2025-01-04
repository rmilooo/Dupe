package org.ValkSteal.dupe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStandardize {
    public static ItemStack standardize(ItemStack itemStack) {
        // Handle null input
        if (itemStack == null || itemStack.isEmpty() || itemStack.getType().isAir()) {
            return null;
        }

        // Ensure the item meta is not null and supports Damageable
        ItemMeta meta = itemStack.getItemMeta();
        if (meta instanceof Damageable standardizedMeta) {
            standardizedMeta.setDamage(0);
            itemStack.setItemMeta(standardizedMeta);
            return itemStack;
        }

        // Return null if the item meta does not support Damageable or is null
        return null;
    }
}
