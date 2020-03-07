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

import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.block.LayeredOreBlock;
import io.github.cottonmc.resources.common.CommonRegistry;
import io.github.cottonmc.resources.common.CottonResourcesItemGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;

public class GenericResourceType implements ResourceType {
	protected String name;
	protected HashMap<String, Supplier<Block>> blockAffixes = new HashMap<>();
	protected HashSet<String> itemAffixes = new HashSet<>();

	public GenericResourceType(String name) {
		this.name = name;
	}

	public GenericResourceType withBlockAffix(String affix, Supplier<Block> supplier) {
		blockAffixes.put(affix, supplier);
		return this;
	}

	public String getAffixFor(String itemName) {
		return itemName.substring(getBaseResource().length() + 1);
	}

	@Override
	public String getBaseResource() {
		return name;
	}

	public GenericResourceType withItemAffixes(String... affixes) {
		this.itemAffixes.addAll(Arrays.asList(affixes));
		return this;
	}

	@Override
	public boolean contains(String itemName) {
		if (itemName.equals(name) && itemAffixes.contains("")) {
			return true; //matches empty affix
		}

		if (!itemName.startsWith(getBaseResource() + "_")) {
			return false; //not even our prefix
		}

		String affix = getAffixFor(itemName);

		return (itemAffixes.contains(affix) || blockAffixes.keySet().contains(affix));
	}

	@Override
	public Item getItem(String itemName) {
		String fullName = (itemName != null && !itemName.isEmpty()) ? name + "_" + itemName : name;

		Identifier id = CottonResources.common(fullName); //Because CommonItems.getItem has a condition flipped
		return Registry.ITEM.getOrEmpty(id).orElse(null);
	}

	@Override
	public Optional<Item> getGear() {
		return Optional.ofNullable(this.getItem("gear"));
	}

	@Override
	public Optional<Item> getDust() {
		return Optional.ofNullable(this.getItem("dust"));
	}

	@Override
	public Optional<Item> getNugget() {
		return Optional.ofNullable(this.getItem("nugget"));
	}

	@Override
	public Optional<Item> getPlate() {
		return Optional.ofNullable(this.getItem("plate"));
	}

	@Override
	public Optional<Block> getBlock() {
		return Optional.ofNullable(this.getBlock("block"));
	}

	@Override
	public Optional<Block> getOre() {
		return Optional.ofNullable(this.getBlock("ore"));
	}

	@Override
	public Optional<Block> getNetherOre() {
		return Optional.ofNullable(this.getBlock("nether_ore"));
	}

	@Override
	public Optional<Block> getEndOre() {
		return Optional.ofNullable(this.getBlock("end_ore"));
	}

	public Item registerItem(String itemName) {
		return CommonRegistry.register(itemName, new Item(CottonResourcesItemGroup.ITEM_GROUP_SETTINGS));
	}

	@Override
	public Block getBlock(String blockName) {
		Block existing = (blockName != null) ? CommonRegistry.getBlock(name + "_" + blockName) : CommonRegistry.getBlock(name);

		if (existing != null) {
			return existing;
		} else {
			CottonResources.LOGGER.warn("No block found with name " + blockName + "!");
			return null;
		}
	}

	public Block registerBlock(String blockName) {
		String affix = getAffixFor(blockName);
		Supplier<Block> blockSupplier = blockAffixes.get(affix);

		if (blockSupplier == null) {
			return null;
		}

		Block resultBlock = blockSupplier.get();

		if (resultBlock instanceof LayeredOreBlock && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			handleMipped(resultBlock);
		}

		BlockItem resultItem = new BlockItem(resultBlock, CottonResourcesItemGroup.ITEM_GROUP_SETTINGS); //Shouldn't be necessary, but is?

		return CommonRegistry.register(blockName, resultBlock, resultItem);
	}

	public Collection<String> getAffixes() {
		return ImmutableSet.<String>builder().addAll(itemAffixes).addAll(blockAffixes.keySet()).build();
	}

	@Override
	public void registerAll() {
		registerBlocks();
		registerItems();
	}

	public void registerBlocks() {
		for (String affix : blockAffixes.keySet()) {
			registerBlock(getFullNameForAffix(affix));
		}
	}

	public void registerItems() {
		for (String affix : itemAffixes) {
			registerItem(getFullNameForAffix(affix));
		}
	}

	@Environment(EnvType.CLIENT)
	private static void handleMipped(Block block) {
		BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
	}

	public static class Builder extends ResourceTypeBuilder.Builder<GenericResourceType.Builder> {
		private String affix;

		public Builder(String resourceName) {
			super(resourceName);
		}

		public GenericResourceType.Builder noAffix() {
			this.affix = "";
			return this;
		}

		public GenericResourceType.Builder withAffix(String affix) {
			this.affix = affix;
			return this;
		}

		public GenericResourceType build() {
			GenericResourceType generic = new GenericResourceType(this.resourceName);
			List<String> affixes = new ArrayList<>();

			if (this.affix != null) {
				affixes.add(this.affix);
			}

			if (this.nuggetAffix) {
				affixes.add("nugget");
			}

			if (this.dustAffix) {
				affixes.add("dust");
			}

			if (this.gearAffix) {
				affixes.add("gear");
			}

			if (this.plateAffix) {
				affixes.add("plate");
			}

			generic.withItemAffixes(affixes.toArray(new String[0]));

			if (!this.noBlock) {
				generic.withBlockAffix("block", this.blockSupplier);
			}

			if (this.overworldOre) {
				generic.withBlockAffix("ore", this.oreSupplier);
			}

			if (this.netherOre) {
				generic.withBlockAffix("nether_ore", this.oreSupplier);
			}

			if (this.endOre) {
				generic.withBlockAffix("end_ore", this.oreSupplier);
			}

			return generic;
		}
	}
}
