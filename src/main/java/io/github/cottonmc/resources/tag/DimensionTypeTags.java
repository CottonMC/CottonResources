package io.github.cottonmc.resources.tag;

import java.util.Optional;

import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagContainer;
import net.minecraft.world.dimension.DimensionType;

/**
 * Now you can tag dimensions!
 */
public class DimensionTypeTags {
	public static TagContainer<DimensionType> CONTAINER = new TagContainer<>((identifier)->{
		return Optional.empty();
	}, "", false, "");

	public static TagContainer<DimensionType> getContainer() { return CONTAINER; }
	static void setContainer(TagContainer<DimensionType> container) { CONTAINER = container; }
}
