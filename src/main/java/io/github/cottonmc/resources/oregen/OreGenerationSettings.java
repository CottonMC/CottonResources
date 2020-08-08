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

import com.google.common.base.MoreObjects;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import io.github.cottonmc.jankson.BlockAndItemSerializers;
import io.github.cottonmc.resources.CottonResources;

public class OreGenerationSettings {
	public Set<BlockState> ores = new HashSet<>();
	public int min_height = 6;
	public int max_height = 64;
	public DimensionSpec dimensions = new DimensionSpec();
	public BiomeSpec biomes = new BiomeSpec();
	public int cluster_count = 8;
	public int cluster_size = 8;

	public OreGenerationSettings() {
	}

	public OreGenerationSettings withOreBlock(String ore_block) {
		this.ores.add(Registry.BLOCK.get(new Identifier(ore_block)).getDefaultState());
		return this;
	}

	public OreGenerationSettings withMinHeight(int min_height) {
		this.min_height = min_height;
		return this;
	}

	public OreGenerationSettings withMaxHeight(int max_height) {
		this.max_height = max_height;
		return this;
	}

	public OreGenerationSettings excludeDimension(String dimension) {
		this.biomes.deny.add(new Identifier(dimension));
		return this;
	}

	public OreGenerationSettings withClusterCount(int cluster_count) {
		this.cluster_count = cluster_count;
		return this;
	}

	public OreGenerationSettings withClusterSize(int cluster_size) {
		this.cluster_size = cluster_size;
		return this;
	}

	public static OreGenerationSettings getDefault() {
		OreGenerationSettings settings = new OreGenerationSettings();
		settings.excludeDimension("minecraft:the_nether");
		settings.excludeDimension("minecraft:the_end");
		return settings;
	}

	//Handle some of the especially-vague polymorphism of DimensionSpec and BiomeSpec
	public static OreGenerationSettings deserialize(JsonObject obj) {
		OreGenerationSettings result = new OreGenerationSettings();
		JsonElement blockElem = obj.get("ores");
		result.ores.addAll(deserializeBlockState(blockElem));

		result.min_height = getIntOrDefault(obj, "min_height", result.min_height);
		result.max_height = getIntOrDefault(obj, "max_height", result.max_height);
		result.cluster_count = getIntOrDefault(obj, "cluster_count", result.cluster_count);
		result.cluster_size = getIntOrDefault(obj, "cluster_size", result.cluster_size);

		JsonElement dimensionsElem = obj.get("dimensions");

		if (dimensionsElem != null) result.dimensions = DimensionSpec.deserialize(dimensionsElem);

		JsonElement biomesElem = obj.get("biomes");

		if (biomesElem != null) result.biomes = BiomeSpec.deserialize(biomesElem);

		return result;
	}

	public static Set<BlockState> deserializeBlockState(JsonElement elem) {
		if (elem == null) return ImmutableSet.of();

		if (elem instanceof JsonPrimitive) {
			BlockState state = BlockAndItemSerializers.getBlockStatePrimitive(((JsonPrimitive) elem).asString(), CottonResources.JANKSON.getMarshaller());

			if (state == null) CottonResources.LOGGER.error("State not found for " + ((JsonPrimitive) elem).getValue());

			return (state == null) ? ImmutableSet.of() : ImmutableSet.of(state);
		} else if (elem instanceof JsonObject) {
			BlockState state = BlockAndItemSerializers.getBlockState((JsonObject) elem, CottonResources.JANKSON.getMarshaller());

			if (state == null) CottonResources.LOGGER.error("State not found for " + ((JsonPrimitive) elem).getValue());

			return (state == null) ? ImmutableSet.of() : ImmutableSet.of(state);
		} else if (elem instanceof JsonArray) {
			HashSet<BlockState> result = new HashSet<>();

			for (JsonElement e : (JsonArray) elem) {
				result.addAll(deserializeBlockState(e));
			}

			return result;
		} else { //JsonNull, etc
			return ImmutableSet.of();
		}
	}

	public static int getIntOrDefault(JsonObject obj, String key, int defaultValue) {
		Integer val = obj.get(Integer.class, key);
		return (val == null) ? defaultValue : val;
	}

	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("min_height", min_height)
				.add("max_height", max_height)
				.add("cluster_size", cluster_size)
				.add("cluster_count", cluster_count)
				.add("dimensionspec", dimensions)
				.add("biomes", biomes)
				.toString();
	}
}
