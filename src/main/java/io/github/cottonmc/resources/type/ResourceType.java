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

package io.github.cottonmc.resources.type;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nullable;

public interface ResourceType {
	static ResourceTypeBuilder builder(String resourceName) {
		return new ResourceTypeBuilder(resourceName);
	}

	/**
	 * Gets the base resource name. For example, "copper".
	 */
	String getBaseResource();

	/**
	 * Finds out whether this object takes responsibility for creating and registering the given block or item name.
	 * For instance, a resource with the domain "copper" will contain "copper_ingot" and "copper_block"; and a
	 * resource with the domain "mercury" will govern an item named "mercury".
	 */
	default boolean contains(String itemName) {
		return itemName.startsWith(getBaseResource() + "_");
	}

	/**
	 * Returns the full name of the block/item for the given affix. For example, given "ore", returns "copper_ore",
	 * if this is a copper resource. If the affix is empty, only the base resource name is returned.
	 */
	default String getFullNameForAffix(String affix) {
		return affix.equals("") ? getBaseResource() : getBaseResource() + "_" + affix;
	}

	/**
	 * Gets the item (or BlockItem!) corresponding to this item name. If it's already defined, returns the
	 * already-defined one. Only if it's neither registered nor builtin is null returned.
	 * For example, if you want to get cotton:copper_ingot, and baseResource is copper,
	 * you pass "ingot" in, since that is the affix.
	 */
	@Nullable
	Item getItem(String itemName);

	Optional<Item> getGear();

	Optional<Item> getDust();

	Optional<Item> getNugget();

	Optional<Item> getPlate();

	Optional<Block> getBlock();

	Optional<Block> getOre();

	Optional<Block> getNetherOre();

	Optional<Block> getEndOre();

	/**
	 * Gets the block corresponding to this block name. If it's already defined, returns the already-defined one. If
	 * it's a builtin, registers and returns the builtin. Only if it's neither registered nor builtin is null returned.
	 * For example, if you want to get cotton:copper_ore, and baseResource is copper,
	 * * you pass "ore" in, since that is the affix.
	 */
	@Nullable
	Block getBlock(String blockName);

	/**
	 * Gets all known/builtin affixes for this item.
	 *
	 * @return a collection of affixes which are guaranteed to be known for the purposes of contains and getItem
	 */
	Collection<String> getAffixes();

	/**
	 * Registers all blocks and items of this type that haven't already been registered.
	 */
	void registerAll();
}
