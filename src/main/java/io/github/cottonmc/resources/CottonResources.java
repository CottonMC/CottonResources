package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.logging.Ansi;
import io.github.cottonmc.cotton.logging.ModLogger;
import io.github.cottonmc.resources.config.CottonResourcesConfig;
import net.fabricmc.api.ModInitializer;

public class CottonResources implements ModInitializer {

	public static final String MODID = "cotton-resources";

	public static ModLogger logger = new ModLogger(MODID, "COTTON RESOURCES");
	public static CottonResourcesConfig config;

	@Override
	public void onInitialize() {
		logger.setPrefixFormat(Ansi.Yellow);
		config = ConfigManager.loadConfig(CottonResourcesConfig.class);
		CommonResources.initialize();
		logger.info("cotton-resources initialized.");

	}
}
