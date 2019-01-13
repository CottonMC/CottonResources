package io.github.cottonmc.resources;


import io.github.cottonmc.cotton.registry.CommonBlocks;
import io.github.cottonmc.cotton.registry.CommonItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommonResources {
    private static String[] MACHINE_AFFIXES = new String[] {"gear", "plate"};
    private static String[] RADIOACTIVE_AFFIXES = new String[] {"dust"};

    public static final Map<String, ResourceType> BUILTINS = new HashMap<>();
    
    static {
        builtinMetal("copper", BlockSuppliers.STONE_TIER_ORE, MACHINE_AFFIXES);
        builtinMetal("silver", BlockSuppliers.IRON_TIER_ORE,  MACHINE_AFFIXES);
        builtinMetal("lead",   BlockSuppliers.IRON_TIER_ORE,  MACHINE_AFFIXES);
        builtinMetal("zinc",   BlockSuppliers.STONE_TIER_ORE);
        
        builtinMetal("steel",    null, MACHINE_AFFIXES);
        builtinMetal("brass",    null, MACHINE_AFFIXES);
        builtinMetal("electrum", null); //no ore, no gears/plates
        
        builtinItem("coal", "dust");
        BUILTINS.put("coal_coke", new GenericResourceType("coal_coke").withBlockAffix("block", BlockSuppliers.COAL_BLOCK).withItemAffixes(""));
        builtinItem("mercury");
        
        //These might get rods or molten capsules. They'd just need to be added to the end.
        builtinItem("uranium",   RADIOACTIVE_AFFIXES);
        builtinItem("plutonium", RADIOACTIVE_AFFIXES);
        builtinItem("thorium",   RADIOACTIVE_AFFIXES);
    }

    private static void builtinMetal(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
        MetalResourceType result = new MetalResourceType(id).withOreSupplier(oreSupplier);
        if (extraAffixes.length > 0){
            result.withItemAffixes(extraAffixes);
        }
        
        BUILTINS.put(id, result);
    }

    private static void builtinItem(String id, String... extraAffixes) {
        ItemResourceType result = new ItemResourceType(id, extraAffixes);
        if (extraAffixes.length == 0){
            result.withItemAffixes(""); //This is just a base type with no affixes, like "mercury".
        }
        BUILTINS.put(id, result);
    }

    /**Registers a Block based on its name if it is supported, or returns it if it already exists.
     * @param name The name of the bloc.
     * @return The Block associated with the given name, or null if the name is not supported.
     */
    @Nullable
    public static Block provideBlock(String name){
        Block existing = CommonBlocks.getBlock(name);
        if (existing!=null) return existing;

        for(ResourceType type : BUILTINS.values()) {
            if (type.contains(name)) return type.getBlock(name);
        }

        return null;
    }

    /**Registers an Item based on its name if it is supported, or returns it if it already exists.
     * @param name The name of the item.
     * @return The Item associated with the given name, or null if the name is not supported.
     */
    @Nullable
    public static Item provideItem(String name){
        Item existing = CommonItems.getItem(name);
        if (existing != null) {
            return existing;
        }

        for(ResourceType type : BUILTINS.values()) {
            if (type.contains(name)) return type.getItem(name);
        }

        return null;
    }

    /** 
     * @param name the name of a resource, such as "copper", "coal_coke", or "mercury".
     * @return the ResourceType with this name if it exists, or null if none exists.
     */
    @Nullable
    public static ResourceType provideResource(String name) {
        return BUILTINS.get(name);
    }
}
