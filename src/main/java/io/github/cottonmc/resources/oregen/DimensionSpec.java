package io.github.cottonmc.resources.oregen;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import blue.endless.jankson.JsonElement;
import io.github.cottonmc.resources.tag.DimensionTypeTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

public class DimensionSpec extends TaggableSpec<Dimension> {
	
	public DimensionSpec allowTag(Identifier tag) {
		allow.addAll(resolveTag(tag));
		return this;	
	}
	
	public DimensionSpec denyTag(Identifier tag) {
		deny.addAll(resolveTag(tag));
		return this;
	}
	
	@Override
	public boolean test(Dimension dim) {
		Identifier id = DimensionType.getId(dim.getType());
		
		if (deny.contains(id)) return false; //nulls (unregistered dims) will always pass this test
		if (allow.isEmpty() || allow.contains(ANY)) return true; //'*' will accept nulls
		return allow.contains(id); //null-id dimensions will always fail this check
	}
	
	/** Can't be directly used as a TypeAdapter because of array polymorphism, but can be used by a parent TypeAdapter */
	public static DimensionSpec deserialize(JsonElement elem) {
		return TaggableSpec.deserialize(new DimensionSpec(), elem, DimensionSpec::resolveTag);
	}
	
	public static Set<Identifier> resolveTag(Identifier tagName) {
		TagContainer<DimensionType> tagContainer = DimensionTypeTags.getContainer();
		Tag<DimensionType> tag = tagContainer.get(tagName);
		if (tag==null) return ImmutableSet.of();
		
		HashSet<Identifier> result = new HashSet<>();
		for(DimensionType dimension : tag.values()) {
			Identifier id = Registry.DIMENSION_TYPE.getId(dimension);
			if (id!=null) result.add(id);
		}
		return result;
	}
}
