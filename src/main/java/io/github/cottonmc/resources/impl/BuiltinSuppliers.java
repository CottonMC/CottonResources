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
package io.github.cottonmc.resources.impl;

import io.github.cottonmc.resources.api.Color;
import io.github.cottonmc.resources.api.MippedOreBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BuiltinSuppliers {
    public static Block makeTieredOre(int tier) {
        return new MippedOreBlock(FabricBlockSettings.of(Material.STONE)
                .hardness(3.0f)
                .resistance(3.0f)
                .breakByTool(FabricToolTags.PICKAXES, tier)
        );
    }
    public static Block makeGlowingTieredOre(int tier, Color color, int litLightLevel) {
        return new MippedOreBlock.Glowing(FabricBlockSettings.of(Material.STONE)
                .hardness(3.0f)
                .resistance(3.0f)
                .breakByTool(FabricToolTags.PICKAXES, tier)
                .luminance(createLightLevelFromBlockState(litLightLevel)),
                color
        );
    }
    public static final Supplier<Block> METAL_STORAGE_BLOCK_SUPP = () ->
            new Block(FabricBlockSettings.of(Material.METAL)
                    .sounds(BlockSoundGroup.METAL)
                    .hardness(5.0f)
                    .resistance(6.0f)
            );
    public static final Supplier<Block> STONY_STORAGE_BLOCK_SUPP = () ->
            new Block(FabricBlockSettings.of(Material.STONE)
                    .hardness(5.0f)
                    .resistance(6.0f)
            );
    public static final Supplier<Item> MISC_SUPP = () ->
            new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Supplier<Item.Settings> BUILDING_BLOCK = () ->
            new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS);

    public static ToIntFunction<BlockState> createLightLevelFromBlockState(int litLevel) {
        return (blockState) -> {
            return (Boolean)blockState.get(Properties.LIT) ? litLevel : 0;
        };
    }
}
