package io.github.cottonmc.resources.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.impl.SyntaxError;

public class AssetUtil {
	public static void main(String... args) {
		//Load plans
		File inputsFolder = new File("inputs");
		File outputsFolder = new File("outputs");
		
		File plansFolder = new File(inputsFolder, "plans");
		File recipesFolder = new File(inputsFolder, "recipes");
		
		if (!plansFolder.exists()) {
			throw new NotEnoughJsonException("'inputs/plan' folder doesn't exist!");
		}
		Jankson jankson = Jankson.builder().build();
		
		for(File f : plansFolder.listFiles()) {
			String filename = f.getName();
			if (filename.endsWith(".json") || filename.endsWith(".json5")) {
				try {
					JsonObject obj = jankson.load(f);
					
					JsonObject defaults = obj.get(JsonObject.class, "default");
					if (defaults==null) throw new SyntaxError("File '"+filename+"' is missing required key 'default'.");
					
					JsonArray generate = obj.get(JsonArray.class, "generate");
					if (generate==null) throw new SyntaxError("File '"+filename+"' is missing required key 'generate'.");
					
					ResourcePlan defaultPlan = ResourcePlan.fromJson(defaults);
					
					for(JsonElement generateElem : generate) {
						if (generateElem instanceof JsonPrimitive) {
							ResourcePlan plan = defaultPlan.clone();
							plan.name = ((JsonPrimitive) generateElem).asString();
							handlePlan(jankson, plan);
							//System.out.println("Generated configured ResourcePlan "+jankson.toJson(plan).toJson(JsonGrammar.JSON5));
						} else {
							//TODO: Apply the generateElem object on top of the defaults somehow
						}
					}
					
					System.out.println("Items handled: "+handledItems);
					
				} catch (IOException | SyntaxError e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void handlePlan(Jankson jankson, ResourcePlan plan) {
		handleItems(jankson, plan.name, plan.items);
	}
	
	public static ArrayList<String> handledItems = new ArrayList<>();
	
	public static void handleItems(Jankson jankson, String base, ResourcePlan.Items items) {
		for(String s : items.affixes) {
			String item = base+"_"+s;
			handledItems.add(item);
			
			if (items.tags.contains(s)) {
				//TODO: Generate a tag for this item.
			}
		}
	}
}
