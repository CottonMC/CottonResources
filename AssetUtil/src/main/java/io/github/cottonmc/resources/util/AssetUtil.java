package io.github.cottonmc.resources.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

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
					
					System.out.println(""+numFilesGenerated+" json files generated.");
					
				} catch (IOException | SyntaxError e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void handlePlan(Jankson jankson, ResourcePlan plan) {
		try {
			handleItemTags(jankson, plan.name, plan.items);
		} catch (NotEnoughJsonException ex) {
			ex.printStackTrace();
		}
		
		try {
			handleItemRecipes(jankson, plan.name, plan.items);
		} catch (NotEnoughJsonException ex) {
			ex.printStackTrace();
		}
		
		try {
			handleBlockTags(jankson, plan.name, plan.blocks);
		} catch (NotEnoughJsonException ex) {
			ex.printStackTrace();
		}
	}
	
	//public static ArrayList<String> handledItems = new ArrayList<>();
	//public static ArrayList<String> handledBlocks = new ArrayList<>();
	public static int numFilesGenerated = 0;
	
	
	public static void handleItemRecipes(Jankson jankson, String base, ResourcePlan.Items items) throws NotEnoughJsonException {
		File recipesFolder = new File("./inputs", "recipes");
		if (!recipesFolder.exists()) throw new NotEnoughJsonException("Can't generate recipes: recipe folder doesn't exist");
		
		MustacheFactory mustache = new DefaultMustacheFactory();
		
		for(String s : items.recipes) {
			File templateFile = new File(recipesFolder, s+".json");
			if (templateFile.exists()) {
				try {
					Mustache template = mustache.compile(new InputStreamReader(new FileInputStream(templateFile), StandardCharsets.UTF_8), s);
					
					String outputFileName = base + templateFile.getName().substring(1);
					File output = new File("./outputs/recipes", outputFileName);
					Writer writer = new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8);
					template.execute(writer, new Scope(base));
					writer.flush();
					numFilesGenerated++;
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				new NotEnoughJsonException("Can't generate recipes for nonexistant template '"+s+"'.").printStackTrace();
			}
			
		}
	}
	
	public static void handleItemTags(Jankson jankson, String base, ResourcePlan.Items items) throws NotEnoughJsonException {
		if (items.tags.isEmpty()) return; //successfully!
		
		File templateFile = new File("./inputs/tags", "item.json");
		if (!templateFile.exists()) throw new NotEnoughJsonException("Can't generate item tags:	'inputs/tags/item_tag.json' doesn't exist");
		
		MustacheFactory factory = new DefaultMustacheFactory();
		try {
			Mustache template = factory.compile(new InputStreamReader(new FileInputStream(templateFile), StandardCharsets.UTF_8), templateFile.getName());
		
			File itemTagsFolder = new File("./outputs/tags/items/");
			if (!itemTagsFolder.exists()) itemTagsFolder.mkdirs();
			
			for(String s : items.tags) {
				String item = base+"_"+s;
				//handledItems.add(item);
				File outputFile = new File("./outputs/tags/items/"+item+".json");
				Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
				template.execute(writer, new Scope(item));
				writer.flush();
				numFilesGenerated++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void handleBlockTags(Jankson jankson, String base, ResourcePlan.Blocks blocks) {
		if (blocks.tags.isEmpty()) return; //successfully!
		
		MustacheFactory factory = new DefaultMustacheFactory();
		try {
			File blockTagsFolder = new File("./outputs/tags/blocks/");
			if (!blockTagsFolder.exists()) blockTagsFolder.mkdirs();
			
			for(Map.Entry<String, String> entry : blocks.tags.entrySet()) {
				File templateFile = new File("./inputs/tags", entry.getKey()+".json");
				if (!templateFile.exists()) throw new NotEnoughJsonException("Can't generate block tag: '"+entry.getKey()+".json' doesn't exist");
				Mustache template = factory.compile(new InputStreamReader(new FileInputStream(templateFile), StandardCharsets.UTF_8), templateFile.getName());
				
				String item = base+"_"+entry.getValue();
				//handledBlocks.add(item);
				File outputFile = new File("./outputs/tags/blocks/"+item+".json");
				Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
				template.execute(writer, new Scope(base));
				writer.flush();
				numFilesGenerated++;
			}
			
			if (blocks.item_tags.size()>0) {
				File itemTagsFolder = new File("./outputs/tags/items/");
				if (!itemTagsFolder.exists()) itemTagsFolder.mkdirs();
				
				for(Map.Entry<String, String> entry : blocks.item_tags.entrySet()) {
					String item = base+"_"+entry.getValue();
					File templateFile = new File("./inputs/tags", entry.getKey()+".json");
					if (!templateFile.exists()) throw new NotEnoughJsonException("Can't generate item tags:	'inputs/tags/"+entry.getKey()+".json' doesn't exist");
					Mustache template = factory.compile(new InputStreamReader(new FileInputStream(templateFile), StandardCharsets.UTF_8), templateFile.getName());
					
					File outputFile = new File("./outputs/tags/items/"+item+".json");
					Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
					template.execute(writer, new Scope(base));
					writer.flush();
					numFilesGenerated++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class Scope {
		public String resource;
		
		public Scope(String resource) {
			this.resource = resource;
		}
	}
}
