package io.github.cottonmc.resources.block;

import io.github.cottonmc.cotton.registry.CommonBlocks;
import net.minecraft.block.*;
import net.minecraft.block.Block.*;

public class DefaultBlocks {

    /**Registers an Item based on its name if it is supported or returns it if it already exists.
    * @param name The name of the block that shall be registered.
     * @return The Block associated with the given name or null if no Block for that name was found
     */
    public static Block provide(String name){
        switch(name){
            case "copper_ore":
                return CommonBlocks.register(name, new OreBlock(Settings.of(Material.STONE).strength(3.0F, 3.0F)));
            //...
            default:
                return null;
        }
    }
}
