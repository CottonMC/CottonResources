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

package io.github.cottonmc.resources;

import io.github.cottonmc.resources.block.BlockSuppliers;
import io.github.cottonmc.resources.type.GemResourceType;
import io.github.cottonmc.resources.type.GenericResourceType;
import io.github.cottonmc.resources.type.MetalResourceType;
import io.github.cottonmc.resources.type.RadioactiveResourceType;
import io.github.cottonmc.resources.type.ResourceType;

/**
 * An enumeration of all Built-in resources provided by Cotton-Resources.
 */
public final class BuiltinResources {
	public static final MetalResourceType COPPER = ResourceTemplates.fullMetalType("copper").oreSupplier(BlockSuppliers.STONE_TIER_ORE).build();
	public static final MetalResourceType SILVER = ResourceTemplates.fullMetalType("silver").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final MetalResourceType LEAD = ResourceTemplates.fullMetalType("lead").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final MetalResourceType ZINC = ResourceTemplates.fullMetalType("zinc").oreSupplier(BlockSuppliers.STONE_TIER_ORE).build();
	public static final MetalResourceType ALUMINUM = ResourceTemplates.fullMetalType("aluminum").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final MetalResourceType COBALT = ResourceTemplates.fullMetalType("cobalt").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();

	public static final MetalResourceType TIN = ResourceTemplates.fullMetalType("tin").oreSupplier(BlockSuppliers.STONE_TIER_ORE).build();
	public static final MetalResourceType TITANIUM = ResourceTemplates.fullMetalType("titanium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final MetalResourceType TUNGSTEN = ResourceTemplates.fullMetalType("tungsten").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();

	public static final MetalResourceType PLATINUM = ResourceTemplates.fullMetalType("platinum").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final MetalResourceType PALLADIUM = ResourceTemplates.fullMetalType("palladium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final MetalResourceType OSMIUM = ResourceTemplates.fullMetalType("osmium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final MetalResourceType IRIDIUM = ResourceTemplates.fullMetalType("iridium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();

	public static final MetalResourceType STEEL = ResourceTemplates.metalTypeNoOre("steel").build();
	public static final MetalResourceType BRASS = ResourceTemplates.metalTypeNoOre("brass").build();
	public static final MetalResourceType ELECTRUM = ResourceTemplates.metalTypeNoOre("electrum").build();

	public static final GenericResourceType COAL = ResourceType.builder("coal")
			.generic()
			.noBlock()
			.withDustAffix()
			.build();

	public static final GenericResourceType COAL_COKE = ResourceType.builder("coal_coke")
			.generic()
			.blockSupplier(BlockSuppliers.COAL_BLOCK)
			.noAffix()
			.build();

	public static final GenericResourceType MERCURY = ResourceType.builder("mercury")
			.generic()
			.noBlock()
			.noAffix()
			.build();

	public static final RadioactiveResourceType URANIUM = ResourceTemplates.radioactiveTypeNoOre("uranium")
			.allOres()
			.itemAffixName("ingot")
			.oreSupplier(BlockSuppliers.RADIOACTIVE_DIAMOND_TIER_ORE)
			.build();

	public static final RadioactiveResourceType THORIUM = ResourceTemplates.radioactiveTypeNoOre("thorium")
			.itemAffixName("ingot")
			.build();

	public static final RadioactiveResourceType PLUTONIUM = ResourceTemplates.radioactiveTypeNoOre("plutonium")
			.itemAffixName("ingot")
			.build();

	public static final GemResourceType RUBY = ResourceTemplates.fullGemType("ruby").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final GemResourceType TOPAZ = ResourceTemplates.fullGemType("topaz").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final GemResourceType AMETHYST = ResourceTemplates.fullGemType("amethyst").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final GemResourceType PERIDOT = ResourceTemplates.fullGemType("peridot").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();
	public static final GemResourceType SAPPHIRE = ResourceTemplates.fullGemType("sapphire").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build();

	// Vanilla Resource Types
	// These exist solely to add the gears/plates/dust when applicable.

	public static final GenericResourceType WOOD = ResourceType.builder("wood")
			.generic()
			.noBlock()
			.withGearAffix()
			.build();

	public static final GenericResourceType STONE = ResourceType.builder("stone")
			.generic()
			.noBlock()
			.withGearAffix()
			.build();

	public static final GenericResourceType IRON = ResourceType.builder("iron")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build();

	public static final GenericResourceType GOLD = ResourceType.builder("gold")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build();

	public static final GenericResourceType DIAMOND = ResourceType.builder("diamond")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build();

	public static final GenericResourceType EMERALD = ResourceType.builder("emerald")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build();
}
