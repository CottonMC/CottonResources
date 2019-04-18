package io.github.cottonmc.resources.oregen;

import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.config.OreGenerationSettings;
import io.github.cottonmc.resources.oregen.CottonOreFeature;
import io.github.cottonmc.resources.oregen.CottonOreFeatureConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class OreGeneration {
    private static Set<String> registered = new HashSet<>();

    public static void registerOres() {
        for (Biome biome : Registry.BIOME) {
            for (Map.Entry<String, OreGenerationSettings> ore : CottonResources.config.ores.entrySet()) {
                registered.add(ore.getKey());
                OreGenerationSettings settings = ore.getValue();
                if (!Registry.BLOCK.containsId(new Identifier(settings.ore_block)) || !settings.enabled) continue;
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Biome.configureFeature(
                                CottonOreFeature.COTTON_ORE,
                                new CottonOreFeatureConfig(
                                        OreFeatureConfig.Target.NATURAL_STONE,
                                        Registry.BLOCK.get(new Identifier(settings.ore_block)).getDefaultState(),
                                        settings.cluster_size,
                                        settings.dimensions_blacklist),
                                Decorator.COUNT_RANGE,
                                new RangeDecoratorConfig(settings.cluster_count, 0, 0, settings.max_height)
                        )
                );
            }
        }
    }

    public static void addDefaultGen(String resource, OreGenerationSettings settings) {
        if (!CottonResources.config.ores.containsKey(resource)) {
            if (registered.contains(resource)) {
                CottonResources.logger.warn("A mod tried to register ore gen for " + resource + ", even though another mod already did. Only the first ore gen will be added.");
                CottonResources.logger.warn("Try adding ore gen to the Cotton Resources config file to avoid this issue.");
                return;
            }
            registered.add(resource);
            for (Biome biome : Registry.BIOME) {
                if (!Registry.BLOCK.containsId(new Identifier(settings.ore_block)) || !settings.enabled) continue;
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Biome.configureFeature(
                                CottonOreFeature.COTTON_ORE,
                                new CottonOreFeatureConfig(
                                        OreFeatureConfig.Target.NATURAL_STONE,
                                        Registry.BLOCK.get(new Identifier(settings.ore_block)).getDefaultState(),
                                        settings.cluster_size,
                                        settings.dimensions_blacklist),
                                Decorator.COUNT_RANGE,
                                new RangeDecoratorConfig(settings.cluster_count, 0, 0, settings.max_height)
                        )
                );
            }
        }
    }
}
