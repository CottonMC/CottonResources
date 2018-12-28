package io.github.cottonmc.resources;

import io.github.cottonmc.resources.block.ModBlocks;
import io.github.cottonmc.resources.item.ModItems;
import net.minecraft.item.ItemStack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class CottonResources implements ModInitializer {

	public static final ItemGroup cottonGroup = FabricItemGroupBuilder.build(new Identifier("cotton:cotton_tab"), new Supplier<ItemStack>() {
		@Override
		public ItemStack get() {
			return new ItemStack(ModItems.COPPER_INGOT);
		}
	});

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModItems.init();

	}
}
