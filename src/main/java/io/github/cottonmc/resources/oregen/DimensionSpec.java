package io.github.cottonmc.resources.oregen;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

public class DimensionSpec implements Predicate<Dimension> {
	//TODO: Dimension tag registry
	
	public static final Identifier ANY = new Identifier("*"); //technically "minecraft:*"
	
	public final Set<Identifier> allow = new HashSet<>();
	public final Set<Identifier> deny = new HashSet<>();
	
	public DimensionSpec allow(Identifier id) {
		allow.add(id);
		return this;
	}
	
	public DimensionSpec allow(String id) {
		allow.add(new Identifier(id));
		return this;
	}
	
	public DimensionSpec deny(Identifier id) {
		deny.add(id);
		return this;
	}
	
	public DimensionSpec deny(String id) {
		deny.add(new Identifier(id));
		return this;
	}
	
	//public DimensionSpec allowTag(Identifier tag) {
	//	return this;	
	//}
	
	//public DimensionSpec denyTag(Identifier tag) {
	//	return this;
	//}
	
	@Override
	public boolean test(Dimension dim) {
		Identifier id = DimensionType.getId(dim.getType());
		if (deny.contains(id)) return false; //nulls (unregistered dims) will always pass this test
		if (allow.isEmpty() || allow.contains(ANY)) return true; //'*' will accept nulls
		return allow.contains(id); //null-id dimensions will always fail this check
	}
}
