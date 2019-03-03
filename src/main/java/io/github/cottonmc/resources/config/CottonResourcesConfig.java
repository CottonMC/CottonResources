package io.github.cottonmc.resources.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.HashMap;

@ConfigFile(name="CottonResources")
public class CottonResourcesConfig {

    @Comment("Needs to be implemented")
    public boolean override_vanilla_generation = false;

    @Comment("Generation settings")
    public HashMap<String, OreGenerationSettings> ores = new HashMap<>();

    {
        ores.putAll(OreGenerationSettings.getDefaultSettingsFor("silver", "lead", "zinc"));
        ores.put("copper", OreGenerationSettings.getDefault().
                withOreBlock("cotton:copper_ore").withMaxHeight(96).
                withClusterCount(256).
                withClusterSize(16));
    }


}