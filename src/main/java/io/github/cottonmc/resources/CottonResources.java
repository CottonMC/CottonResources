package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.datapack.CottonDatapack;
import io.github.cottonmc.cotton.datapack.recipe.RecipeUtil;
import io.github.cottonmc.cotton.logging.ModLogger;
import io.github.cottonmc.resources.config.CottonResourcesConfig;
import io.github.cottonmc.resources.oregen.OreGeneration;
import io.github.cottonmc.resources.oregen.OreGenerationSettings;
import io.github.cottonmc.resources.oregen.OreVoteConfig;
import io.github.cottonmc.resources.oregen.OregenResourceListener;
import io.github.cottonmc.resources.tag.DimensionTypeTags;
import io.github.cottonmc.resources.type.GemResourceType;
import io.github.cottonmc.resources.type.GenericResourceType;
import io.github.cottonmc.resources.type.MetalResourceType;
import io.github.cottonmc.resources.type.RadioactiveResourceType;
import io.github.cottonmc.resources.type.ResourceType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CottonResources implements ModInitializer {

	public static final String MODID = "cotton-resources";
	public static final ModLogger LOGGER = new ModLogger(MODID, "COTTON RESOURCES");
	public static final CottonResourcesConfig CONFIG = ConfigManager.loadConfig(CottonResourcesConfig.class);
	private static final String[] MACHINE_AFFIXES = new String[]{"gear", "plate"};
	private static final Map<String, ResourceType> BUILTINS = new HashMap<>();
	
	@Override
	public void onInitialize() {
		builtinMetal("copper", BlockSuppliers.STONE_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("silver", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("lead", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("zinc", BlockSuppliers.STONE_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("aluminum", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("cobalt", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("tin", BlockSuppliers.STONE_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("titanium", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("tungsten", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);

		builtinMetal("platinum", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("palladium", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("osmium", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinMetal("iridium", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);

		builtinMetal("steel", null, MACHINE_AFFIXES);
		builtinMetal("brass", null, MACHINE_AFFIXES);
		builtinMetal("electrum", null, MACHINE_AFFIXES);

		builtinItem("coal", "dust");
		BUILTINS.put("coal_coke", new GenericResourceType("coal_coke").withBlockAffix("block", BlockSuppliers.COAL_BLOCK).withItemAffixes(""));
		builtinItem("mercury");

		builtinItem("iron", "gear", "plate", "dust");
		builtinItem("gold", "gear", "plate", "dust");

		//These might get rods or molten capsules. They'd just need to be added to the end.
		builtinRadioactive("uranium", BlockSuppliers.DIAMOND_TIER_ORE, "gear", "plate", "ingot", "nugget");
		builtinRadioactive("plutonium", null, "gear", "plate", "ingot", "nugget");
		builtinRadioactive("thorium", null, "gear", "plate", "ingot", "nugget");

		builtinItem("diamond", "gear", "plate", "dust");
		builtinItem("emerald", "gear", "plate", "dust");

		builtinGem("ruby", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinGem("topaz", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinGem("amethyst", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinGem("peridot", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);
		builtinGem("sapphire", BlockSuppliers.IRON_TIER_ORE, MACHINE_AFFIXES);

		for (ResourceType resource : BUILTINS.values()) {
			OreGenerationSettings oreGenerationSettings = CONFIG.ores.get(resource.getBaseResource());
			if (oreGenerationSettings == null) {
				LOGGER.warn("No ore generation settings found for " + resource.getBaseResource() + " so registering anyway");
				resource.registerAll();
				//} else if (oreGenerationSettings.enabled) {
				//    // || IsResourceRequestedBymodJson?
				//    resource.registerAll();
			} else {
				resource.registerAll();
				nullifyRecipes(resource);
			}
		}
		OreGeneration.registerOres();

		ResourceManagerHelper.get(net.minecraft.resource.ResourceType.SERVER_DATA).registerReloadListener(new OregenResourceListener());
	}

	private static void builtinMetal(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
		//LOGGER.devInfo("registering " + id); //Nope
		MetalResourceType result = new MetalResourceType(id);
		if (oreSupplier != null) result.withOreSupplier(oreSupplier);

		if (extraAffixes.length > 0) {
			result.withItemAffixes(extraAffixes);
		}

		BUILTINS.put(id, result);
	}

	private static void builtinGem(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
		//LOGGER.devInfo("registering " + id); //Pls no
		GemResourceType result = new GemResourceType(id).withOreSupplier(oreSupplier);
		if (extraAffixes.length > 0) {
			result.withItemAffixes(extraAffixes);
		}

		BUILTINS.put(id, result);
	}

	private static void builtinRadioactive(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
		//LOGGER.devInfo("registering " + id); //Pls don't
		RadioactiveResourceType result = new RadioactiveResourceType(id);
		if (oreSupplier != null) result.withOreSupplier(oreSupplier);

		if (extraAffixes.length > 0) {
			result.withItemAffixes(extraAffixes);
		}

		BUILTINS.put(id, result);
	}

	private static void builtinItem(String id, String... extraAffixes) {
		GenericResourceType result = new GenericResourceType(id).withItemAffixes(extraAffixes);
		if (extraAffixes.length == 0) {
			result.withItemAffixes(""); //This is just a base type with no affixes, like "mercury".
		}
		BUILTINS.put(id, result);
	}


	/**
	 * TODO from json pls
	 */
	public static void requestResource(String name) {

	}

	// nullify recipes for metals not currently enabled
	private static void nullifyRecipes(ResourceType resource) {
		if (resource instanceof MetalResourceType) {
			MetalResourceType metal = (MetalResourceType) resource;
			RecipeUtil.removeRecipe(new Identifier(CottonDatapack.SHARED_NAMESPACE, metal.getBaseResource() + "_block"));
			RecipeUtil.removeRecipe(new Identifier(CottonDatapack.SHARED_NAMESPACE, metal.getBaseResource() + "_ingot"));
			RecipeUtil.removeRecipe(new Identifier(CottonDatapack.SHARED_NAMESPACE, metal.getBaseResource() + "_ingot_from_blasting"));
			RecipeUtil.removeRecipe(new Identifier(CottonDatapack.SHARED_NAMESPACE, metal.getBaseResource() + "_ingot_from_" + metal.getBaseResource() + "_block"));
			RecipeUtil.removeRecipe(new Identifier(CottonDatapack.SHARED_NAMESPACE, metal.getBaseResource() + "_ingot_from_nuggets"));
			RecipeUtil.removeRecipe(new Identifier(CottonDatapack.SHARED_NAMESPACE, metal.getBaseResource() + "_nugget"));
		}
	}
}
