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

package io.github.cottonmc.resources.compat;

import com.google.common.collect.ImmutableSet;
import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.oregen.OregenResourceListener;
import io.github.cottonmc.resources.type.ResourceType;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.util.version.VersionParsingException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Set;

public class REICompat implements REIPluginV0 {
	public static final Identifier ID = CottonResources.resources("disable_items");

	private static final Set<String> IMMUNE_TO_HIDING = ImmutableSet.<String>of(
			"wood", "stone", "iron", "gold", "diamond"
	);

	@Override
	public Identifier getPluginIdentifier() {
		return ID;
	}

	@Override
	public void registerEntries(EntryRegistry entryRegistry) {
		CottonResources.LOGGER.info("Configuring resource visibility");
		recheckItemHiding(entryRegistry.getStacksList());
	}

	public void recheckItemHiding(List<EntryStack> list) {
		for (ResourceType rsrc : CottonResources.BUILTINS.values()) {
			if (IMMUNE_TO_HIDING.contains(rsrc.getBaseResource())) continue;

			CottonResources.LOGGER.info("Not hiding: " + OregenResourceListener.getConfig().ores);
			boolean enabled = OregenResourceListener.getConfig().ores.contains(rsrc.getBaseResource());

			for (String affix : rsrc.getAffixes()) {
				Item item = rsrc.getItem(affix);

				if (item == null || item.equals(Items.AIR)) {
					CottonResources.LOGGER.info(rsrc.getBaseResource() + "_" + affix + " does not exist?!");
					continue;
				}

				ItemStack stack = new ItemStack(item);

				boolean add = enabled;

				for (int i = 0; i < list.size(); i++) {
					EntryStack entry = list.get(i);

					if (entry.getType() == EntryStack.Type.ITEM) {
						ItemStack listItem = entry.getItemStack();

						if (listItem.getItem() == item) {
							add = false;

							if (!enabled) {
								//CottonResources.LOGGER.info("Removing "+item.getTranslationKey());
								list.remove(i);
								break;
							} else {
								//CottonResources.LOGGER.info("Letting stay "+item.getTranslationKey());
								break;
							}
						}
					}
				}

				//if (add) {
				//CottonResources.LOGGER.info("Re-adding "+item.getTranslationKey());
				//	list.add(Entry.create(new ItemStack(item)));
				//}
			}
		}
	}

	@Override
	public SemanticVersion getMinimumVersion() throws VersionParsingException {
		return SemanticVersion.parse("3.0-pre");
	}
}
