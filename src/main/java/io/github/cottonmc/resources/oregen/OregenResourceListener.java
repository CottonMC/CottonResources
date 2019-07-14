package io.github.cottonmc.resources.oregen;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.cottonmc.resources.CottonResources;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Collection;

public class OregenResourceListener implements SynchronousResourceReloadListener, IdentifiableResourceReloadListener {
	protected static final OreVoteConfig jsonConfig = new OreVoteConfig();
	
	@Override
	public void apply(ResourceManager resourceManager) {
		jsonConfig.generators.clear();
		jsonConfig.ores.clear();
		
		Jankson jankson = JanksonFactory.builder()
				.registerTypeAdapter(OreGenerationSettings.class, OreGenerationSettings::deserialize)
				.build();
		
		
		
		Collection<Identifier> identifiers = resourceManager.findResources("oregen", (rsrc) -> rsrc.endsWith(".json") || rsrc.endsWith(".json5"));
		
		for (Identifier id : identifiers) {
			
			try {
				Resource rsrc = resourceManager.getResource(id);
				
				
				
				JsonObject configObject = jankson.load(resourceManager.getResource(id).getInputStream());
				configObject.setMarshaller(jankson.getMarshaller()); //maybe fix?
				System.out.println(configObject);
				OreVoteConfig configLocal = 
						OreVoteConfig.deserialize(configObject);
						//jankson.fromJson(configObject, OreVoteConfig.class);
				//Fold this config into the globally resolved one
				jsonConfig.ores.addAll(configLocal.ores);
				
				System.out.println("Loading generators for "+configLocal.generators.keySet()+" provided by "+rsrc.getResourcePackName());
				System.out.println(jankson.toJson(configLocal));
				jsonConfig.generators.putAll(configLocal.generators);
				
			} catch (IOException ex) {
				CottonResources.LOGGER.error(ex.getMessage(), ex);
			} catch (SyntaxError ex) {
				CottonResources.LOGGER.error(ex.getCompleteMessage());
			}
		}
		
		System.out.println("Final set of generator keys available: "+jsonConfig.generators.keySet());
		System.out.println("Enabled generators: "+jsonConfig.ores);
	}

	@Override
	public Identifier getFabricId() {
		return new Identifier(CottonResources.MODID, "ore_voting");
	}
	
	public static OreVoteConfig getConfig() {
		return jsonConfig;
	}
}
