package io.github.cottonmc.resources.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.HashMap;

@ConfigFile(name = "CottonResources")
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
                withMaxHeight(48).withMinHeight(6).
                withClusterCount(8).withClusterSize(8));
        ores.put("tin", OreGenerationSettings.getDefault().withOreBlock("c:tin_ore").
                withMaxHeight(48).withMinHeight(6).
                withClusterCount(8).withClusterSize(8));
        ores.put("aluminum", OreGenerationSettings.getDefault().withOreBlock("c:aluminum_ore").
                withMaxHeight(48).withMinHeight(6).
                withClusterCount(4).withClusterSize(8));
        ores.put("tungsten", OreGenerationSettings.getDefault().withOreBlock("c:tungsten_ore").
                withMaxHeight(32).withMinHeight(6).
                withClusterCount(4).withClusterSize(8));
        ores.put("uranium", OreGenerationSettings.getDefault().withOreBlock("c:uranium_ore").
                withMaxHeight(32).withMinHeight(6).
                withClusterCount(4).withClusterSize(4));
        ores.put("cobalt", OreGenerationSettings.getDefault().withOreBlock("c:cobalt_ore").
                withMaxHeight(20).withMinHeight(6).
                withClusterCount(4).withClusterSize(3));
        ores.put("platinum", OreGenerationSettings.getDefault().withOreBlock("c:platinum_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(2).withClusterSize(3));
        ores.put("palladium", OreGenerationSettings.getDefault().withOreBlock("c:palladium_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(2).withClusterSize(3));
        ores.put("osmium", OreGenerationSettings.getDefault().withOreBlock("c:osmium_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(2).withClusterSize(3));
        ores.put("iridium", OreGenerationSettings.getDefault().withOreBlock("c:iridium_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(2).withClusterSize(3));
        ores.put("ruby", OreGenerationSettings.getDefault().withOreBlock("c:ruby_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(1).withClusterSize(5));
        ores.put("topaz", OreGenerationSettings.getDefault().withOreBlock("c:topaz_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(1).withClusterSize(5));
        ores.put("sapphire", OreGenerationSettings.getDefault().withOreBlock("c:sapphire_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(1).withClusterSize(5));
        ores.put("peridot", OreGenerationSettings.getDefault().withOreBlock("c:peridot_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(1).withClusterSize(5));
        ores.put("amethyst", OreGenerationSettings.getDefault().withOreBlock("c:amethyst_ore").
                withMaxHeight(16).withMinHeight(6).
                withClusterCount(1).withClusterSize(5));
    }


}