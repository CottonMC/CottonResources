package io.github.cottonmc.resources.config;

import io.github.cottonmc.resources.oregen.OreGenerationSettings;
import blue.endless.jankson.Comment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CottonResourcesConfig {
	@Comment("If true, vanilla's ore gen will be cancelled.")
	public boolean override_vanilla_generation = false;
	
	@Comment("Listing a resource here forces it not to generate in the world, even if a mod requests it.")
	public Set<String> disabledResources = new HashSet<>();
	
	@Comment("Additional settings for ore generators. Identical to the datapack json")
	public HashMap<String, OreGenerationSettings> generators = new HashMap<>();
}