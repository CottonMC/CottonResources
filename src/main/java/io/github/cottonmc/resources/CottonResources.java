package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.resources.config.CottonResourcesConfig;
import net.fabricmc.api.ModInitializer;

public class CottonResources implements ModInitializer {

	CottonResourcesConfig config;

	@Override
	public void onInitialize() {
		config = ConfigManager.loadConfig(CottonResourcesConfig.class);

		config.enabledBlocks.forEach(blockName -> {
			CommonResources.requestBlock(blockName);
		});

		config.enabledItems.forEach(itemName -> {
			CommonResources.requestItem(itemName);
		});

	}
}
