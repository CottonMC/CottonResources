package io.github.cottonmc.resources;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CommonResources {
    private static String[] MACHINE_AFFIXES = new String[] {"gear", "plate"};
    private static String[] RADIOACTIVE_AFFIXES = new String[] {"dust"};
    
    private static final Map<String, ResourceType> BUILTINS = new HashMap<>();
    
    static {
        builtinMetal("copper", BlockSuppliers.STONE_TIER_ORE, MACHINE_AFFIXES);
        builtinMetal("silver", BlockSuppliers.IRON_TIER_ORE,  MACHINE_AFFIXES);
        builtinMetal("lead",   BlockSuppliers.IRON_TIER_ORE,  MACHINE_AFFIXES);
        builtinMetal("zinc",   BlockSuppliers.STONE_TIER_ORE);
        builtinMetal("uranium",BlockSuppliers.IRON_TIER_ORE); //TODO: Registers ingot, nugget, and block. Should it?
        
        builtinMetal("steel",    null, MACHINE_AFFIXES);
        builtinMetal("brass",    null, MACHINE_AFFIXES);
        builtinMetal("electrum", null); //no ore, no gears/plates
        
        builtinItem("coal", "dust");
        BUILTINS.put("coal_coke", new GenericResourceType("coal_coke").withBlockAffix("block", BlockSuppliers.METAL_BLOCK).withAffixes(""));
        builtinItem("mercury");
        
        //These might get rods or molten capsules. They'd just need to be added to the end.
        builtinItem("uranium",   RADIOACTIVE_AFFIXES);
        builtinItem("plutonium", RADIOACTIVE_AFFIXES);
        builtinItem("thorium",   RADIOACTIVE_AFFIXES);
    }

    private static void builtinMetal(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
        MetalResourceType result = new MetalResourceType(id).withOreSupplier(oreSupplier);
        if (extraAffixes.length>0) result.withItemAffixes(extraAffixes);
        
        BUILTINS.put(id, result);
    }

    private static void builtinItem(String id, String... extraAffixes) {
        ItemResourceType result = new ItemResourceType(id, extraAffixes);
        if (extraAffixes.length==0) result.withAffixes(""); //This is just a base type with no affixes, like "mercury".
        BUILTINS.put(id, result);
    }

    /**Registers a Block based on its name if it is supported, or returns it if it already exists.
     * @param name The name of the bloc.
     * @return The Block associated with the given name, or null if the name is not supported.
     */
    @Nullable
    public static Block provideBlock(String name){
        Identifier id = new Identifier("cotton", name);
        if (Registry.BLOCK.contains(id)) {
            //It exists, get it from the registry
            return Registry.BLOCK.get(id);
        }

        for(ResourceType type : BUILTINS.values()) {
            if (type.governs(name)) return type.getBlock(name);
        }

        return null;
    }

    /**Registers an Item based on its name if it is supported, or returns it if it already exists.
     * @param name The name of the item.
     * @return The Item associated with the given name, or null if the name is not supported.
     */
    @Nullable
    public static Item provideItem(String name){
        Identifier id = new Identifier("cotton", name);
        if (Registry.ITEM.contains(id)) {
            //It exists, get it from the registry
            return Registry.ITEM.get(id);
        }

        for(ResourceType type : BUILTINS.values()) {
            if (type.governs(name)) return type.getItem(name);
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
