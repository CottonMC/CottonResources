package io.github.cottonmc.resources;

import io.github.cottonmc.resources.config.CottonResourcesConfig;
import io.github.cottonmc.resources.config.OreGenerationSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Map;

public class OreGeneration {
    public static void registerOres() {
        for (Biome biome : Registry.BIOME) {
            for (Map.Entry<String, OreGenerationSettings> ore : CottonResourcesConfig.ores.entrySet()) {
                String name = ore.getKey();
                OreGenerationSettings settings = ore.getValue();
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Biome.configureFeature(
                                Feature.ORE,
                                new OreFeatureConfig(
                                        OreFeatureConfig.Target.NATURAL_STONE,
                                        Registry.BLOCK.get(new Identifier(settings.ore_block)).getDefaultState(),
                                        settings.cluster_size),
                                Decorator.COUNT_RANGE,
                                new RangeDecoratorConfig(settings.cluster_count, 0, 0, settings.max_height)
                        )
                );
            }
            biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, Registry.BLOCK.get(new Identifier("cotton:copper_ore")).getDefaultState(), 17), Decorator.COUNT_RANGE, new RangeDecoratorConfig(20, 0, 0, 128)));
        }
    }
}
