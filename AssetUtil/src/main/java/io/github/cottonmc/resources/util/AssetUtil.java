package io.github.cottonmc.resources.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
		//File outputsFolder = new File("outputs");
		
		File plansFolder = new File(inputsFolder, "plans");
		//File recipesFolder = new File(inputsFolder, "recipes");
		
		if (!plansFolder.exists()) {
			throw new RuntimeException(new NotEnoughJsonException("'inputs/plan' folder doesn't exist!"));
		}
		Jankson jankson = Jankson.builder().build();
		int totalGenerated = 0;
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
					
					
					
				} catch (IOException | SyntaxError e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("	"+f.getName()+": "+numFilesGenerated);
			totalGenerated += numFilesGenerated;
			numFilesGenerated = 0;
		}
		System.out.println(""+totalGenerated+" json files generated.");
	}
	
	public static void handlePlan(Jankson jankson, ResourcePlan plan) {
		try {
			//System.out.println("	[Item tags]");
			handleItemTags(jankson, plan.name, plan.items);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		try {
			//System.out.println("	[Recipes]");
			handleItemRecipes(jankson, plan.name, plan.items);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		try {
			//System.out.println("	[Block Tags]");
			handleBlockTags(jankson, plan.name, plan.blocks);
		} catch (NotEnoughJsonException ex) {
			ex.printStackTrace();
		}
		
		try {
			//System.out.println("	[Loot Tables]");
			handleLootTables(jankson, plan.name, plan.blocks);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static int numFilesGenerated = 0;
	
	
	public static void handleItemRecipes(Jankson jankson, String base, ResourcePlan.Items items) throws IOException {
		File recipesFolder = new File("./inputs", "recipes");
		if (!recipesFolder.exists()) throw new FileNotFoundException("Can't generate recipes: recipe folder doesn't exist");
		
		for(String s : items.recipes) {
			File templateFile = new File(recipesFolder, s+".json");
			if (templateFile.exists()) {
				String outputFileName = (templateFile.getName().startsWith("x_")) ? base + templateFile.getName().substring(1) : templateFile.getName();
				File output = new File("./outputs/recipes", outputFileName);
				File outputOuter = output.getParentFile(); if (!outputOuter.exists()) outputOuter.mkdirs();
				
				apply(templateFile, output, new BaseScope(base));
				numFilesGenerated++;
			} else {
				System.out.println("Skipping nonexistant recipe template file '"+templateFile.getName()+"' for resource '"+base+"'.");
			}
		}
	}
	
	
	public static void handleItemTags(Jankson jankson, String base, ResourcePlan.Items items) throws IOException {
		if (items.tags.isEmpty()) return; //successfully!
		
		File templateFile = new File("./inputs/tags", "item.json");
		if (!templateFile.exists()) throw new FileNotFoundException("Can't generate item tags: 'inputs/tags/item_tag.json' doesn't exist");
		
		//MustacheFactory factory = new DefaultMustacheFactory();
		
		//Mustache template = factory.compile(new InputStreamReader(new FileInputStream(templateFile), StandardCharsets.UTF_8), templateFile.getName());
	
		File itemTagsFolder = new File("./outputs/tags/items/");
		if (!itemTagsFolder.exists()) itemTagsFolder.mkdirs();
		
		for(String s : items.tags) {
			String item = base+"_"+s;
			//handledItems.add(item);
			File outputFile = new File("./outputs/tags/items/"+item+".json");
			apply(templateFile, outputFile, new ItemScope(base, item));
			//Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
			//template.execute(writer, new Scope(item)); //TODO: switch to base, item notation
			//writer.flush();
			numFilesGenerated++;
		}
	}
	
	public static void handleBlockTags(Jankson jankson, String base, ResourcePlan.Blocks blocks) throws NotEnoughJsonException {
		if (blocks.tags.isEmpty()) return; //successfully!
		
		try {
			File blockTagsFolder = new File("./outputs/tags/blocks/");
			if (!blockTagsFolder.exists()) blockTagsFolder.mkdirs();
			
			for(Map.Entry<String, String> entry : blocks.tags.entrySet()) {
				File templateFile = new File("./inputs/tags", entry.getValue()+".json");
				Mustache template = template(templateFile);
				
				String item = base+"_"+entry.getKey();
				//handledBlocks.add(item);
				File outputFile = new File("./outputs/tags/blocks/"+item+".json");
				Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
				template.execute(writer, new Scope(base, item));
				writer.flush();
				numFilesGenerated++;
			}
			
			if (blocks.item_tags.size()>0) {
				File itemTagsFolder = new File("./outputs/tags/items/");
				if (!itemTagsFolder.exists()) itemTagsFolder.mkdirs();
				
				for(Map.Entry<String, String> entry : blocks.item_tags.entrySet()) {
					String item = base+"_"+entry.getKey();
					File templateFile = new File("./inputs/tags", entry.getValue()+".json");
					Mustache template = template(templateFile);
					
					File outputFile = new File("./outputs/tags/items/"+item+".json");
					Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
					template.execute(writer, new Scope(base, item));
					writer.flush();
					numFilesGenerated++;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void handleLootTables(Jankson jankson, String base, ResourcePlan.Blocks blocks) throws IOException {
		if (blocks.loot_tables.isEmpty()) return;
		
		File lootTablesFolder = new File("./outputs/loot_tables/");
		if (!lootTablesFolder.exists()) lootTablesFolder.mkdirs();
		
		for(Map.Entry<String, String> entry : blocks.loot_tables.entrySet()) {
			String item = base+"_"+entry.getKey();
			
			File templateFile = new File("./inputs/loot_tables/"+entry.getValue()+".json");
			File outputFile = new File("./outputs/loot_tables/"+item+".json");
			apply(templateFile, outputFile, new Scope(base, item));
			numFilesGenerated++;
		}
	}
	
	private static final MustacheFactory FACTORY = new DefaultMustacheFactory();
	public static Mustache template(File f) throws NotEnoughJsonException {
		if (!f.exists()) throw new NotEnoughJsonException("Template file '"+f.getName()+"' doesn't exist");
		try {
			return FACTORY.compile(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8), f.getName());
		} catch (FileNotFoundException ex) {
			throw new NotEnoughJsonException("Error reading file", ex);
		}
	}
	
	public static void apply(Mustache template, File outputFile, Object scope) throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
		template.execute(writer, scope);
		writer.flush();
	}
	
	public static void apply(File input, File output, Object scope) throws IOException {
		if (!input.exists()) throw new FileNotFoundException("Template file '"+input.getName()+"' does not exist");
		if (!output.getParentFile().exists()) output.getParentFile().mkdirs();
		Mustache template = FACTORY.compile(new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8), input.getName());
		Writer writer = new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8);
		template.execute(writer, scope);
		writer.flush();
	}
	
	public static class BaseScope {
		public String base;
		public BaseScope(String base) { this.base = base; }
	}
	
	public static class ItemScope {
		public String base;
		public String item;
		public ItemScope(String base, String item) { this.base = base; this.item = item; }
	}
	
	public static class Scope {
		public String base;
		public String item;
		@Deprecated
		public String resource;
		
		public Scope(String resource) {
			this.base = resource;
			this.item = resource;
			this.resource = resource;
		}
		
		public Scope(String base, String item) {
			this.base = base;
			this.item = item;
			this.resource = base;
		}
	}
}
