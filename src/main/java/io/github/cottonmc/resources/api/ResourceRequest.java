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
package io.github.cottonmc.resources.api;

import io.github.cottonmc.resources.CottonResources;
import net.minecraft.util.Identifier;

public class ResourceRequest {
    private final Resource originalResource;
    protected final Resource resource;
    public final String modid;
    public ResourceRequest(Resource resource, String modid) {
        this.resource = resource;
        this.originalResource = resource;
        this.modid = modid;
    }
    public ResourceRequest requestPart(Identifier identifier) {
        if (!this.resource.partMap.containsKey(identifier)) return this;
        Resource.Part<?> part = this.resource.partMap.get(identifier);
        if (part.enabled) return this;
        part.enabled = true;
        if (!part.dependencies.isEmpty()) {
            for (Identifier dependencyId : part.dependencies) {
                Resource.Part<?> dependency = this.resource.partMap.get(dependencyId);
                if (dependency.enabled) continue;
                dependency.enabled = true;
                this.resource.partMap.put(dependencyId, dependency);
            }
        }
        this.resource.partMap.put(identifier, part);
        return this;
    }
    public ResourceRequest requestParts(Identifier... identifiers) {
        for (Identifier identifier : identifiers) {
            this.requestPart(identifier);
        }
        return this;
    }
    public <T> ResourceRequest addPart(Identifier identifier, Resource.Part<T> part) {
        Resource.Part<?> aa = this.resource.partMap.putIfAbsent(identifier, part);
        if (aa != null)
            CottonResources.LOGGER.warn(
                    String.format("Error processing request from %s: Part %s already exists on Resource %s.", modid,
                            identifier.toString(), ResourceArbiter.getId(originalResource)));
        return this;
    }
}
