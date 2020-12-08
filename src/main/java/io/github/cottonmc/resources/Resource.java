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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Resource {
    protected Map<Identifier, Part<?>> partMap = new HashMap<>();

    public static Builder fullMetalType(int tier) {
        return new Builder()
                .hasItem(Builtin.Parts.INGOT, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.INGOT)
                .hasBlockWithItem(Builtin.Parts.ORE, Builtin.Suppliers.makeTieredOre(tier),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.ORE)
                .hasBlockWithItem(Builtin.Parts.STORAGE_BLOCK, Builtin.Suppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.STORAGE_BLOCK)
                .hasItem(Builtin.Parts.NUGGET, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.NUGGET)
                .hasItem(Builtin.Parts.DUST, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.DUST)
                .hasBlockWithItem(Builtin.Parts.END_ORE, Builtin.Suppliers.makeTieredOre(tier),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.END_ORE)
                .hasBlockWithItem(Builtin.Parts.NETHER_ORE, Builtin.Suppliers.makeTieredOre(tier),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.NETHER_ORE)
                .hasItem(Builtin.Parts.GEAR, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.GEAR)
                .hasItem(Builtin.Parts.PLATE, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.PLATE);
    }
    public static Builder fullMetalGlowingType(int tier, Color particleColor) {
        return new Builder()
                .hasItem(Builtin.Parts.INGOT, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.INGOT)
                .hasBlockWithItem(Builtin.Parts.ORE, Builtin.Suppliers.makeGlowingTieredOre(tier, particleColor,
                        9), Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.ORE)
                .hasBlockWithItem(Builtin.Parts.STORAGE_BLOCK, Builtin.Suppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.STORAGE_BLOCK)
                .hasItem(Builtin.Parts.NUGGET, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.NUGGET)
                .hasItem(Builtin.Parts.DUST, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.DUST)
                .hasBlockWithItem(Builtin.Parts.END_ORE, Builtin.Suppliers.makeGlowingTieredOre(tier, particleColor,
                        9), Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.END_ORE)
                .hasBlockWithItem(Builtin.Parts.NETHER_ORE, Builtin.Suppliers.makeGlowingTieredOre(tier, particleColor,
                        9), Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.NETHER_ORE)
                .hasItem(Builtin.Parts.GEAR, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.GEAR)
                .hasItem(Builtin.Parts.PLATE, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.PLATE);
    }

    public static Builder metalTypeNoOre() {
        return new Builder()
                .hasItem(Builtin.Parts.INGOT, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.INGOT)
                .hasBlockWithItem(Builtin.Parts.STORAGE_BLOCK, Builtin.Suppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.STORAGE_BLOCK)
                .hasItem(Builtin.Parts.NUGGET, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.NUGGET)
                .hasItem(Builtin.Parts.DUST, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.DUST)
                .hasItem(Builtin.Parts.GEAR, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.GEAR)
                .hasItem(Builtin.Parts.PLATE, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.PLATE);
    }

    public static Builder fullGemType(int tier) {
        return new Builder()
                .hasItem(Builtin.Parts.GEM, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.NO_CHANGE)
                .hasBlockWithItem(Builtin.Parts.ORE, Builtin.Suppliers.makeTieredOre(tier),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.ORE)
                .hasBlockWithItem(Builtin.Parts.STORAGE_BLOCK, Builtin.Suppliers.METAL_STORAGE_BLOCK_SUPP.get(),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.STORAGE_BLOCK)
                .hasItem(Builtin.Parts.DUST, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.DUST)
                .hasBlockWithItem(Builtin.Parts.END_ORE, Builtin.Suppliers.makeTieredOre(tier),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.END_ORE)
                .hasBlockWithItem(Builtin.Parts.NETHER_ORE, Builtin.Suppliers.makeTieredOre(tier),
                        Builtin.Suppliers.BUILDING_BLOCK.get(), Builtin.NameTransformers.NETHER_ORE)
                .hasItem(Builtin.Parts.GEAR, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.GEAR)
                .hasItem(Builtin.Parts.PLATE, Builtin.Suppliers.MISC_SUPP.get(), Builtin.NameTransformers.PLATE);
    }
    public static class Part<T> {
        protected boolean enabled;
        public boolean vanilla;
        public T part;
        private final Set<Identifier> dependencies = new HashSet<>();
        private final Function<Identifier, Identifier> nameTransformer;
        protected Part(T part, Function<Identifier, Identifier> nameTransformer) {
            this.part = part;
            this.nameTransformer = nameTransformer;
        }
        protected void depend(Identifier dependency) {
            this.dependencies.add(dependency);
        }
        protected Identifier transformName(Identifier in) {
            return this.nameTransformer.apply(in);
        }
    }
    public static class Builder {
        private final Resource resource = new Resource();
        public Builder hasItem(Identifier identifier, Item item, Function<Identifier, Identifier> nameTransformer) {
            this.resource.partMap.putIfAbsent(identifier, new Part<>(item, nameTransformer));
            return this;
        }
        public Builder hasItem(Identifier identifier, Item item, Function<Identifier, Identifier> nameTransformer, boolean vanilla) {
            Part<Item> part = new Part<>(item, nameTransformer);
            part.vanilla = vanilla;
            this.resource.partMap.putIfAbsent(identifier, part);
            return this;
        }
        public Builder hasBlock(Identifier identifier, Block block, Function<Identifier, Identifier> nameTransformer) {
            this.resource.partMap.putIfAbsent(identifier, new Part<>(block, nameTransformer));
            return this;
        }
        public Builder hasBlock(Identifier identifier, Block block, Function<Identifier, Identifier> nameTransformer, boolean vanilla) {
            Part<Block> part = new Part<>(block, nameTransformer);
            part.vanilla = vanilla;
            this.resource.partMap.putIfAbsent(identifier, part);
            return this;
        }
        public Builder hasBlockWithItem(Identifier identifier, Block block, Item.Settings itemSettings,
                                        Function<Identifier, Identifier> nameTransformer) {
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
                                        Function<Identifier, Identifier> nameTransformer, boolean vanilla) {
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
        public Builder hasFluid(Identifier identifier, Fluid fluid, Function<Identifier, Identifier> nameTransformer) {
            this.resource.partMap.putIfAbsent(identifier, new Part<>(fluid, nameTransformer));
            return this;
        }
        public Builder hasFluid(Identifier identifier, Fluid fluid, Function<Identifier, Identifier> nameTransformer, boolean vanilla) {
            Part<Fluid> part = new Part<>(fluid, nameTransformer);
            part.vanilla = vanilla;
            this.resource.partMap.putIfAbsent(identifier, part);
            return this;
        }
        public <T> Builder has(Identifier identifier, T t, Function<Identifier, Identifier> nameTransformer) {
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
                                identifier.toString(), CottonResources.ResourceArbiter.getId(originalResource)));
            return this;
        }
    }
}
