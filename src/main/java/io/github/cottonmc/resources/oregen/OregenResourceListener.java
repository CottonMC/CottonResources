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

package io.github.cottonmc.resources.oregen;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.compat.REISafeCompat;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OregenResourceListener implements SimpleSynchronousResourceReloadListener {
	protected static final OreVoteConfig jsonConfig = new OreVoteConfig();

	@Override
	public void apply(ResourceManager resourceManager) {
		jsonConfig.generators.clear();
		jsonConfig.ores.clear();
		jsonConfig.replacements.clear();

		Jankson jankson = JanksonFactory.builder().build();

		Collection<Identifier> identifiers = resourceManager.findResources("oregen", (rsrc) -> rsrc.endsWith(".json") || rsrc.endsWith(".json5"));

		for (Identifier id : identifiers) {
			try {
				JsonObject configObject = jankson.load(resourceManager.getResource(id).getInputStream());
				OreVoteConfig configLocal = OreVoteConfig.deserialize(configObject);
				//Fold this config into the globally resolved one
				jsonConfig.ores.addAll(configLocal.ores);
				jsonConfig.generators.putAll(configLocal.generators);

				for (Map.Entry<String, HashMap<String, String>> entry : configLocal.replacements.entrySet()) {
					String resourceName = entry.getKey();
					HashMap<String, String> newReplacers = entry.getValue();
					HashMap<String, String> oldReplacers = jsonConfig.replacements.computeIfAbsent(resourceName, (it) -> new HashMap<>());
					oldReplacers.putAll(newReplacers);
				}
			} catch (IOException ex) {
				CottonResources.LOGGER.error(ex.getMessage(), ex);
			} catch (SyntaxError ex) {
				CottonResources.LOGGER.error(ex.getCompleteMessage());
			}
		}

		//Config overrides all this, so clobber anything that exists with the config:
		jsonConfig.generators.putAll(CottonResources.CONFIG.generators);
		jsonConfig.ores.addAll(CottonResources.CONFIG.enabledResources);
		jsonConfig.ores.removeAll(CottonResources.CONFIG.disabledResources);

		CottonResources.LOGGER.info("Final set of generator keys available: {}", jsonConfig.generators.keySet());
		CottonResources.LOGGER.info("Enabled generators: {}", jsonConfig.ores);
		CottonResources.LOGGER.info("Replacers defined for {} resources:", jsonConfig.replacements.size());
		/*for(Map.Entry<String, HashMap<String, String>> replacerBlockEntry : jsonConfig.replacements.entrySet()) {
			String resourceName = replacerBlockEntry.getKey();
			HashMap<String, String> replacements = replacerBlockEntry.getValue();
			CottonResources.LOGGER.info("    Replacers for {}: {}", resourceName, replacements);
			//CottonResources.LOGGER.info("    Replacers for "+resourceName+": "+replacements);
		}*/

		REISafeCompat.doObjectHiding.run();
	}

	@Override
	public Identifier getFabricId() {
		return CottonResources.resources("ore_voting");
	}

	public static OreVoteConfig getConfig() {
		return jsonConfig;
	}
}
