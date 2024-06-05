package me.yirf.orecrushmining;

import io.github.itzispyder.pdk.PDK;
import io.github.itzispyder.pdk.utils.SchedulerUtils;
import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.yirf.orecrushmining.commands.Command;
import me.yirf.orecrushmining.configs.Config;
import me.yirf.orecrushmining.listeners.BlockListener;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Orecrushmining extends JavaPlugin {

    public static Orecrushmining instance;


    public static final Config config = JsonSerializable.load(Config.configPath, Config.class, new Config());

    @Override
    public void onEnable() {
        PDK.init(this);

        new BlockListener(this).register();
        new Command().register();

        config.save();
        this.createFiles();
        int refreshRate = Math.max(1, config.configRefreshRate);

        SchedulerUtils.repeat(refreshRate * 60 * 20, config::save);

    }

    @Override
    public void onDisable() {
        config.save();
    }

    private File blocksf;
    private FileConfiguration blocks;
    private void createFiles() {
        blocksf = new File("plugins/orecrushmining/", "blocks.yml");
        if (!blocksf.exists()) {
            blocksf.getParentFile().mkdirs();
            saveResource("blocks.yml", false);
        }
        blocks = new YamlConfiguration();

        try {
            blocks.load(blocksf);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
