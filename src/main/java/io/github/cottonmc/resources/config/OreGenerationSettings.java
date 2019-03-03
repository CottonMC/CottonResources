package io.github.cottonmc.resources.config;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;

public class OreGenerationSettings {

    public boolean enabled;
    public String ore_block;
    public int min_height;
    public int max_height;
    public ArrayList<Integer> dimensions_blacklist;
    public int cluster_count;
    public int cluster_size;

    public OreGenerationSettings enable() {
        this.enabled = true;
        return this;
    }

    public OreGenerationSettings disable() {
        this.enabled = false;
        return this;
    }

    public OreGenerationSettings withOreBlock(String ore_block) {
        this.ore_block = ore_block;
        return this;
    }
    public OreGenerationSettings withMinHeight(int min_height) {
        this.min_height = min_height;
        return this;
    }
    public OreGenerationSettings withMaxHeight(int max_height) {
        this.max_height = max_height;
        return this;
    }
    public OreGenerationSettings excludeDimension(int dimension) {
        this.dimensions_blacklist.add(dimension);
        return this;
    }
    public OreGenerationSettings withClusterCount(int cluster_count) {
        this.cluster_count = cluster_count;
        return this;
    }
    public OreGenerationSettings withClusterSize(int cluster_size) {
        this.cluster_size = cluster_size;
        return this;
    }

    public static OreGenerationSettings getDefault() {
        OreGenerationSettings settings = new OreGenerationSettings();
        settings.enabled = true;
        settings.ore_block = Registry.BLOCK.getId(Blocks.LIGHT_BLUE_WOOL).toString();
        settings.min_height = 6;
        settings.max_height = 64;
        settings.dimensions_blacklist = new ArrayList<>();
        settings.dimensions_blacklist.add(-1);
        settings.dimensions_blacklist.add(1);
        settings.cluster_count = 8;
        settings.cluster_size = 8;
        return settings;
    }

    public static HashMap<String, OreGenerationSettings> getDefaultSettingsFor(String... resources) {
        // This does not check that the resource exists (since we faced loading order issues). Only use on actual ores!
        HashMap<String, OreGenerationSettings> ores = new HashMap<>();
        for (String resourceName : resources) {
            ores.put(resourceName, OreGenerationSettings.getDefault());
        }
        return ores;
    }
}
