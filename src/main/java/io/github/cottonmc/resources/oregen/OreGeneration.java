package io.github.cottonmc.resources.oregen;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;


public class OreGeneration {
	public static void registerOres() {
		
		//Run the cotton ores step in every biome, once, always. Then we'll generate all the ores we need in one step
		//Stepping away from the regular biome generator flow is important for retrogen later.
		for (Biome biome : Registry.BIOME) {
			biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
				Biome.configureFeature(
					CottonOreFeature.COTTON_ORE,
					FeatureConfig.DEFAULT,
					Decorator.COUNT_RANGE,
					new RangeDecoratorConfig(1, 0, 0, 256)
				)
			);
		}
	}
}
