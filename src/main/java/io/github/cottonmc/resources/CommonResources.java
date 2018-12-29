package io.github.cottonmc.resources;

import io.github.cottonmc.resources.block.DefaultBlocks;
import io.github.cottonmc.resources.item.DefaultItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CommonResources {

    /**This method will check if the given name is known to cotton-resources. If so it will attempt to register said
     * block, if it hasn't already been registered, and return it.
     * @param name The block name to look for
     * @return Either the block if it is supported or null if no block was found for the given name.
     */
    public static Block requestBlock(String name){
        //I put this in a separate class since it's probably going to become massive
        return DefaultBlocks.provide(name);
    }

    /**This method will check if the given name is known to cotton-resources. If so it will attempt to register said
     * item, if it hasn't already been registered, and return it.
     * @param name The item name to look for
     * @return Either the item if it is supported or null if no item was found for the given name.
     */
    public static Item requestItem(String name){
        //I put this in a separate class since it's probably going to become massive
        return DefaultItems.provide(name);
    }
}
