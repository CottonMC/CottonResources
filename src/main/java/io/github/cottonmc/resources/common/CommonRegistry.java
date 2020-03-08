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

package io.github.cottonmc.resources.common;

import io.github.cottonmc.resources.CottonResources;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CommonRegistry {
	/**
	 * Attempts to get a common item by name. If no item with this name was found register the given item and return it.
	 *
	 * @param name The item name to look for. This is the path and does not include the namespace.
	 * @param item A item that will be registered if no item with this name was found.
	 * @return Returns either an already existing item with the specified name or a new one that was register under the given name.
	 */
	public static Item register(String name, Item item) {
		Identifier id = CottonResources.common(name);

		Item existing = Registry.ITEM.getOrEmpty(id).orElse(null);

		if (existing == null || existing == Items.AIR) {
			Registry.register(Registry.ITEM, id, item);
			return item;
		} else {
			return existing;
		}
	}

	/**
	 * Checks if a Common Item with the given name exists and returns it.
	 *
	 * @param name The name to look for.
	 * @return Either the item if it is found or null if no such Common Item exists
	 */
	public static Item getItem(String name) {
		Identifier id = CottonResources.common(name);

		Item existing = Registry.ITEM.getOrEmpty(id).orElse(null);

		if (existing == null || existing == Items.AIR) {
			return null;
		} else {
			return existing;
		}
	}

	/**
	 * Attempts to get a common block by name. If no block with this name was found register the given block and create a BlockItem for it.
	 *
	 * @param name  The block name to look for. This is the path and does not include the namespace.
	 * @param block A block that will be registered if no block with this name was found.
	 * @return Returns either an already existing block with the specified name or a new one that was register under the given name.
	 */
	public static Block register(String name, Block block) {
		BlockItem item = new BlockItem(block, CottonResourcesItemGroup.ITEM_GROUP_SETTINGS);
		return register(name, block, item);
	}

	/**
	 * Attempts to get a common block by name. If no block with this name was found register the given Block and BlockItem.
	 *
	 * @param name  The block name to look for. This is the path and does not include the namespace.
	 * @param block A block that will be registered if no block with this name was found.
	 * @param item  The settings of the new Blocks BlockItem.
	 * @return Returns either an already existing block with the specified name or a new one that was register under the given name.
	 */
	public static Block register(String name, Block block, BlockItem item) {
		Identifier id = CottonResources.common(name);

		if (!Registry.BLOCK.getOrEmpty(id).isPresent()) {
			Registry.register(Registry.BLOCK, id, block);
			register(id.getPath(), item);

			return block;
		} else {
			return Registry.BLOCK.get(id);
		}
	}

	/**
	 * Checks if a Common Block with the given name exists and returns it.
	 *
	 * @param name The name to look for.
	 * @return Either the block if it is found or null if no such Common Block exists
	 */
	public static Block getBlock(String name) {
		Identifier id = CottonResources.common(name);

		if (!Registry.BLOCK.getOrEmpty(id).isPresent()) {
			return Registry.BLOCK.get(id);
		}

		return null;
	}
}
