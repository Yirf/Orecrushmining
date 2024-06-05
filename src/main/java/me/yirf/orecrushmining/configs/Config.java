package me.yirf.orecrushmining.configs;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;

import java.io.File;

public class Config implements JsonSerializable<Config> {

    public static final String configPath = "plugins/orecrushmining/config.json";
    public int blockStageDamageSpeed = 5;
    public int configRefreshRate = 5;

    public int toolCustomModelData = 1111;
    public boolean useToolCustomModelData = true;

    public String speedMetaDataKey = "miningSpeed";


    @Override
    public File getFile() {
        return new File(configPath);
    }
}
