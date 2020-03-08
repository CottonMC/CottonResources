/*
 * MIT License
 *
 * Copyright (c) 2018-2020 The Cotton Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.cottonmc.resources.block;

import io.github.cottonmc.resources.CottonResources;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public class LayeredOreBlock extends OreBlock {
	private boolean complainedAboutLoot = false;

	public LayeredOreBlock(Settings settings) {
		super(settings);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		//EARLY DETECTION OF BUSTED LOOT TABLES:
		Identifier tableId = this.getDropTableId();

		if (tableId == LootTables.EMPTY) {
			return Collections.emptyList();
		} else {
			LootContext context = builder.put(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
			ServerWorld world = context.getWorld();
			LootTable lootSupplier = world.getServer().getLootManager().getSupplier(tableId);

			List<ItemStack> result = lootSupplier.getDrops(context);

			if (result.isEmpty()) {
				//This might not be good. Confirm:

				if (lootSupplier instanceof FabricLootSupplier) {
					List<LootPool> pools = ((FabricLootSupplier) lootSupplier).getPools();

					if (pools.isEmpty()) {
						//Yup. Somehow we got a loot pool that just never drops anything.
						if (!complainedAboutLoot) {
							CottonResources.LOGGER.error("Loot pool '" + tableId + "' doesn't seem to be able to drop anything. Supplying the ore block instead. Please report this to the Cotton team!");
							complainedAboutLoot = true;
						}

						result.add(new ItemStack(this.asItem()));
					}
				}
			}

			return result;
		}
	}
}
