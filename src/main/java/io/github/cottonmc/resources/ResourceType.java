package io.github.cottonmc.resources;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface ResourceType {
    /** Gets the domain, which is the base resource name. */
    String getDomain();

    default boolean governs(String itemName) {
        return itemName.startsWith(getDomain()+"_");
    }

    @Nullable
    Item getItem(String itemName);

    @Nullable
    Block getBlock(String blockName);

    void registerAllItems();
    void registerAllBlocks();
    default void registerAll() {
        registerAllItems();
        registerAllBlocks();
    }
}
