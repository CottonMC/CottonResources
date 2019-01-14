package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.logging.Ansi;
import io.github.cottonmc.cotton.logging.ModLogger;
import io.github.cottonmc.resources.config.CottonResourcesConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CottonResources implements ModInitializer {

	public static ModLogger logger = new ModLogger("CottonResources", "COTTON RESOURCES");
	public static CottonResourcesConfig config;

	@Override
	public void onInitialize() {
		logger.setPrefixFormat(Ansi.Blue);

		config = ConfigManager.loadConfig(CottonResourcesConfig.class);

		config.enabledBlocks.forEach(blockName -> {
			Block res = CommonResources.provideBlock(blockName);
			if (res == null) {
				logger.info("Failed to load Block requested by config: " + blockName);
			}
		});


		config.enabledItems.forEach(itemName -> {
			Item res = CommonResources.provideItem(itemName);
			if (res == null) {
				logger.info("Failed to load Item requested by config: " + itemName);
			}
		});

	}
}
