package io.github.cottonmc.resources.tag;

import java.util.Map;

import com.google.common.util.concurrent.MoreExecutors;

import io.github.cottonmc.resources.CottonResources;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.tag.RegistryTagContainer;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WorldTagReloadListener implements SynchronousResourceReloadListener, IdentifiableResourceReloadListener {
	public static final Identifier ID = new Identifier(CottonResources.MODID, "world_tags");
	
	//implements SynchronousResourceReloadListener {
		@Override
		public void apply(ResourceManager resourceManager) {
			BiomeTags.setContainer(reload(resourceManager, Registry.BIOME, "tags/biomes", "biome"));
			
			DimensionTypeTags.setContainer(reload(resourceManager, Registry.DIMENSION, "tags/dimensions", "dimension"));
		}
	//}
	
	private static <T> TagContainer<T> reload(ResourceManager resourceManager, Registry<T> registry, String folder, String name) {
		RegistryTagContainer<T> container = new RegistryTagContainer<>(registry, folder, name);
		try {
			Map<Identifier, Tag.Builder<T>> map = container.prepareReload(resourceManager, MoreExecutors.directExecutor()).get(); //waits synchronously for prepareReload to complete
			container.applyReload(map);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return container;
	}
	
	//implements IdentifiableResourceReloadListener {
		@Override
		public Identifier getFabricId() {
			return ID;
		}
	//}
}
