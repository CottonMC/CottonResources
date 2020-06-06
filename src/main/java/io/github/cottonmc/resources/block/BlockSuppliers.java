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

package io.github.cottonmc.resources.block;

import io.github.cottonmc.resources.CottonResources;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 * Contains all Suppliers used to create the block types used by Cotton-Resources.
 */
public class BlockSuppliers {
	/**
	 * A generic metal ore supplier, similar to the properties of coal or iron ore.
	 */
	public static final Supplier<Block> STONE_TIER_ORE = () -> new LayeredOreBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(3.0f)
			.resistance(3.0f));
	/**
	 * A resilient metal ore supplier, requires an iron like tool level to collect.
	 */
	public static final Supplier<Block> IRON_TIER_ORE = () -> new LayeredOreBlock(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE)
			.hardness(3.0f)
			.resistance(3.0f)
			.breakByTool(FabricToolTags.PICKAXES, 2));
	/**
	 * A resilient metal ore supplier, similar to the properties of redstone ore where it has a glowing state.
	 *
	 * <p>May be used in future for other ores.</p>
	 */
	public static final Supplier<Block> LIGHTABLE_IRON_TIER_ORE = () -> new LayeredOreBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_ORE)
			.hardness(3.0f)
			.resistance(3.0f)
			.breakByTool(FabricToolTags.PICKAXES, 2));
	/**
	 * An extremely resilient metal ore supplier, only harvestable by diamond or better pickaxes.
	 */
	public static final Supplier<Block> DIAMOND_TIER_ORE = () -> new LayeredOreBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(3.0f)
			.resistance(3.0f)
			.breakByTool(FabricToolTags.PICKAXES, 3));
	/**
	 * An extremely resilient metal ore supplier, only harvestable by diamond or better pickaxes.
	 */
	public static final Supplier<Block> RADIOACTIVE_DIAMOND_TIER_ORE = () -> new GlowingLayeredOreBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_ORE)
			.hardness(3.0f)
			.resistance(3.0f)
			.breakByTool(FabricToolTags.PICKAXES, 3)
			.lightLevel(createLightLevelFromBlockState(9)));
	/**
	 * A generic metal block supplier, based on the properties from iron_block.
	 */
	public static final Supplier<Block> METAL_BLOCK = () -> new Block(FabricBlockSettings.of(Material.METAL)
			.sounds(CottonResources.METAL_SOUND_GROUP)
			.hardness(5.0f)
			.resistance(6.0f));
	/**
	 * A block supplier based on coal_block.
	 */
	public static final Supplier<Block> COAL_BLOCK = () -> new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(5.0f)
			.resistance(6.0f));

	public static ToIntFunction<BlockState> createLightLevelFromBlockState(int luminance) {
		return (blockState) -> blockState.get(Properties.LIT) ? luminance : 0;
	}
}
