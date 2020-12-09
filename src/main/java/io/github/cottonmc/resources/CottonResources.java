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

import io.github.cottonmc.resources.api.ResourceArbiter;
import io.github.cottonmc.resources.api.CottonResourcesApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CottonResources implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("CottonResources");
	@Override
	public void onInitialize() {
		ResourceRequestCallback.EVENT.invoker().request();
		FabricLoader.getInstance().getEntrypointContainers("cotton-resources", CottonResourcesApi.class).forEach(entrypoint -> {
			entrypoint.getEntrypoint().registerResources();
		});
		FabricLoader.getInstance().getEntrypointContainers("cotton-resources", CottonResourcesApi.class).forEach(entrypoint -> {
			entrypoint.getEntrypoint().requestResources();
		});
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

}
