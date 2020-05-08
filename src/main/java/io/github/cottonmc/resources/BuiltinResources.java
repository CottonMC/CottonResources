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
import net.minecraft.util.registry.Registry;

/**
 * An enumeration of all Built-in resources provided by Cotton-Resources.
 */
public final class BuiltinResources {
	public static final MetalResourceType COPPER = register(ResourceTemplates.fullMetalType("copper").oreSupplier(BlockSuppliers.STONE_TIER_ORE).build());
	public static final MetalResourceType SILVER = register(ResourceTemplates.fullMetalType("silver").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final MetalResourceType LEAD = register(ResourceTemplates.fullMetalType("lead").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final MetalResourceType ZINC = register(ResourceTemplates.fullMetalType("zinc").oreSupplier(BlockSuppliers.STONE_TIER_ORE).build());
	public static final MetalResourceType ALUMINUM = register(ResourceTemplates.fullMetalType("aluminum").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final MetalResourceType COBALT = register(ResourceTemplates.fullMetalType("cobalt").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());

	public static final MetalResourceType TIN = register(ResourceTemplates.fullMetalType("tin").oreSupplier(BlockSuppliers.STONE_TIER_ORE).build());
	public static final MetalResourceType TITANIUM = register(ResourceTemplates.fullMetalType("titanium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final MetalResourceType TUNGSTEN = register(ResourceTemplates.fullMetalType("tungsten").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());

	public static final MetalResourceType PLATINUM = register(ResourceTemplates.fullMetalType("platinum").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final MetalResourceType PALLADIUM = register(ResourceTemplates.fullMetalType("palladium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final MetalResourceType OSMIUM = register(ResourceTemplates.fullMetalType("osmium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final MetalResourceType IRIDIUM = register(ResourceTemplates.fullMetalType("iridium").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());

	public static final MetalResourceType STEEL = register(ResourceTemplates.metalTypeNoOre("steel").build());
	public static final MetalResourceType BRASS = register(ResourceTemplates.metalTypeNoOre("brass").build());
	public static final MetalResourceType ELECTRUM = register(ResourceTemplates.metalTypeNoOre("electrum").build());
	public static final MetalResourceType BRONZE = register(ResourceTemplates.metalTypeNoOre("bronze").build());

	public static final GenericResourceType COAL = register(ResourceType.builder("coal")
			.generic()
			.noBlock()
			.withDustAffix()
			.build());

	public static final GenericResourceType COAL_COKE = register(ResourceType.builder("coal_coke")
			.generic()
			.blockSupplier(BlockSuppliers.COAL_BLOCK)
			.noAffix()
			.build());

	public static final GenericResourceType MERCURY = register(ResourceType.builder("mercury")
			.generic()
			.noBlock()
			.noAffix()
			.build());

	public static final RadioactiveResourceType URANIUM = register(ResourceTemplates.radioactiveTypeNoOre("uranium")
			.allOres()
			.itemAffixName("ingot")
			.oreSupplier(BlockSuppliers.RADIOACTIVE_DIAMOND_TIER_ORE)
			.build());

	public static final RadioactiveResourceType THORIUM = register(ResourceTemplates.radioactiveTypeNoOre("thorium")
			.itemAffixName("ingot")
			.build());

	public static final RadioactiveResourceType PLUTONIUM = register(ResourceTemplates.radioactiveTypeNoOre("plutonium")
			.itemAffixName("ingot")
			.build());

	public static final GemResourceType RUBY = register(ResourceTemplates.fullGemType("ruby").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final GemResourceType TOPAZ = register(ResourceTemplates.fullGemType("topaz").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final GemResourceType AMETHYST = register(ResourceTemplates.fullGemType("amethyst").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final GemResourceType PERIDOT = register(ResourceTemplates.fullGemType("peridot").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());
	public static final GemResourceType SAPPHIRE = register(ResourceTemplates.fullGemType("sapphire").oreSupplier(BlockSuppliers.IRON_TIER_ORE).build());

	// Vanilla Resource Types
	// These exist solely to add the gears/plates/dust when applicable.

	public static final GenericResourceType WOOD = register(ResourceType.builder("wood")
			.generic()
			.noBlock()
			.withGearAffix()
			.build());

	public static final GenericResourceType STONE = register(ResourceType.builder("stone")
			.generic()
			.noBlock()
			.withGearAffix()
			.build());

	public static final GenericResourceType IRON = register(ResourceType.builder("iron")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build());

	public static final GenericResourceType GOLD = register(ResourceType.builder("gold")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build());

	public static final GenericResourceType DIAMOND = register(ResourceType.builder("diamond")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build());

	public static final GenericResourceType EMERALD = register(ResourceType.builder("emerald")
			.generic()
			.noBlock()
			.withDustAffix()
			.withMachineAffixes()
			.build());

	private static <T extends ResourceType> T register(T resourceType) {
		return Registry.register(CottonResources.RESOURCE_TYPES, CottonResources.resources(resourceType.getBaseResource()), resourceType);
	}
}
