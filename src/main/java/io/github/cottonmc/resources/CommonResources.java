package io.github.cottonmc.resources;


import net.minecraft.block.Block;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommonResources {
    private static String[] MACHINE_AFFIXES = new String[] {"gear", "plate"};
    private static String[] RADIOACTIVE_AFFIXES = new String[] {"dust"};

    public static final Map<String, ResourceType> BUILTINS = new HashMap<>();

    public static void initialize() {
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

        for (ResourceType resource : BUILTINS.values()) {
            resource.registerAll();
        }
    }

    private static void builtinMetal(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
        CottonResources.logger.info("registering " + id);
        MetalResourceType result = new MetalResourceType(id).withOreSupplier(oreSupplier);
        if (extraAffixes.length > 0){
            result.withItemAffixes(extraAffixes);
        }
        
        BUILTINS.put(id, result);
    }

    private static void builtinItem(String id, String... extraAffixes) {
        GenericResourceType result = new GenericResourceType(id).withItemAffixes(extraAffixes);
        if (extraAffixes.length == 0){
            result.withItemAffixes(""); //This is just a base type with no affixes, like "mercury".
        }
        BUILTINS.put(id, result);
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
