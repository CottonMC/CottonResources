package io.github.cottonmc.resources.config;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import io.github.cottonmc.resources.oregen.BiomeSpec;
import io.github.cottonmc.resources.oregen.DimensionSpec;

public class OreGenerationSettings {
	public BlockState ore_block = Blocks.LIGHT_BLUE_WOOL.getDefaultState();
	public int min_height = 6;
	public int max_height = 64;
	public DimensionSpec dimensions = new DimensionSpec();
	public BiomeSpec biomes = new BiomeSpec();
	public int cluster_count = 8;
	public int cluster_size = 8;
	
	public OreGenerationSettings() {
	}
	
	public OreGenerationSettings withOreBlock(String ore_block) {
		this.ore_block = Registry.BLOCK.get(new Identifier(ore_block)).getDefaultState();
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
		//this.biomes.deny.add(new Identifier(dimension));
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
		System.out.println("deserialize called");
		
		OreGenerationSettings result = new OreGenerationSettings();
		result.ore_block = obj.get(BlockState.class, "block");
		
		result.min_height = getIntOrDefault(obj, "min_height", result.min_height);
		result.max_height = getIntOrDefault(obj, "max_height", result.max_height);
		result.cluster_count = getIntOrDefault(obj, "cluster_count", result.cluster_count);
		result.cluster_size = getIntOrDefault(obj, "cluster_size", result.cluster_size);
		
		JsonElement dimensionsElem = obj.get("dimensions");
		System.out.println("Deserializing dimensionSpec....");
		if (dimensionsElem!=null) result.dimensions =
			DimensionSpec.deserialize(dimensionsElem);
		
		JsonElement biomesElem = obj.get("biomes");
		if (biomesElem!=null) result.biomes =
			BiomeSpec.deserialize(biomesElem);
		
		return result;
	}
	
	public static int getIntOrDefault(JsonObject obj, String key, int defaultValue) {
		Integer val = obj.get(Integer.class, key);
		return (val==null) ? defaultValue : val;
	}
}
