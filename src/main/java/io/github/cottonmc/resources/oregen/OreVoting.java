package io.github.cottonmc.resources.oregen;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.config.OreGenerationSettings;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class OreVoting implements SynchronousResourceReloadListener, IdentifiableResourceReloadListener {

	@Override
	public void apply(ResourceManager resourceManager) {
		OreVoteConfig config = new OreVoteConfig();
		HashMap<String, OreGenerationSettings> generators = new HashMap<>();
		
		Jankson jankson = JanksonFactory.createJankson();
		Collection<Identifier> identifiers = resourceManager.findResources("oregen", (rsrc) -> rsrc.endsWith(".json"));
		System.out.println(identifiers);
		for (Identifier id : identifiers) {
			//TODO: do some hackery to detect world/modpack datapack versus mod datapack versus 'c' namespace and bin the data accordingly
			
			try {
				JsonObject configObject = jankson.load(resourceManager.getResource(id).getInputStream());
				OreVoteConfig configLocal = jankson.fromJson(configObject, OreVoteConfig.class);
				//Fold this config into the globally resolved one
				config.requests.addAll(configLocal.requests);
				
				//TODO: Compile a list of offered features, first-come first-serve.
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (SyntaxError ex) {
				CottonResources.LOGGER.error(ex.getCompleteMessage());
			}
		}
		
		
		
		//TODO: We may need to synchronize on a mutex to smooth out this map access
		
		for(String s : config.requests) {
			OreGenerationSettings feature = CottonResources.CONFIG.ores.get(s);
			if (feature!=null) {
				
			} else {
				//TODO: Check "generator" bag to see if we have a generator offer. If so, load it in and enable it.
			}
		}
	}

	@Override
	public Identifier getFabricId() {
		return new Identifier("cotton-resources", "ore_voting");
	}
}
