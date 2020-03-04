package io.github.cottonmc.resources.oregen;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import io.github.cottonmc.resources.CottonResources;
import net.minecraft.util.Identifier;

/**
 * A concise schema for json allow/deny semantics for anything that has an Identifier and can be held in a Tag
 */
public abstract class TaggableSpec<T> implements Predicate<T> {
	public static final Identifier ANY = new Identifier("any");
	
	public final Set<Identifier> allow = new HashSet<>();
	public final Set<Identifier> deny = new HashSet<>();
	
	public TaggableSpec<T> allow(Identifier id) {
		allow.add(id);
		return this;
	}
	
	public TaggableSpec<T> allow(String id) {
		allow.add(new Identifier(id));
		return this;
	}
	
	public TaggableSpec<T> deny(Identifier id) {
		deny.add(id);
		return this;
	}
	
	public TaggableSpec<T> deny(String id) {
		deny.add(new Identifier(id));
		return this;
	}
	
	/* TaggableSpec Schema
	 * 
	 * %IDENTIFIER_STRING => JsonString e.g. "minecraft:the_end" or just "the_end"
	 * %TAG_STRING => JsonString e.g. "#minecraft:overworlds" or just "#overworlds"
	 * %TAG_OBJECT => { "tag": %IDENTIFIER_STRING }
	 * %ITEM => %TAG_STRING | %TAG_OBJECT | %IDENTIFIER_STRING
	 * %SET => [ %ITEM (, ...) ]
	 * %NOT => { "not" : %SET }
	 * %SPEC => %ITEM | %NOT | %SET
	 */
	
	public static <U extends TaggableSpec<?>> U deserialize(U result, JsonElement elem, Function<Identifier, Set<Identifier>> tagResolver) {
		if (elem instanceof JsonPrimitive) {
			result.allow.addAll(parseItem(elem, tagResolver));
		} else if (elem instanceof JsonArray) {
			//Normal list of spec lines
			for(JsonElement e : (JsonArray)elem) {
				result.allow.addAll(parseItem(e, tagResolver));
			}
		} else if (elem instanceof JsonObject) {
			JsonElement not = ((JsonObject)elem).get("not");
			if (not!=null) {
				parseNot(not, result, tagResolver);
			} else {
				JsonElement tag = ((JsonObject)elem).get("tag");
				if (tag!=null) result.allow.addAll(parseItem(elem, tagResolver));
			}
		}
		
		return result;
	}
	
	public static JsonElement serialize(TaggableSpec<?> spec) {
		if (spec.deny.isEmpty()) {
			//This can just be a JsonArray of allowed elements
			JsonArray result = new JsonArray();
			for(Identifier id : spec.allow) {
				result.add(new JsonPrimitive(id.toString()));
			}
			return result;
		} else if (spec.allow.isEmpty()) {
			JsonObject result = new JsonObject();
			JsonArray arr = new JsonArray();
			result.put("not", arr);
			for(Identifier id : spec.deny) {
				arr.add(new JsonPrimitive(id.toString()));
			}
			return result;
		} else {
			CottonResources.LOGGER.error("Failed to serialize a complex TaggableSpec!");
			return new JsonObject();
		}
		
	}
	
	public static <U extends TaggableSpec<?>> void parseNot(JsonElement not, U spec, Function<Identifier, Set<Identifier>> tagResolver) {
		if (not instanceof JsonPrimitive || not instanceof JsonObject) {
			spec.deny.addAll(parseItem(not, tagResolver));
		} else if (not instanceof JsonArray) {
			for(JsonElement elem : (JsonArray)not) {
				spec.deny.addAll(parseItem(elem, tagResolver));
			}
			
		}
	}
	
	public static Set<Identifier> parseItem(JsonElement line, Function<Identifier, Set<Identifier>> tagResolver) {
		if (line instanceof JsonPrimitive) {
			String s = ((JsonPrimitive) line).asString();
			if (s.startsWith("#")) {
				return tagResolver.apply(new Identifier(s.substring(1)));
			} else {
				if (s.equals("*")) return ImmutableSet.of(ANY);
				return ImmutableSet.of(new Identifier(s));
			}
		} else if (line instanceof JsonObject) {
			JsonElement tag = ((JsonObject)line).get("tag");
			if (tag!=null && tag instanceof JsonPrimitive) {
				Identifier tagId = new Identifier(((JsonPrimitive)tag).asString());
				return tagResolver.apply(tagId);
			}
		}
		
		return ImmutableSet.of();
	}


	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("Allowed", allow)
			.add("Disallowed", deny)
			.toString();
	}
}
