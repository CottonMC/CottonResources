package io.github.cottonmc.resources.oregen;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import blue.endless.jankson.JsonElement;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

public class DimensionSpec extends TaggableSpec<Dimension> {
	//TODO: Dimension tag registry
	
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
	
	public static Set<Identifier> resolveTag(Identifier id) {
		return ImmutableSet.of();
	}
}
