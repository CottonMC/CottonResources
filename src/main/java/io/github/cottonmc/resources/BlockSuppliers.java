package io.github.cottonmc.resources;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.FabricItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

import java.util.function.Supplier;

public class BlockSuppliers {
    /** A generic metal ore supplier, similar to the properties of coal or iron ore. */
    public static final Supplier<Block> STONE_TIER_ORE = () -> new LayeredOreBlock(FabricBlockSettings.of(Material.STONE)
            .hardness(3.0f)
            .resistance(3.0f)
            .build());
    /** A resilient metal ore supplier, similar to the properties of redstone ore. */
    public static final Supplier<Block> IRON_TIER_ORE = () -> new LayeredOreBlock(FabricBlockSettings.of(Material.STONE)
            .hardness(3.0f)
            .resistance(3.0f)
            .breakByTool(FabricItemTags.PICKAXES, 2)
            .build());
    /** An even harder ore supplier, like diamond ore, and uranium ore. */
    public static final Supplier<Block> DIAMOND_TIER_ORE = () -> new LayeredOreBlock(FabricBlockSettings.of(Material.STONE)
            .hardness(3.0f)
            .resistance(3.0f)
            .breakByTool(FabricItemTags.PICKAXES, 3)
            .build());
    /** A generic metal block supplier, based on the properties from iron_block */
    public static final Supplier<Block> METAL_BLOCK = () -> new Block(FabricBlockSettings.of(Material.METAL)
            .hardness(5.0f)
            .resistance(6.0f)
            .build());
    /** A block supplier based on coal_block. */
    public static final Supplier<Block> COAL_BLOCK = () -> new Block(FabricBlockSettings.of(Material.STONE)
            .hardness(5.0f)
            .resistance(6.0f)
            .build());
}
