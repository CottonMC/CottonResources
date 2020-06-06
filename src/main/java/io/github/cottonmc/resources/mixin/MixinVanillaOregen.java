/*
 * MIT License
 *
 * Copyright (c) 2018-2020 The Cotton Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.cottonmc.resources.mixin;

import io.github.cottonmc.resources.CottonResources;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

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

	@Inject(method = "addNetherOres", at = @At("HEAD"), cancellable = true)
	private static void mixinAddNetherOres(Biome biome, int goldCount, int quartzCount, CallbackInfo ci) {
		if (CottonResources.CONFIG.override_vanilla_generation) {
			ci.cancel();
		}
	}
}
