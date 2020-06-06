/*
 * MIT License
 *
 * Copyright (c) 2018-2020 The Cotton Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.cottonmc.resources.tag;

import java.util.Map;
import com.google.common.util.concurrent.MoreExecutors;
import io.github.cottonmc.resources.CottonResources;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.RegistryTagContainer;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WorldTagReloadListener implements SimpleSynchronousResourceReloadListener {
	public static final Identifier ID = CottonResources.resources("world_tags");

	@Override
	public void apply(ResourceManager resourceManager) {
		BiomeTags.setContainer(reload(resourceManager, Registry.BIOME, "tags/biomes", "biome"));
		// TODO: Reimplement me
		//DimensionTypeTags.setContainer(reload(resourceManager, Registry.DIMENSION_TYPE, "tags/dimensions", "dimension"));
	}

	private static <T> TagContainer<T> reload(ResourceManager resourceManager, Registry<T> registry, String folder, String name) {
		RegistryTagContainer<T> container = new RegistryTagContainer<>(registry, folder, name);

		try {
			Map<Identifier, Tag.Builder> map = container.prepareReload(resourceManager, MoreExecutors.directExecutor()).get(); //waits synchronously for prepareReload to complete
			container.applyReload(map);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return container;
	}

	@Override
	public Identifier getFabricId() {
		return ID;
	}
}
