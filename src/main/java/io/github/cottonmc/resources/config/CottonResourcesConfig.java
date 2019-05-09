package io.github.cottonmc.resources.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.HashMap;

@ConfigFile(name="CottonResources")
public class CottonResourcesConfig {

    @Comment("If true, vanilla's ore gen will be cancelled.")
    public boolean override_vanilla_generation = false;

    @Comment("Settings for ore generation. If an ore isn't registered, it won't generate.")
    public HashMap<String, OreGenerationSettings> ores = new HashMap<>();

    {
        ores.put("copper", OreGenerationSettings.getDefault().withOreBlock("c:copper_ore").
                withMaxHeight(96).withMinHeight(32).
                withClusterCount(20).withClusterSize(10));
        ores.put("silver", OreGenerationSettings.getDefault().withOreBlock("c:silver_ore").
                withMaxHeight(48).withMinHeight(6).
                withClusterCount(4).withClusterSize(8));
        ores.put("lead", OreGenerationSettings.getDefault().withOreBlock("c:lead_ore").
                withMaxHeight(48).withMinHeight(6).
                withClusterCount(4).withClusterSize(8));
        ores.put("zinc", OreGenerationSettings.getDefault().withOreBlock("c:zinc_ore").
                withMaxHeight(72).withMinHeight(6).
                withClusterCount(8).withClusterSize(8));
    }


}