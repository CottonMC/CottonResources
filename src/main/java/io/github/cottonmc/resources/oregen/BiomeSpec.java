package io.github.cottonmc.resources.oregen;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomeSpec implements Predicate<Biome> {
	public static final Identifier ANY = new Identifier("*"); //technically "minecraft:*"
	
	public final Set<Identifier> allow = new HashSet<>();
	public final Set<Identifier> deny = new HashSet<>();
	
	@Override
	public boolean test(Biome biome) {
		Identifier id = Registry.BIOME.getId(biome); //This is barely acceptable because BIOME is a SimpleRegistry and not a DefaultedRegistry.
		
		if (deny.contains(id)) return false; //nulls always pass this deny filter
		if (allow.isEmpty() || allow.contains(ANY)) return true; //allow:'*' should also cover biomes with null id's
		return allow.contains(id); //nulls will always fail this allow filter
	}
	
	
	/* BiomeSpec Schema
	 * 
	 * %IDENTIFIER_STRING => JsonString e.g. "minecraft:ocean"
	 * %TAG_OBJECT => { "tag": %IDENTIFIER_STRING }
	 * %BIOME_LINE => %TAG_OBJECT | %IDENTIFIER_STRING
	 * %BIOME_SET => [ %BIOME_LINE (, ...) ]
	 * %NOT => { "not" : %BIOME_SET }
	 * %BIOME_SPEC => %IDENTIFIER_STRING | %TAG_OBJECT | %NOT | %BIOME_SET
	 */
	
	/** Can't be directly used as a TypeAdapter because of array polymorphism, but can be used by a parent TypeAdapter */
	public static BiomeSpec deserialize(JsonElement elem) {
		BiomeSpec result = new BiomeSpec();
		
		if (elem instanceof JsonPrimitive) {
			result.allow.addAll(parseSpecLine(elem));
		} else if (elem instanceof JsonArray) {
			//Normal list of spec lines
			for(JsonElement e : (JsonArray)elem) {
				result.allow.addAll(parseSpecLine(e));
			}
		} else if (elem instanceof JsonObject) {
			//only really for "not"
			JsonElement not = ((JsonObject)elem).get("not");
			if (not!=null) {
				parseNot(not, result);
			} else {
				JsonElement tag = ((JsonObject)elem).get("tag");
				if (tag!=null) result.allow.addAll(parseSpecLine(elem));
			}
		}
		
		return result;
	}
	
	public static void parseNot(JsonElement not, BiomeSpec spec) {
		if (not instanceof JsonPrimitive || not instanceof JsonObject) {
			spec.deny.addAll(parseSpecLine(not));
		} else if (not instanceof JsonArray) {
			for(JsonElement elem : (JsonArray)not) {
				spec.deny.addAll(parseSpecLine(elem));
			}
			
		}
	}
	
	public static Set<Identifier> parseSpecLine(JsonElement line) {
		if (line instanceof JsonPrimitive) {
			return ImmutableSet.of(new Identifier(line.toString()));
		} else if (line instanceof JsonObject) {
			JsonElement tag = ((JsonObject)line).get("tag");
			if (tag!=null && tag instanceof JsonPrimitive) {
				Identifier tagId = new Identifier(((JsonPrimitive)tag).asString());
				//TODO: lookup Identifiers from tags
				
			}
		}
		
		return ImmutableSet.of();
	}
}
