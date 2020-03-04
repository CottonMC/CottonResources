package io.github.cottonmc.resources.mixin;

import io.github.cottonmc.resources.CottonResources;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This Mixin exists solely for disabling vanilla ores.
 */
@Mixin(DefaultBiomeFeatures.class)
public abstract class MixinVanillaOregen {

    @Inject(method = "addDefaultOres", at = @At(value = "HEAD"), cancellable = true)
    private static void mixinDefaultOres(Biome biome, CallbackInfo ci) {
        if (CottonResources.CONFIG.override_vanilla_generation) {
            ci.cancel();
        }
    }

    @Inject(method = "addExtraGoldOre", at = @At(value = "HEAD"), cancellable = true)
    private static void mixinExtraGoldOre(Biome biome, CallbackInfo ci) {
        if (CottonResources.CONFIG.override_vanilla_generation) {
            ci.cancel();
        }
    }

    @Inject(method = "addEmeraldOre", at = @At(value = "HEAD"), cancellable = true)
    private static void mixinEmeraldOre(Biome biome, CallbackInfo ci) {
        if (CottonResources.CONFIG.override_vanilla_generation) {
            ci.cancel();
        }
    }
}
