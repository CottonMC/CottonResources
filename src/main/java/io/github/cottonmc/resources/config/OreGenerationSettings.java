package io.github.cottonmc.resources.config;

import io.github.cottonmc.resources.CommonResources;
import io.github.cottonmc.resources.ResourceType;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;

public class OreGenerationSettings {

    public boolean enabled;
    public String base_material;
    public String ore_block;
    public int min_height;
    public int max_height;
    public ArrayList<Integer> dimensions;
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

    public OreGenerationSettings withBaseMaterial(String base_material) {
        this.base_material = base_material;
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
    public OreGenerationSettings withDimensions(ArrayList<Integer> dimensions) {
        this.dimensions = dimensions;
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
        settings.base_material = "minecraft:stone";
        settings.ore_block = Registry.BLOCK.getId(Blocks.LIGHT_BLUE_WOOL).toString();
        settings.min_height = 6;
        settings.max_height = 64;
        settings.dimensions = new ArrayList<>();
        settings.dimensions.add(0);
        settings.cluster_count = 8;
        settings.cluster_size = 8;
        return settings;
    }

    public static HashMap<String, OreGenerationSettings> getDefaultSettingsFor(String... resources) {
        HashMap<String, OreGenerationSettings> ores = new HashMap<>();
        for (String resourceName : resources) {
            ResourceType resource = CommonResources.BUILTINS.get(resourceName);
            if (resource.contains(resource.getBaseResource() + "_ore")) {
                ores.put(resourceName, OreGenerationSettings.getDefault());
            }
        }
        return ores;
    }
}
