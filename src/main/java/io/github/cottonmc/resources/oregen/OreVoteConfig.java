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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;

public class OreVoteConfig {
	public final Set<String> ores = new HashSet<String>();

	public final HashMap<String, OreGenerationSettings> generators = new HashMap<>();

	public final HashMap<String, HashMap<String, String>> replacements = new HashMap<>();

	public static OreVoteConfig deserialize(JsonObject obj) {
		OreVoteConfig result = new OreVoteConfig();

		JsonArray oresArray = obj.get(JsonArray.class, "ores");

		if (oresArray != null) {
			for (JsonElement elem : oresArray) {
				if (elem instanceof JsonPrimitive) result.ores.add(((JsonPrimitive) elem).asString());
			}
		}

		JsonObject generatorsObj = obj.getObject("generators");

		if (generatorsObj != null) {
			for (Map.Entry<String, JsonElement> entry : generatorsObj.entrySet()) {
				if (entry.getValue() instanceof JsonObject) {
					OreGenerationSettings generator = OreGenerationSettings.deserialize((JsonObject) entry.getValue());
					result.generators.put(entry.getKey(), generator);
				}
			}
		}

		JsonObject replacementsObj = obj.getObject("replacements");

		if (replacementsObj != null) {
			for (Map.Entry<String, JsonElement> entry : replacementsObj.entrySet()) {
				String resourceName = entry.getKey();

				if (entry.getValue() instanceof JsonObject) {
					JsonObject resourceObj = (JsonObject) entry.getValue();

					for (Map.Entry<String, JsonElement> resourceReplacement : resourceObj.entrySet()) {
						if (resourceReplacement.getValue() instanceof JsonPrimitive) {
							String replacementValue = ((JsonPrimitive) resourceReplacement.getValue()).asString();
							String replacementKey = resourceReplacement.getKey();

							HashMap<String, String> repl = result.replacements.computeIfAbsent(resourceName, (it) -> new HashMap<>());
							repl.put(replacementKey, replacementValue);
						}
					}
				}
			}
		}

		return result;
	}
}
