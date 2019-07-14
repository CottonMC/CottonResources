package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.logging.ModLogger;
import io.github.cottonmc.resources.config.CottonResourcesConfig;
import io.github.cottonmc.resources.oregen.CottonOreFeature;
import io.github.cottonmc.resources.oregen.OregenResourceListener;
import io.github.cottonmc.resources.tag.WorldTagReloadListener;
import io.github.cottonmc.resources.type.GemResourceType;
import io.github.cottonmc.resources.type.GenericResourceType;
import io.github.cottonmc.resources.type.MetalResourceType;
import io.github.cottonmc.resources.type.RadioactiveResourceType;
import io.github.cottonmc.resources.type.ResourceType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;

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
			resource.registerAll();
		}
		
		setupBiomeGenerators(); //add cotton-resources ores to all current biomes
		RegistryEntryAddedCallback.event(Registry.BIOME).register((id, ident, biome)->setupBiomeGenerator(biome)); //Add cotton-resources ores to any later biomes that appear
		
		ResourceManagerHelper.get(net.minecraft.resource.ResourceType.SERVER_DATA).registerReloadListener(new OregenResourceListener());
		ResourceManagerHelper.get(net.minecraft.resource.ResourceType.SERVER_DATA).registerReloadListener(new WorldTagReloadListener());
		
		//TODO: Move to its own StripCommand class
		CommandRegistry.INSTANCE.register(false, (dispatcher)->{
			Command<ServerCommandSource> stripCommand = new Command<ServerCommandSource>() {
				private final Set<Block> TO_STRIP = ImmutableSet.<Block>of(
							Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE,
							Blocks.DIRT, Blocks.GRASS_BLOCK,
							Blocks.ACACIA_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG, Blocks.JUNGLE_LOG, Blocks.SPRUCE_LOG,
							Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LEAVES
						);
				
				@Override
				public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
					
					ServerPlayerEntity caller = context.getSource().getPlayer();
					Chunk chunk = caller.getEntityWorld().getChunk(caller.getBlockPos());
					context.getSource().sendFeedback(new LiteralText("Stripping "+chunk.getPos()+"..."), true);
					
					for(int z=0; z<16; z++) {
						for(int x=0; x<16; x++) {
							for(int y=0; y<256; y++) {
								//TODO: better test for natural stone
								BlockPos pos = chunk.getPos().toBlockPos(x, y, z);
								BlockState toReplace = caller.getEntityWorld().getBlockState(pos);
								if (toReplace.isAir()) continue;
								if (TO_STRIP.contains(toReplace.getBlock())) {
									caller.getEntityWorld().setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
								}
							}
						}
					}
					
					
					context.getSource().sendFeedback(new LiteralText("Chunk stripped."), true);
					return 1;
				}
			};
			
			LiteralCommandNode<ServerCommandSource> stripCommandNode = CommandManager.literal("strip")
					.executes(stripCommand)
					.requires((source)->source.hasPermissionLevel(2))
					.build();
			
			dispatcher.getRoot().addChild(stripCommandNode);
		});
	}
	
	private static void setupBiomeGenerators() {
		for (Biome biome : Registry.BIOME) {
			setupBiomeGenerator(biome);
		}
	}
	
	private static void setupBiomeGenerator(Biome biome) {
		biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
			Biome.configureFeature(
				CottonOreFeature.COTTON_ORE,
				FeatureConfig.DEFAULT,
				Decorator.COUNT_RANGE,
				new RangeDecoratorConfig(1, 0, 0, 256)
			)
		);
	}

	private static void builtinMetal(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
		MetalResourceType result = new MetalResourceType(id);
		if (oreSupplier != null) result.withOreSupplier(oreSupplier);

		if (extraAffixes.length > 0) {
			result.withItemAffixes(extraAffixes);
		}

		BUILTINS.put(id, result);
	}

	private static void builtinGem(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
		GemResourceType result = new GemResourceType(id).withOreSupplier(oreSupplier);
		if (extraAffixes.length > 0) {
			result.withItemAffixes(extraAffixes);
		}

		BUILTINS.put(id, result);
	}

	private static void builtinRadioactive(String id, Supplier<Block> oreSupplier, String... extraAffixes) {
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
	
}
