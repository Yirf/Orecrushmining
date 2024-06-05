package me.yirf.orecrushmining.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Blocks {


    public static FileConfiguration get() {
        File file = new File("plugins/orecrushmining", "blocks.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static class Config {
        private static final String path = "blocks.";

        public static int blockHealth(String key) {
            return get().getInt(path + key + ".health");
        }
    }
}