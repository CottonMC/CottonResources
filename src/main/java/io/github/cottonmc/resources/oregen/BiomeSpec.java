package io.github.cottonmc.resources.oregen;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import blue.endless.jankson.JsonElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomeSpec extends TaggableSpec<Biome> {
	//TODO: Biome tag registry
	
	public BiomeSpec allowTag(Identifier tag) {
		allow.addAll(resolveTag(tag));
		return this;	
	}
	
	public BiomeSpec denyTag(Identifier tag) {
		deny.addAll(resolveTag(tag));
		return this;
	}
	
	@Override
	public boolean test(Biome biome) {
		Identifier id = Registry.BIOME.getId(biome); //This is barely acceptable because BIOME is a SimpleRegistry and not a DefaultedRegistry.
		
		if (deny.contains(id)) return false; //nulls always pass this deny filter
		if (allow.isEmpty() || allow.contains(ANY)) return true; //allow:'*' should also cover biomes with null id's
		return allow.contains(id); //nulls will always fail this allow filter
	}
	
	/** Can't be directly used as a TypeAdapter because of array polymorphism, but can be used by a parent TypeAdapter */
	public static BiomeSpec deserialize(JsonElement elem) {
		return TaggableSpec.deserialize(new BiomeSpec(), elem, BiomeSpec::resolveTag);
	}
	
	public static Set<Identifier> resolveTag(Identifier tag) {
		return ImmutableSet.of();
	}
}
