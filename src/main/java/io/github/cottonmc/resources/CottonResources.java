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

package io.github.cottonmc.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.cottonmc.resources.command.CottonResourcesCommands;
import io.github.cottonmc.resources.common.CottonResourcesItemGroup;
import io.github.cottonmc.resources.config.CottonResourcesConfig;
import io.github.cottonmc.resources.oregen.BiomeSpec;
import io.github.cottonmc.resources.oregen.CottonOreFeature;
import io.github.cottonmc.resources.oregen.DimensionSpec;
import io.github.cottonmc.resources.oregen.OreGenerationSettings;
import io.github.cottonmc.resources.oregen.OregenResourceListener;
import io.github.cottonmc.resources.oregen.TaggableSpec;
import io.github.cottonmc.resources.tag.WorldTagReloadListener;
import io.github.cottonmc.resources.type.ResourceType;
import io.github.cottonmc.resources.util.PrefixMessageFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CottonResources implements ModInitializer {
	public static final String COMMON = "c";
	public static final String MODID = "cotton-resources";
	public static final Logger LOGGER = LogManager.getLogger("CottonResources", new PrefixMessageFactory("CottonResources"));
	public static CottonResourcesConfig CONFIG = new CottonResourcesConfig(); //ConfigManager.loadConfig(CottonResourcesConfig.class);
	public static final Registry<ResourceType> RESOURCE_TYPES = new SimpleRegistry<>();
	public static final Jankson JANKSON = JanksonFactory.builder()
			.registerTypeAdapter(OreGenerationSettings.class, OreGenerationSettings::deserialize)
			.registerSerializer(BiomeSpec.class, (spec, marshaller) -> TaggableSpec.serialize(spec))
			.registerSerializer(DimensionSpec.class, (spec, marshaller) -> TaggableSpec.serialize(spec))
			.build();
	private static final String[] MACHINE_AFFIXES = new String[]{"gear", "plate"};

	public static SoundEvent METAL_STEP_SOUND;
	public static BlockSoundGroup METAL_SOUND_GROUP;

	@Override
	public void onInitialize() {
		METAL_STEP_SOUND = Registry.register(Registry.SOUND_EVENT, "block.cotton-resources.metal.step", new SoundEvent(CottonResources.common("block.cotton-resources.metal.step")));
		METAL_SOUND_GROUP = new BlockSoundGroup(1.0F, 1.5F, SoundEvents.BLOCK_METAL_BREAK, METAL_STEP_SOUND, SoundEvents.BLOCK_METAL_PLACE, SoundEvents.BLOCK_METAL_HIT, SoundEvents.BLOCK_METAL_FALL);

		CottonResourcesItemGroup.init();

		BuiltinResources.ALUMINUM.getClass(); // Gotta load all the entries ourselves somehow. If someone else already did, then good

		CottonResources.RESOURCE_TYPES.stream().forEach(ResourceType::registerAll);

		// Track new registrations and register them.
		RegistryEntryAddedCallback.event(CottonResources.RESOURCE_TYPES).register((rawId, identifier, resourceType) -> {
			resourceType.registerAll();
		});

		setupBiomeGenerators(); //add cotton-resources ores to all current biomes
		RegistryEntryAddedCallback.event(Registry.BIOME).register((id, ident, biome) -> setupBiomeGenerator(biome)); //Add cotton-resources ores to any later biomes that appear

		// THIS MUST BE WORLD TAGS, THEN OREGEN LISTENER OR DIMENSION AND BIOME SPECS SHALL FAIL GLORIOUSLY.
		ResourceManagerHelper.get(net.minecraft.resource.ResourceType.SERVER_DATA).registerReloadListener(new WorldTagReloadListener());
		ResourceManagerHelper.get(net.minecraft.resource.ResourceType.SERVER_DATA).registerReloadListener(new OregenResourceListener());

		CommandRegistry.INSTANCE.register(false, CottonResourcesCommands::register);

		File file = new File(FabricLoader.getInstance().getConfigDirectory(), "CottonResources.json5");

		if (file.exists()) {
			CONFIG = loadConfig();
		}

		saveConfig(CONFIG);
	}

	private static void setupBiomeGenerators() {
		for (Biome biome : Registry.BIOME) {
			setupBiomeGenerator(biome);
		}
	}

	private static void setupBiomeGenerator(Biome biome) {
		biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
				CottonOreFeature.COTTON_ORE
						.configure(FeatureConfig.DEFAULT)
						.createDecoratedFeature(
								Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(1, 0, 0, 256)
								)
						));
	}

	public static CottonResourcesConfig loadConfig() {
		File file = new File(FabricLoader.getInstance().getConfigDirectory(), "CottonResources.json5");

		try {
			JsonObject json = JANKSON.load(file);
			CottonResources.LOGGER.info("Loading: " + json);
			CottonResourcesConfig loading = JANKSON.fromJson(json, CottonResourcesConfig.class);
			CottonResources.LOGGER.info("Loaded Map: " + loading.generators);
			//Manually reload oregen because BiomeSpec and DimensionSpec can be fussy

			JsonObject oregen = json.getObject("generators");

			if (oregen != null) {
				CottonResources.LOGGER.info("RELOADING " + oregen.size() + " entries");

				for (Map.Entry<String, JsonElement> entry : oregen.entrySet()) {
					if (entry.getValue() instanceof JsonObject) {
						OreGenerationSettings settings = OreGenerationSettings.deserialize((JsonObject) entry.getValue());
						loading.generators.put(entry.getKey(), settings);
					}
				}
			}

			CottonResources.LOGGER.info("RELOADED Map: " + loading.generators);

			return loading;
		} catch (IOException | SyntaxError e) {
			e.printStackTrace();
		}

		return new CottonResourcesConfig();
	}

	public static void saveConfig(CottonResourcesConfig config) {
		File file = new File(FabricLoader.getInstance().getConfigDirectory(), "CottonResources.json5");

		JsonElement json = JANKSON.toJson(config);

		try (FileOutputStream out = new FileOutputStream(file, false)) {
			out.write(json.toJson(JsonGrammar.JSON5).getBytes(StandardCharsets.UTF_8));
		} catch (IOException ex) {
			LOGGER.error("Could not write config", ex);
		}
	}

	@SafeVarargs
	public static <T> T[] mergeArrays(T[] a, T... b) {
		T[] result = Arrays.copyOf(a, a.length + b.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static Identifier common(String path) {
		return new Identifier(CottonResources.COMMON, path);
	}

	public static Identifier resources(String path) {
		return new Identifier(CottonResources.MODID, path);
	}
}
