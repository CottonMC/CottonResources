package io.github.cottonmc.resources.oregen;

import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.config.OreGenerationSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
                if (ore.getValue().enabled) {
                    registered.add(ore.getKey());
                    OreGenerationSettings settings = ore.getValue();
                    
                    Block oreBlock = Registry.BLOCK.get(new Identifier(settings.ore_block));
                    if (oreBlock==Blocks.AIR || !settings.enabled) continue;
                    
                    biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                            Biome.configureFeature(
                                    CottonOreFeature.COTTON_ORE,
                                    new CottonOreFeatureConfig(
                                            OreFeatureConfig.Target.NATURAL_STONE,
                                            oreBlock.getDefaultState(),
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
}
