package io.github.cottonmc.resources;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public interface ResourceType {
    /** Gets the base resource name. */
    String getBaseResource();

    /** Finds out whether this object takes responsibility for creating and registering the given block or item name.
     * For instance, a resource with the domain "copper" will contain "copper_ingot" and "copper_block"; and a
     * resource with the domain "mercury" will govern an item named "mercury".
     */
    default boolean contains(String itemName) {
        return itemName.startsWith(getBaseResource()+"_");
    }

    /** 
     * Gets the item (or BlockItem!) corresponding to this item name. If it's already defined, returns the
     * already-defined one. If it's a builtin, registers and returns the builtin. Only if it's neither registered nor
     * builtin is null returned.
     */
    @Nullable
    Item getItem(String itemName);

    /** 
     * Gets the block corresponding to this block name. If it's already defined, returns the already-defined one. If
     * it's a builtin, registers and returns the builtin. Only if it's neither registered nor builtin is null returned.
     */
    @Nullable
    Block getBlock(String blockName);

    /** Registers all non-block builtin items of this type that haven't already been registered */
    void registerAllItems();
    /** Registers all blocks (and their associated ItemBlocks) of this type that haven't already been registered. */
    void registerAllBlocks();
    
    /** Registers all blocks and items of this type that haven't already been registered */
    default void registerAll() {
        registerAllItems();
        registerAllBlocks();
    }
}
