package org.ValkSteal.dupe;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YMLHandler {
    private final File file;
    private final FileConfiguration config;

    public YMLHandler(String fileName, String pluginFolder) {
        this.file = new File(pluginFolder, fileName);
        if (!file.exists()) {
            try {
                if (file.getParentFile().mkdirs()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }

    public int getInt(String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        return config.getBoolean(path, defaultValue);
    }

    public ItemStack getItemStack(String path, ItemStack defaultValue) {
        return config.getItemStack(path, defaultValue);
    }

    public ItemStack[] getItemStackArray(String path) {
        List<?> list = config.getList(path);
        if (list == null) return new ItemStack[0];

        List<ItemStack> itemStacks = new ArrayList<>();
        for (Object obj : list) {
            if (obj instanceof ItemStack) {
                itemStacks.add((ItemStack) obj);
            }
        }
        return itemStacks.toArray(new ItemStack[0]);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
