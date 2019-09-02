package io.github.cottonmc.resources.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Nullable;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;

public class ResourcePlan {
	@Nullable
	public String name = null;
	
	public Items items = new Items();
	public Blocks blocks = new Blocks();
	
	@Override
	public ResourcePlan clone() {
		ResourcePlan result = new ResourcePlan();
		result.items = items.clone();
		result.blocks = blocks.clone();
		
		return result;
	}
	
	public static ResourcePlan fromJson(JsonElement json) {
		if (json instanceof JsonObject) return fromJson((JsonObject)json);
		
		ResourcePlan result = new ResourcePlan();
		if (json instanceof JsonPrimitive) result.name = ((JsonPrimitive)json).asString();
		return result;
	}
	
	public static ResourcePlan fromJson(JsonObject json) {
		ResourcePlan result = new ResourcePlan();
		
		JsonObject itemsElem = json.get(JsonObject.class, "items");
		if (itemsElem!=null) result.items = Items.fromJson(itemsElem);
		
		JsonObject blocksElem = json.get(JsonObject.class, "blocks");
		if (blocksElem!=null) result.blocks = Blocks.fromJson(blocksElem);
		
		return result;
	}
	
	
	public static class Items {
		public ArrayList<String> affixes = new ArrayList<>();
		
		/** If true, generate a tag for each item */
		public ArrayList<String> tags = new ArrayList<>();
		
		/**
		 * A list of template names to use for generating recipes with each base+affix combination
		 */
		public ArrayList<String> recipes = new ArrayList<>();
		
		@Override
		public Items clone() {
			Items result = new Items();
			result.affixes.addAll(affixes);
			result.tags.addAll(tags);
			result.recipes.addAll(recipes);
			return result;
		}
		
		public static Items fromJson(JsonObject json) {
			Items result = new Items();
			String[] affixes = json.get(String[].class, "affixes");
			if (affixes==null) return result; //Nothing to see here.
			result.affixes.addAll(Arrays.asList(affixes));
			
			JsonElement tagsElem = json.get("tags");
			if (tagsElem!=null) {
				if (tagsElem instanceof JsonArray) {
					for(JsonElement tagElem : (JsonArray)tagsElem) {
						if (tagElem instanceof JsonPrimitive) result.affixes.add(((JsonPrimitive) tagElem).asString());
					}
				} else if ((tagsElem instanceof JsonPrimitive) && (((JsonPrimitive)tagsElem).getValue() instanceof Boolean)) {
					Boolean b =  (Boolean) (((JsonPrimitive)tagsElem).getValue());
					if (b!=null && b.booleanValue()) result.tags.addAll(result.affixes);
				}
			}
			
			JsonElement recipesElem = json.get("recipes");
			if (recipesElem!=null && recipesElem instanceof JsonArray) {
				for(JsonElement recipeElem : (JsonArray)recipesElem) {
					if (recipeElem instanceof JsonPrimitive) {
						result.recipes.add(((JsonPrimitive)recipeElem).asString());
					}
				}
			}
			
			return result;
		}
	}
	
	public static class Blocks implements Cloneable {
		
		public ArrayList<String> affixes = new ArrayList<>();
		
		/** A list of affixes to generate tags for */
		public ArrayList<String> tags = new ArrayList<>();
		
		/** Map of block affixes to model template names */
		public HashMap<String, String> models = new HashMap<>();
		
		public boolean item_models = true;
		public boolean loot_tables = false; //TODO: Schema
		
		public Blocks clone() {
			try {
				return (Blocks) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
		
		public static Blocks fromJson(JsonObject json) {
			Blocks result = new Blocks();
			
			return result;
		}
	}
}
