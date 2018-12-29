package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.logging.Ansi;
import io.github.cottonmc.cotton.logging.ModLogger;
import io.github.cottonmc.resources.block.DefaultBlocks;
import io.github.cottonmc.resources.config.CottonResourcesConfig;
import io.github.cottonmc.resources.item.DefaultItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CottonResources implements ModInitializer {

	ModLogger logger = new ModLogger("CottonResources", "COTTON RESOURCES");
	CottonResourcesConfig config;

	@Override
	public void onInitialize() {
		logger.setPrefixFormat(Ansi.Blue.and(Ansi.Bold));

		config = ConfigManager.loadConfig(CottonResourcesConfig.class);



		config.enabledBlocks.forEach(blockName -> {
			Block res = requestBlock(blockName);
			if (res == null) {
				logger.info("Failed to load Block requested by config: " + blockName);
			}
		});


		config.enabledItems.forEach(itemName -> {
			Item res = requestItem(itemName);
			if (res == null) {
				logger.info("Failed to load Item requested by config: " + itemName);
			}
		});

	}



	/**This method will check if the given name is known to cotton. If so it will attempt to register said
	 * block, if it hasn't already been registered, and return it.
	 * @param name The block name to look for
	 * @return Either the block if it is supported or null if no block was found for the given name.
	 */
	public static Block requestBlock(String name){
		//I put this in a separate class since it's probably going to become massive
		return DefaultBlocks.provide(name);
	}

	/**This method will check if the given name is known to cotton. If so it will attempt to register said
	 * item, if it hasn't already been registered, and return it.
	 * @param name The item name to look for
	 * @return Either the item if it is supported or null if no item was found for the given name.
	 */
	public static Item requestItem(String name){
		//I put this in a separate class since it's probably going to become massive
		return DefaultItems.provide(name);
	}
}
