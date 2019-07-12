package io.github.cottonmc.resources.tag;

import java.util.Optional;

import net.minecraft.tag.TagContainer;
import net.minecraft.world.biome.Biome;

/**
 * Now you can tag Biomes!
 */
public class BiomeTags {
	public static TagContainer<Biome> CONTAINER = new TagContainer<>((identifier)->{
		return Optional.empty();
	}, "", false, "");
	
	public static TagContainer<Biome> getContainer() { return CONTAINER; }
	static void setContainer(TagContainer<Biome> container) { CONTAINER = container; }
}
