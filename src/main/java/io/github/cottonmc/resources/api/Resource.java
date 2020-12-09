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

import io.github.cottonmc.resources.impl.BuiltinNameTransformers;
import io.github.cottonmc.resources.impl.BuiltinResources;
import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.impl.BuiltinSuppliers;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Resource {
    protected Map<Identifier, Part<?>> partMap = new HashMap<>();

    public static Builder fullMetalType(int tier) {
        return new Builder()
                .hasItem(BuiltinResources.Parts.INGOT, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.INGOT)
                .hasBlockWithItem(BuiltinResources.Parts.ORE, BuiltinSuppliers.makeTieredOre(tier),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.ORE)
                .hasBlockWithItem(BuiltinResources.Parts.STORAGE_BLOCK, BuiltinSuppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.STORAGE_BLOCK)
                .hasItem(BuiltinResources.Parts.NUGGET, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.NUGGET)
                .hasItem(BuiltinResources.Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                .hasBlockWithItem(BuiltinResources.Parts.END_ORE, BuiltinSuppliers.makeTieredOre(tier),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                .hasBlockWithItem(BuiltinResources.Parts.NETHER_ORE, BuiltinSuppliers.makeTieredOre(tier),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                .hasItem(BuiltinResources.Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                .hasItem(BuiltinResources.Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE);
    }
    public static Builder fullMetalGlowingType(int tier, Color particleColor) {
        return new Builder()
                .hasItem(BuiltinResources.Parts.INGOT, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.INGOT)
                .hasBlockWithItem(BuiltinResources.Parts.ORE, BuiltinSuppliers.makeGlowingTieredOre(tier, particleColor,
                        9), BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.ORE)
                .hasBlockWithItem(BuiltinResources.Parts.STORAGE_BLOCK, BuiltinSuppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.STORAGE_BLOCK)
                .hasItem(BuiltinResources.Parts.NUGGET, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.NUGGET)
                .hasItem(BuiltinResources.Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                .hasBlockWithItem(BuiltinResources.Parts.END_ORE, BuiltinSuppliers.makeGlowingTieredOre(tier, particleColor,
                        9), BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                .hasBlockWithItem(BuiltinResources.Parts.NETHER_ORE, BuiltinSuppliers.makeGlowingTieredOre(tier, particleColor,
                        9), BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                .hasItem(BuiltinResources.Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                .hasItem(BuiltinResources.Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE);
    }

    public static Builder metalTypeNoOre() {
        return new Builder()
                .hasItem(BuiltinResources.Parts.INGOT, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.INGOT)
                .hasBlockWithItem(BuiltinResources.Parts.STORAGE_BLOCK, BuiltinSuppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.STORAGE_BLOCK)
                .hasItem(BuiltinResources.Parts.NUGGET, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.NUGGET)
                .hasItem(BuiltinResources.Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                .hasItem(BuiltinResources.Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                .hasItem(BuiltinResources.Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE);
    }

    public static Builder fullGemType(int tier) {
        return new Builder()
                .hasItem(BuiltinResources.Parts.GEM, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.NO_CHANGE)
                .hasBlockWithItem(BuiltinResources.Parts.ORE, BuiltinSuppliers.makeTieredOre(tier),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.ORE)
                .hasBlockWithItem(BuiltinResources.Parts.STORAGE_BLOCK, BuiltinSuppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.STORAGE_BLOCK)
                .hasItem(BuiltinResources.Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                .hasBlockWithItem(BuiltinResources.Parts.END_ORE, BuiltinSuppliers.makeTieredOre(tier),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                .hasBlockWithItem(BuiltinResources.Parts.NETHER_ORE, BuiltinSuppliers.makeTieredOre(tier),
                        BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                .hasItem(BuiltinResources.Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                .hasItem(BuiltinResources.Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE);
    }
    public static class Part<T> {
        protected boolean enabled;
        public boolean vanilla;
        public T part;
        final Set<Identifier> dependencies = new HashSet<>();
        private final NameTransformer nameTransformer;
        protected Part(T part, NameTransformer nameTransformer) {
            this.part = part;
            this.nameTransformer = nameTransformer;
        }
        protected void depend(Identifier dependency) {
            this.dependencies.add(dependency);
        }
        protected Identifier transformName(Identifier in) {
            return this.nameTransformer.transform(in);
        }
    }
    public static class Builder {
        private final Resource resource = new Resource();
        public Builder hasItem(Identifier identifier, Item item, NameTransformer nameTransformer) {
            this.resource.partMap.putIfAbsent(identifier, new Part<>(item, nameTransformer));
            return this;
        }
        public Builder hasItem(Identifier identifier, Item item, NameTransformer nameTransformer, boolean vanilla) {
            Part<Item> part = new Part<>(item, nameTransformer);
            part.vanilla = vanilla;
            this.resource.partMap.putIfAbsent(identifier, part);
            return this;
        }
        public Builder hasBlock(Identifier identifier, Block block, NameTransformer nameTransformer) {
            this.resource.partMap.putIfAbsent(identifier, new Part<>(block, nameTransformer));
            return this;
        }
        public Builder hasBlock(Identifier identifier, Block block, NameTransformer nameTransformer, boolean vanilla) {
            Part<Block> part = new Part<>(block, nameTransformer);
            part.vanilla = vanilla;
            this.resource.partMap.putIfAbsent(identifier, part);
            return this;
        }
        public Builder hasBlockWithItem(Identifier identifier, Block block, Item.Settings itemSettings,
                                        NameTransformer nameTransformer) {
            Part<Block> blockPart = new Part<>(block, nameTransformer);
            Part<Item> itemPart = new Part<>(new BlockItem(block, itemSettings), nameTransformer);
            Identifier itemPartId = new Identifier(identifier.getNamespace(), identifier.getPath().concat("_item"));
            blockPart.depend(itemPartId);
            itemPart.depend(identifier);
            this.resource.partMap.putIfAbsent(identifier, blockPart);
            this.resource.partMap.putIfAbsent(itemPartId, itemPart);
            return this;
        }
        public Builder hasBlockWithItem(Identifier identifier, Block block, Item.Settings itemSettings,
                                        NameTransformer nameTransformer, boolean vanilla) {
            Part<Block> blockPart = new Part<>(block, nameTransformer);
            Part<Item> itemPart = new Part<>(new BlockItem(block, itemSettings), nameTransformer);
            Identifier itemPartId = new Identifier(identifier.getNamespace(), identifier.getPath().concat("_item"));
            blockPart.depend(itemPartId);
            itemPart.depend(identifier);
            itemPart.vanilla = vanilla; blockPart.vanilla = vanilla;
            this.resource.partMap.putIfAbsent(identifier, blockPart);
            this.resource.partMap.putIfAbsent(itemPartId, itemPart);
            return this;
        }
        public Builder hasFluid(Identifier identifier, Fluid fluid, NameTransformer nameTransformer) {
            this.resource.partMap.putIfAbsent(identifier, new Part<>(fluid, nameTransformer));
            return this;
        }
        public Builder hasFluid(Identifier identifier, Fluid fluid, NameTransformer nameTransformer, boolean vanilla) {
            Part<Fluid> part = new Part<>(fluid, nameTransformer);
            part.vanilla = vanilla;
            this.resource.partMap.putIfAbsent(identifier, part);
            return this;
        }
        public <T> Builder has(Identifier identifier, T t, NameTransformer nameTransformer) {
            Part<T> part = new Part<>(t, nameTransformer);
            this.resource.partMap.putIfAbsent(identifier, part);
            return this;
        }
        public Resource build() {
            return resource;
        }
    }
    public static class Request {
        private final Resource originalResource;
        protected final Resource resource;
        public final String modid;
        public Request(Resource resource, String modid) {
            this.resource = resource;
            this.originalResource = resource;
            this.modid = modid;
        }
        public Request requestPart(Identifier identifier) {
            if (!this.resource.partMap.containsKey(identifier)) return this;
            Part<?> part = this.resource.partMap.get(identifier);
            if (part.enabled) return this;
            part.enabled = true;
            if (!part.dependencies.isEmpty()) {
                for (Identifier dependencyId : part.dependencies) {
                    Part<?> dependency = this.resource.partMap.get(dependencyId);
                    if (dependency.enabled) continue;
                    dependency.enabled = true;
                    this.resource.partMap.put(dependencyId, dependency);
                }
            }
            this.resource.partMap.put(identifier, part);
            return this;
        }
        public Request requestParts(Identifier... identifiers) {
            for (Identifier identifier : identifiers) {
                this.requestPart(identifier);
            }
            return this;
        }
        public <T> Request addPart(Identifier identifier, Part<T> part) {
            Part<?> aa = this.resource.partMap.putIfAbsent(identifier, part);
            if (aa != null)
                CottonResources.LOGGER.warn(
                        String.format("Error processing request from %s: Part %s already exists on Resource %s.", modid,
                                identifier.toString(), ResourceArbiter.getId(originalResource)));
            return this;
        }
    }
}
