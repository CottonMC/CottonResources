package io.github.cottonmc.resources.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ConfigFile(name="CottonResources")
public class CottonResourcesConfig {

    @Comment("If true, vanilla's ore gen will be cancelled.")
    public boolean override_vanilla_generation = false;

    @Comment("Which built-in resources should be registered, even if a mod doesn't ask for them. \n"
            + "Adding a \"*\" entry will register all built-in resources.")
    public List<String> enabledResources = new ArrayList<>();

    @Comment("Settings for ore generation. If an ore isn't registered, it won't generate.")
    public HashMap<String, OreGenerationSettings> ores = new HashMap<>();

    {
        ores.putAll(OreGenerationSettings.getDefaultSettingsFor("silver", "lead", "zinc"));
        ores.put("copper", OreGenerationSettings.getDefault().
                withOreBlock("c:copper_ore").withMaxHeight(96).
                withClusterCount(256).
                withClusterSize(16));
    }


}