package io.github.cottonmc.resources.oregen;

import blue.endless.jankson.impl.SyntaxError;
import io.github.cottonmc.jankson.JanksonFactory;
import io.github.cottonmc.resources.CottonResources;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class OreVoting implements SimpleResourceReloadListener {
    @Override
    public CompletableFuture load(ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            Collection<Identifier> identifiers = resourceManager.findResources("oregen", (rsrc) -> rsrc.endsWith(".json"));
            System.out.println(identifiers);
            for (Identifier id : identifiers) {
                try {
                    JanksonFactory.createJankson().load(resourceManager.getResource(id).getInputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SyntaxError ex) {
                    CottonResources.LOGGER.error(ex.getCompleteMessage());
                }
            }
        });
    }

    @Override
    public CompletableFuture<Void> apply(Object o, ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return null;
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(CottonResources.MODID, "ore_voting");
    }
}
