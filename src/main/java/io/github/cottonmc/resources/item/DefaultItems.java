package io.github.cottonmc.resources.item;

import io.github.cottonmc.cotton.Cotton;
import io.github.cottonmc.cotton.registry.CommonBlocks;
import io.github.cottonmc.cotton.registry.CommonItems;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class DefaultItems {

    /**Registers an Item based on its name if it is supported or returns it if it already exists.
     * @param name The name of the block that shall be registered if the item hasn't already been registered.
     * @return The Item associated with the given name or null if the name is not supported.
     */
    public static Item provide(String name){
        switch(name){
            case "copper_ingot":
                return CommonItems.register(name, new Item((new Item.Settings()).itemGroup(Cotton.commonGroup)));
            //...
            default:
                return null; //TODO: If we add a getItem method to CommonItems we could call it here.
        }
    }
}
