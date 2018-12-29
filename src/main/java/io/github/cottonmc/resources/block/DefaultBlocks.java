package io.github.cottonmc.resources.block;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import io.github.cottonmc.cotton.registry.CommonBlocks;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.fabricmc.fabric.tags.FabricItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;

public class DefaultBlocks {
    /** A generic metal ore supplier, similar to the properties of coal or iron ore. */
    private static final Supplier<Block> STONE_TIER_ORE = ()->new OreBlock(FabricBlockSettings.of(Material.STONE)
            .hardness(3.0f)
            .resistance(3.0f)
            .build());
    /** A resilient metal ore provider, similar to the properties of redstone ore. */
    private static final Supplier<Block> IRON_TIER_ORE = ()->new OreBlock(FabricBlockSettings.of(Material.STONE)
            .hardness(3.0f)
            .resistance(3.0f)
            .breakByTool(FabricItemTags.PICKAXES, 2)
            .build());
    /** A generic metal block supplier, based on the properties from iron_block */
    private static final Supplier<Block> METAL_BLOCK = ()->new Block(FabricBlockSettings.of(Material.METAL)
            .hardness(5.0f)
            .resistance(6.0f)
            .build());

    private static final Map<String, Supplier<Block>> BUILTINS = ImmutableMap.<String, Supplier<Block>>builder()
            .put("copper_ore", STONE_TIER_ORE) //Chalcopyrite
            .put("silver_ore", IRON_TIER_ORE)  //Galena
            .put("lead_ore",   IRON_TIER_ORE)  //Galena
            .put("zinc_ore",   STONE_TIER_ORE) //Sphalerite, which is more often found in small amounts mixed with copper, lead, and iron sulfides
            .put("uranium_ore", IRON_TIER_ORE) //Pitchblende
            
            .put("copper_block",    METAL_BLOCK)
            .put("silver_block",    METAL_BLOCK)
            .put("lead_block",      METAL_BLOCK)
            .put("zinc_block",      METAL_BLOCK)
            .put("steel_block",     METAL_BLOCK)
            .put("electrum_block",  METAL_BLOCK)
            .put("brass_block",     METAL_BLOCK)
            .put("coal_coke_block", METAL_BLOCK)
            .build();

    /**Registers an Item based on its name if it is supported or returns it if it already exists.
    * @param name The name of the block that shall be registered.
     * @return The Block associated with the given name or null if no Block for that name was found
     */
    public static Block provide(String name){
        /*//We should add a get to cotton, then we can duck out of this method really early.
        Block existing = CommonBlocks.get(name);
        if (existing!=null) return existing;*/

        Supplier<Block> supplier = BUILTINS.get(name);
        if (supplier!=null) {
            Block toRegister = supplier.get();
            Block registered = CommonBlocks.register(name, toRegister);
            return registered;
        } else {
            return null;
        }
    }
}
