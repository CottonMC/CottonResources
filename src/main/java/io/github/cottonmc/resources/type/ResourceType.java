package io.github.cottonmc.resources.type;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public interface ResourceType {
    /** Gets the base resource name. For example, "copper". */
    String getBaseResource();

    /** Finds out whether this object takes responsibility for creating and registering the given block or item name.
     * For instance, a resource with the domain "copper" will contain "copper_ingot" and "copper_block"; and a
     * resource with the domain "mercury" will govern an item named "mercury".
     */
    default boolean contains(String itemName) {
        return itemName.startsWith(getBaseResource()+"_");
    }

    /**
     *
     * Returns the full name of the block/item for the given affix. For example, given "ore", returns "copper_ore",
     * if this is a copper resource. If the affix is empty, only the base resource name is returned.
     */
    default String getFullNameForAffix(String affix) {
        return affix.equals("") ? getBaseResource() : getBaseResource() + "_" + affix;
    }

    /** 
     * Gets the item (or BlockItem!) corresponding to this item name. If it's already defined, returns the
     * already-defined one. Only if it's neither registered nor builtin is null returned.
     * For example, if you want to get cotton:copper_ingot, and baseResource is copper,
     *      * you pass "ingot" in, since that is the affix.
     */
    @Nullable
    Item getItem(String itemName);

    /** 
     * Gets the block corresponding to this block name. If it's already defined, returns the already-defined one. If
     * it's a builtin, registers and returns the builtin. Only if it's neither registered nor builtin is null returned.
     * For example, if you want to get cotton:copper_ore, and baseResource is copper,
     *      * you pass "ore" in, since that is the affix.
     */
    @Nullable
    Block getBlock(String blockName);
    
    /** Registers all blocks and items of this type that haven't already been registered */
    void registerAll();
}
