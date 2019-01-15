package io.github.cottonmc.resources.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.HashMap;

@ConfigFile(name="CottonResources")
public class CottonResourcesConfig {

    @Comment("Generation settings")
    public HashMap<String, OreGenerationSettings> ores = new HashMap<>();

    {
        //ores.putAll(OreGenerationSettings.getDefaultSettingsFor("copper", "silver", "lead", "zinc"));
        ores.putAll(OreGenerationSettings.getDefaultSettingsFor("copper", "lead", "zinc"));
        ores.put("silver", OreGenerationSettings.getDefault().
                withOreBlock("cotton:silver_ore").withMaxHeight(24).
                withClusterCount(8).
                withClusterSize(6));
    }


}