/*
 * Copyright 2020 The Cotton project
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
package io.github.cottonmc.resources.impl;

import io.github.cottonmc.resources.api.Color;
import io.github.cottonmc.resources.api.MippedOreBlock;
import io.github.cottonmc.resources.api.Resource;
import io.github.cottonmc.resources.api.ResourceArbiter;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BuiltinResources {
    public static final Identifier COPPER_ID = new Identifier("c", "copper");
    public static final Identifier TIN_ID = new Identifier("c", "tin");
    public static final Identifier ZINC_ID = new Identifier("c", "zinc");
    public static final Identifier SILVER_ID = new Identifier("c", "silver");
    public static final Identifier LEAD_ID = new Identifier("c", "lead");
    public static final Identifier ALUMINUM_ID = new Identifier("c", "aluminum");
    public static final Identifier PLATINUM_ID = new Identifier("c", "platinum");
    public static final Identifier PALLADIUM_ID = new Identifier("c", "palladium");
    public static final Identifier OSMIUM_ID = new Identifier("c", "osmium");
    public static final Identifier IRIDIUM_ID = new Identifier("c", "iridum");
    public static final Identifier TUNGSTEN_ID = new Identifier("c", "tungsten");
    public static final Identifier COBALT_ID = new Identifier("c", "cobalt");

    public static final Identifier STEEL_ID = new Identifier("c", "steel");
    public static final Identifier TITANIUM_ID = new Identifier("c", "titanium");
    public static final Identifier BRASS_ID = new Identifier("c", "brass");
    public static final Identifier ELECTRUM_ID = new Identifier("c", "electrum");
    public static final Identifier BRONZE_ID = new Identifier("c", "bronze");

    public static final Identifier COAL_ID = new Identifier("c", "coal");
    public static final Identifier COAL_COKE_ID = new Identifier("c", "coal_coke");
    public static final Identifier URANIUM_ID = new Identifier("c", "uranium");
    public static final Identifier MERCURY_ID = new Identifier("c", "mercury");
    public static final Identifier THORIUM_ID = new Identifier("c", "thorium");
    public static final Identifier PLUTONIUM_ID =  new Identifier("c", "plutonium");

    public static final Identifier RUBY_ID = new Identifier("c", "ruby");
    public static final Identifier TOPAZ_ID = new Identifier("c", "topaz");
    public static final Identifier AMETHYST_ID = new Identifier("c", "amethyst");
    public static final Identifier PERIDOT_ID = new Identifier("c", "peridot");
    public static final Identifier SAPPHIRE_ID = new Identifier("c", "sapphire");

    public static final Identifier WOOD_ID = new Identifier("c", "wood");
    public static final Identifier STONE_ID = new Identifier("c", "stone");
    public static final Identifier IRON_ID = new Identifier("c", "iron");
    public static final Identifier GOLD_ID = new Identifier("c", "gold");
    public static final Identifier DIAMOND_ID = new Identifier("c", "diamond");
    public static final Identifier EMERALD_ID = new Identifier("c", "emerald");
    public static final Identifier NETHERITE_ID = new Identifier("c", "netherite");

    private static final Color URANIUM_PARTICLE_COLOR = Color.parseHex("74D94D");


    public static final Resource COPPER =
            ResourceArbiter.addOrGetResource(COPPER_ID, Resource.fullMetalType(1).build());
    public static final Resource TIN =
            ResourceArbiter.addOrGetResource(TIN_ID, Resource.fullMetalType(1).build());
    public static final Resource ZINC =
            ResourceArbiter.addOrGetResource(ZINC_ID, Resource.fullMetalType(1).build());

    public static final Resource SILVER =
            ResourceArbiter.addOrGetResource(SILVER_ID, Resource.fullMetalType(2).build());
    public static final Resource LEAD =
            ResourceArbiter.addOrGetResource(LEAD_ID, Resource.fullMetalType(2).build());
    public static final Resource ALUMINUM =
            ResourceArbiter.addOrGetResource(ALUMINUM_ID, Resource.fullMetalType(2).build());
    public static final Resource PLATINUM =
            ResourceArbiter.addOrGetResource(PLATINUM_ID, Resource.fullMetalType(2).build());
    public static final Resource PALLADIUM =
            ResourceArbiter.addOrGetResource(PALLADIUM_ID, Resource.fullMetalType(2).build());
    public static final Resource OSMIUM =
            ResourceArbiter.addOrGetResource(OSMIUM_ID, Resource.fullMetalType(2).build());
    public static final Resource IRIDIUM =
            ResourceArbiter.addOrGetResource(IRIDIUM_ID, Resource.fullMetalType(2).build());
    public static final Resource TUNGSTEN =
            ResourceArbiter.addOrGetResource(TUNGSTEN_ID, Resource.fullMetalType(2).build());

    public static final Resource COBALT =
            ResourceArbiter.addOrGetResource(COBALT_ID, Resource.fullMetalType(3).build());
    public static final Resource TITANIUM =
            ResourceArbiter.addOrGetResource(TITANIUM_ID, Resource.fullMetalType(3).build());

    public static final Resource STEEL =
            ResourceArbiter.addOrGetResource(STEEL_ID, Resource.metalTypeNoOre().build());
    public static final Resource BRASS =
            ResourceArbiter.addOrGetResource(BRASS_ID, Resource.metalTypeNoOre().build());
    public static final Resource ELECTRUM =
            ResourceArbiter.addOrGetResource(ELECTRUM_ID, Resource.metalTypeNoOre().build());
    public static final Resource BRONZE =
            ResourceArbiter.addOrGetResource(BRONZE_ID, Resource.metalTypeNoOre().build());

    public static final Resource COAL =
            ResourceArbiter.addOrGetResource(COAL_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, Items.COAL, BuiltinNameTransformers.NO_CHANGE, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.COAL_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.COAL_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasBlock(Parts.ORE, Blocks.COAL_ORE, BuiltinNameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.COAL_ORE, BuiltinNameTransformers.ORE, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, BuiltinSuppliers.makeTieredOre(0),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, BuiltinSuppliers.makeTieredOre(0),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .build());
    public static final Resource COAL_COKE =
            ResourceArbiter.addOrGetResource(COAL_COKE_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.NO_CHANGE)
                            .hasBlockWithItem(Parts.STORAGE_BLOCK, BuiltinSuppliers.STONY_STORAGE_BLOCK_SUPP.get(),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.STORAGE_BLOCK)
                            .build());
    public static final Resource URANIUM =
            ResourceArbiter.addOrGetResource(URANIUM_ID, Resource.fullMetalGlowingType(3,
                    URANIUM_PARTICLE_COLOR).build());
    public static final Resource MERCURY =
            ResourceArbiter.addOrGetResource(MERCURY_ID,
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.NO_CHANGE)
                            .build());
    public static final Resource THORIUM =
            ResourceArbiter.addOrGetResource(THORIUM_ID, Resource.metalTypeNoOre().build());
    public static final Resource PLUTONIUM =
            ResourceArbiter.addOrGetResource(PLUTONIUM_ID, Resource.metalTypeNoOre().build());

    public static final Resource RUBY =
            ResourceArbiter.addOrGetResource(RUBY_ID, Resource.fullGemType(1).build());
    public static final Resource TOPAZ =
            ResourceArbiter.addOrGetResource(TOPAZ_ID, Resource.fullGemType(1).build());
    public static final Resource AMETHYST =
            ResourceArbiter.addOrGetResource(AMETHYST_ID, Resource.fullGemType(1).build());
    public static final Resource PERIDOT =
            ResourceArbiter.addOrGetResource(PERIDOT_ID, Resource.fullGemType(1).build());
    public static final Resource SAPPHIRE =
            ResourceArbiter.addOrGetResource(SAPPHIRE_ID, Resource.fullGemType(1).build());

    public static final Resource WOOD =
            ResourceArbiter.addOrGetResource(WOOD_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .hasItem(Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                            .hasItem(Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE)
                            .build());
    public static final Resource STONE =
            ResourceArbiter.addOrGetResource(STONE_ID, 
                    new Resource.Builder()
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .hasItem(Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                            .hasItem(Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE)
                            .build());
    public static final Resource IRON =
            ResourceArbiter.addOrGetResource(IRON_ID, 
                    new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.IRON_ORE, BuiltinNameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.IRON_ORE, BuiltinNameTransformers.ORE, true)
                            .hasItem(Parts.INGOT, Items.IRON_INGOT, BuiltinNameTransformers.INGOT, true)
                            .hasItem(Parts.NUGGET, Items.IRON_NUGGET, BuiltinNameTransformers.NUGGET, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.IRON_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.IRON_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, BuiltinSuppliers.makeTieredOre(1), 
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, BuiltinSuppliers.makeTieredOre(1),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .hasItem(Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                            .hasItem(Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE)
                            .build());
    public static final Resource GOLD =
            ResourceArbiter.addOrGetResource(GOLD_ID,
                    ResourceArbiter.addOrGetResource(DIAMOND_ID, 
                            new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.GOLD_ORE, BuiltinNameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.GOLD_ORE, BuiltinNameTransformers.ORE, true)
                            .hasItem(Parts.INGOT, Items.GOLD_INGOT, BuiltinNameTransformers.INGOT, true)
                            .hasItem(Parts.NUGGET, Items.GOLD_NUGGET, BuiltinNameTransformers.NUGGET, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.GOLD_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.GOLD_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, BuiltinSuppliers.makeTieredOre(1),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, BuiltinSuppliers.makeTieredOre(1),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .hasItem(Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                            .hasItem(Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE)
                            .build())
            );
    public static final Resource DIAMOND =
            ResourceArbiter.addOrGetResource(DIAMOND_ID, 
                    new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.DIAMOND_ORE, BuiltinNameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.DIAMOND_ORE, BuiltinNameTransformers.ORE, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.DIAMOND_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.DIAMOND_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.GEM, Items.DIAMOND, BuiltinNameTransformers.NO_CHANGE, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, BuiltinSuppliers.makeTieredOre(1),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, BuiltinSuppliers.makeTieredOre(1),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .hasItem(Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                            .hasItem(Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE)
                            .build());
    public static final Resource EMERALD =
            ResourceArbiter.addOrGetResource(EMERALD_ID,
                    new Resource.Builder()
                            .hasBlock(Parts.ORE, Blocks.EMERALD_ORE, BuiltinNameTransformers.ORE, true)
                            .hasItem(Parts.ORE_ITEM, Items.EMERALD_ORE, BuiltinNameTransformers.ORE, true)
                            .hasBlock(Parts.STORAGE_BLOCK, Blocks.EMERALD_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.STORAGE_BLOCK_ITEM, Items.EMERALD_BLOCK, BuiltinNameTransformers.STORAGE_BLOCK, true)
                            .hasItem(Parts.GEM, Items.EMERALD, BuiltinNameTransformers.NO_CHANGE, true)
                            .hasBlockWithItem(Parts.NETHER_ORE, BuiltinSuppliers.makeTieredOre(1),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.NETHER_ORE)
                            .hasBlockWithItem(Parts.END_ORE, BuiltinSuppliers.makeTieredOre(1),
                                    BuiltinSuppliers.BUILDING_BLOCK.get(), BuiltinNameTransformers.END_ORE)
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .hasItem(Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                            .hasItem(Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE)
                            .build());
    public static final Resource NETHERITE =
            ResourceArbiter.addOrGetResource(NETHERITE_ID,
                    new Resource.Builder()
                            .hasItem(Parts.GENERIC, Items.NETHERITE_SCRAP,
                                    (id) -> new Identifier(id.getNamespace(), id.getPath().concat("_scrap")), true)
                            .hasBlock(Parts.ORE, Blocks.ANCIENT_DEBRIS,
                                    (id) -> new Identifier("minecraft", "ancient_debris"), true)
                            .hasItem(Parts.ORE_ITEM, Items.ANCIENT_DEBRIS,
                                    (id) -> new Identifier("minecraft", "ancient_debris"), true)
                            .hasItem(Parts.INGOT, Items.NETHERITE_INGOT, BuiltinNameTransformers.INGOT, true)
                            .hasItem(Parts.DUST, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.DUST)
                            .hasItem(Parts.GEAR, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.GEAR)
                            .hasItem(Parts.PLATE, BuiltinSuppliers.MISC_SUPP.get(), BuiltinNameTransformers.PLATE)
                            .build());

    public static class Parts {
        public static final Identifier GENERIC = new Identifier("c", "generic");
        public static final Identifier GEM = new Identifier("c", "gem");
        public static final Identifier INGOT = new Identifier("c", "ingot");
        public static final Identifier ORE = new Identifier("c", "ore");
        public static final Identifier ORE_ITEM = new Identifier("c", "ore_item");
        public static final Identifier NUGGET = new Identifier("c", "nugget");
        public static final Identifier STORAGE_BLOCK = new Identifier("c", "storage_block");
        public static final Identifier STORAGE_BLOCK_ITEM = new Identifier("c", "storage_block_item");
        public static final Identifier DUST = new Identifier("c", "dust");
        public static final Identifier END_ORE = new Identifier("c", "end_ore");
        public static final Identifier END_ORE_ITEM = new Identifier("c", "end_ore_item");
        public static final Identifier NETHER_ORE = new Identifier("c", "nether_ore");
        public static final Identifier NETHER_ORE_ITEM = new Identifier("c", "nether_ore_item");
        public static final Identifier GEAR = new Identifier("c", "gear");
        public static final Identifier PLATE = new Identifier("c", "plate");
    }

}
