package io.github.cottonmc.resources.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.ArrayList;

@ConfigFile(name="CottonResources")
public class CottonResourcesConfig {

    @Comment("List of items that will be enabled by default, even if no mod requests them:")
    public ArrayList<String> enabledItems = new ArrayList<>();

    @Comment("List of blocks that will be enabled by default, even if no mod requests them:")
    public ArrayList<String> enabledBlocks = new ArrayList<>();
}
