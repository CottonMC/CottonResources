package io.github.cottonmc.resources;


import io.github.cottonmc.cotton.Cotton;
import io.github.cottonmc.cotton.datapack.recipe.RecipeUtil;
import io.github.cottonmc.resources.oregen.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommonResources {
    private static String[] MACHINE_AFFIXES = new String[] {"gear", "plate"};
    private static String[] RADIOACTIVE_AFFIXES = new String[] {"dust"};

    private static final Map<String, ResourceType> BUILTINS = new HashMap<>();

    public static void initialize() {
        builtinMetal("copper", BlockSuppliers.STONE_TIER_ORE, MACHINE_AFFIXES);
        builtinMetal("silver", BlockSuppliers.IRON_TIER_ORE,  MACHINE_AFFIXES);
        builtinMetal("lead",   BlockSuppliers.IRON_TIER_ORE,  MACHINE_AFFIXES);
        builtinMetal("zinc",   BlockSuppliers.STONE_TIER_ORE);
        builtinMetal("aluminum", BlockSuppliers.IRON_TIER_ORE);
        builtinMetal("cobalt", BlockSuppliers.IRON_TIER_ORE);
        builtinMetal("tin", BlockSuppliers.STONE_TIER_ORE);
        builtinMetal("tungsten", BlockSuppliers.IRON_TIER_ORE);

        builtinMetal("platinum", BlockSuppliers.IRON_TIER_ORE);
        builtinMetal("palladium", BlockSuppliers.IRON_TIER_ORE);
        builtinMetal("osmium", BlockSuppliers.IRON_TIER_ORE);
        builtinMetal("iridium", BlockSuppliers.IRON_TIER_ORE);

        builtinMetal("uranium",   BlockSuppliers.IRON_TIER_ORE);

        builtinMetal("steel",    null, MACHINE_AFFIXES);
        builtinMetal("brass",    null, MACHINE_AFFIXES);
        builtinMetal("electrum", null); //no ore, no gears/plates

        builtinItem("coal", "dust");
        BUILTINS.put("coal_coke", new GenericResourceType("coal_coke").withBlockAffix("block", BlockSuppliers.COAL_BLOCK).withItemAffixes(""));
        builtinItem("mercury");
        
        builtinItem("iron", "gear", "plate", "dust");
        builtinItem("gold", "gear", "plate", "dust");

        //These might get rods or molten capsules. They'd just need to be added to the end.
        builtinItem("plutonium", RADIOACTIVE_AFFIXES);
        builtinItem("thorium",   RADIOACTIVE_AFFIXES);

        for (ResourceType resource : BUILTINS.values()) {
            if (CottonResources.config.ores.get(resource.getBaseResource()).enabled) {
                    // || IsResourceRequestedBymodJson?
                resource.registerAll();
            }
            else {
                resource.registerAll();
                nullifyRecipes(resource);
            }
        }
        OreGeneration.registerOres();
    }

    private static void builtinMetal(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
        if (Cotton.isDevEnv) CottonResources.logger.info("registering " + id);
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


    /** TODO from json pls */
    public static void requestResource(String name) {

    }

    // nullify recipes for metals not currently enabled
    private static void nullifyRecipes(ResourceType resource) {
        if (resource instanceof MetalResourceType) {
            MetalResourceType metal = (MetalResourceType)resource;
            RecipeUtil.removeRecipe(new Identifier(Cotton.SHARED_NAMESPACE, metal.name + "_block"));
            RecipeUtil.removeRecipe(new Identifier(Cotton.SHARED_NAMESPACE, metal.name + "_ingot"));
            RecipeUtil.removeRecipe(new Identifier(Cotton.SHARED_NAMESPACE, metal.name + "_ingot_from_blasting"));
            RecipeUtil.removeRecipe(new Identifier(Cotton.SHARED_NAMESPACE, metal.name + "_ingot_from_" + metal.name + "_block"));
            RecipeUtil.removeRecipe(new Identifier(Cotton.SHARED_NAMESPACE, metal.name + "_ingot_from_nuggets"));
            RecipeUtil.removeRecipe(new Identifier(Cotton.SHARED_NAMESPACE, metal.name + "_nugget"));
        }
    }
}
