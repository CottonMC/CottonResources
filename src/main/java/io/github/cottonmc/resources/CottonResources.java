/*
 * Copyright 2020 The Cotton Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.cottonmc.resources;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CottonResources implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("CottonResources");
	@Override
	public void onInitialize() {
		ResourceRequestCallback.EVENT.invoker().request();
		ResourceArbiter.registerAll();
	}
	public interface ResourceRegisterCallback {
		Event<ResourceRegisterCallback> EVENT = EventFactory.createArrayBacked(ResourceRegisterCallback.class,
				(listeners) -> () -> {
					for (ResourceRegisterCallback listener : listeners) {
						listener.register();
					}
				});
		void register();
	}
	public interface ResourceRequestCallback {
		Event<ResourceRequestCallback> EVENT = EventFactory.createArrayBacked(ResourceRequestCallback.class,
				(listeners) -> () -> {
					for (ResourceRequestCallback listener : listeners) {
						listener.request();
					}
				});
		void request();
	}
	protected static class ResourceArbiter {
		private static final Map<Identifier, Resource> resources = new HashMap<>();
		public static void request(Identifier identifier, Resource.Request request) {
			if (resources.containsKey(identifier)) {
				Resource existing = resources.get(identifier);
				for (Map.Entry<Identifier, Resource.Part<?>> partEntry : request.resource.partMap.entrySet()) {
					if (!partEntry.getValue().enabled) continue;
					if (existing.partMap.containsKey(partEntry.getKey())) {
						Resource.Part<?> part = existing.partMap.get(partEntry.getKey());
						part.enabled = true;
						existing.partMap.put(partEntry.getKey(), part);
					} else {
						existing.partMap.put(partEntry.getKey(), partEntry.getValue());
					}
				}
			} else {
				LOGGER.error(String.format("Error managing request from %s: Resource %s does not exist!", request.modid,
						identifier.toString()));
			}
		}
		public static Resource addOrGetResource(Identifier identifier, Resource resource) {
			if (!resources.containsKey(identifier)) resources.put(identifier, resource);
			return resources.get(identifier);
		}
		public static Identifier getId(Resource resource) {
			for (Map.Entry<Identifier, Resource> resourceEntry : resources.entrySet()) {
				if (resourceEntry.getValue().equals(resource)) return resourceEntry.getKey();
			}
			return null;
		}
		protected static void registerAll() {
			for (Map.Entry<Identifier,Resource> resourceEntry : resources.entrySet()) {
				Resource resource =  resourceEntry.getValue();
				for (Map.Entry<Identifier, Resource.Part<?>> partEntry : resource.partMap.entrySet()) {
					if (!partEntry.getValue().enabled || partEntry.getValue().vanilla) continue;
					Identifier transformed = partEntry.getValue().transformName(resourceEntry.getKey());
					if (partEntry.getValue().part instanceof Item) {
						Resource.Part<Item> part = (Resource.Part<Item>)partEntry.getValue();
						if (!Registry.ITEM.containsId(transformed)) {
							part.part = Registry.register(Registry.ITEM, transformed, part.part);
							resource.partMap.put(partEntry.getKey(), part);
						}
					} else if (partEntry.getValue().part instanceof Block) {
						Resource.Part<Block> part = (Resource.Part<Block>)partEntry.getValue();
						if (!Registry.BLOCK.containsId(transformed)) {
							part.part = Registry.register(Registry.BLOCK, transformed, part.part);
							resource.partMap.put(partEntry.getKey(), part);
							if (part.part instanceof Builtin.MippedOreBlock &&
									FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
								handleMipped(part.part);
						}
					} else if (partEntry.getValue().part instanceof Fluid) {
						Resource.Part<Fluid> part = (Resource.Part<Fluid>)partEntry.getValue();
						if (!Registry.FLUID.containsId(transformed)) {
							part.part = Registry.register(Registry.FLUID, transformed, part.part);
							resource.partMap.put(partEntry.getKey(), part);
						}
					}
				}
				resources.put(resourceEntry.getKey(), resource);
			}
		}
		@Environment(EnvType.CLIENT)
		private static void handleMipped(Block block) {
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
		}

	}
}
