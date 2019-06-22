package io.github.cottonmc.resources.oregen;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.impl.SyntaxError;
import io.github.cottonmc.resources.CottonResources;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class OreVoting implements IdentifiableResourceReloadListener {
    private static OreVoting INSTANCE = new OreVoting();
    private OreVoting() {}
    
    public static IdentifiableResourceReloadListener instance() {
        return INSTANCE;
    }

    @Override
    public CompletableFuture<Void> reload(ResourceReloadListener.Synchronizer futureGetter, ResourceManager resourceManager, Profiler profiler1, Profiler profiler2, Executor executor1, Executor executor2) {
        
        return CompletableFuture.runAsync(() -> {
                Collection<Identifier> identifiers = resourceManager.findResources("oregen", (rsrc) -> rsrc.endsWith(".json"));
                for(Identifier id : identifiers) {
                    try {
                        Jankson.builder().build().load(resourceManager.getResource(id).getInputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SyntaxError ex) {
                        CottonResources.logger.error(ex.getCompleteMessage());
                    }
                }
                
                //TODO: Combine votes, update oregen, and reinstate recipes
                
               
            }, executor1)
            .thenCompose((Void)->{
                futureGetter.whenPrepared(null);
                return null;
            });
        
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("c","ore-voting"); //If you want to run just after ore-voting, hang a reload listener with a dependency on "c:ore-voting".
    }
    
}
