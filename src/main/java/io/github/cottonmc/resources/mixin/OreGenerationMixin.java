package io.github.cottonmc.resources.mixin;

import io.github.cottonmc.resources.config.CottonResourcesConfig;
import io.github.cottonmc.resources.config.OreGenerationSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(DefaultBiomeFeatures.class)
public abstract class OreGenerationMixin {


    @Inject(method = "addDefaultOres", at = @At(value = "HEAD"))
    private static void mixinDefaultOres(Biome biome_1, CallbackInfo ci) {
        for (Map.Entry<String, OreGenerationSettings> ore : CottonResourcesConfig.ores.entrySet()) {
            String name = ore.getKey();
            OreGenerationSettings settings = ore.getValue();
            biome_1.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
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
        if (CottonResourcesConfig.override_vanilla_generation) {
            ci.cancel();
        }
        biome_1.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, Registry.BLOCK.get(new Identifier("cotton:copper_ore")).getDefaultState(), 17), Decorator.COUNT_RANGE, new RangeDecoratorConfig(20, 0, 0, 128)));
        System.out.println(Registry.BLOCK.get(new Identifier("cotton:copper_ore")).getDefaultState());
    }

    @Inject(method = "addExtraGoldOre", at = @At(value = "HEAD"))
    private static void mixinExtraGoldOre(Biome biome_1, CallbackInfo ci) {
        if (CottonResourcesConfig.override_vanilla_generation) {
            ci.cancel();
        }
    }

    @Inject(method = "addEmeraldOre", at = @At(value = "HEAD"))
    private static void mixinEmeraldOre(Biome biome_1, CallbackInfo ci) {
        if (CottonResourcesConfig.override_vanilla_generation) {
            ci.cancel();
        }
    }
}
