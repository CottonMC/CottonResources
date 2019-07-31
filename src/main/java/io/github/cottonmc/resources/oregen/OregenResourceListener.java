package io.github.cottonmc.resources.oregen;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.compat.REISafeCompat;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Collection;

public class OregenResourceListener implements SimpleSynchronousResourceReloadListener{
	protected static final OreVoteConfig jsonConfig = new OreVoteConfig();
	
	@Override
	public void apply(ResourceManager resourceManager) {
		jsonConfig.generators.clear();
		jsonConfig.ores.clear();
		jsonConfig.replacements.clear();
		
		Jankson jankson = JanksonFactory.builder().build();
		
		Collection<Identifier> identifiers = resourceManager.findResources("oregen", (rsrc) -> rsrc.endsWith(".json") || rsrc.endsWith(".json5"));
		
		for (Identifier id : identifiers) {
			
			try {
				JsonObject configObject = jankson.load(resourceManager.getResource(id).getInputStream());
				OreVoteConfig configLocal = OreVoteConfig.deserialize(configObject);
				//Fold this config into the globally resolved one
				jsonConfig.ores.addAll(configLocal.ores);
				jsonConfig.ores.removeAll(CottonResources.CONFIG.disabledResources);
				jsonConfig.generators.putAll(configLocal.generators);
				
				
			} catch (IOException ex) {
				CottonResources.LOGGER.error(ex.getMessage(), ex);
			} catch (SyntaxError ex) {
				CottonResources.LOGGER.error(ex.getCompleteMessage());
			}
		}
		
		CottonResources.LOGGER.info("Final set of generator keys available: %s", jsonConfig.generators.keySet());
		CottonResources.LOGGER.info("Enabled generators: %s", jsonConfig.ores);
		
		REISafeCompat.doObjectHiding.run();
	}

	@Override
	public Identifier getFabricId() {
		return new Identifier(CottonResources.MODID, "ore_voting");
	}
	
	public static OreVoteConfig getConfig() {
		return jsonConfig;
	}
}
