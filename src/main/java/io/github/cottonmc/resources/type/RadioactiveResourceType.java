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

import io.github.cottonmc.resources.block.BlockSuppliers;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class RadioactiveResourceType extends GenericResourceType {
	protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;

	public RadioactiveResourceType(String name) {
		super(name);
		//withItemAffixes("dust");
		//withBlockAffix("block", BlockSuppliers.METAL_BLOCK);
	}

	public Optional<Item> getRadioactive() {
		return Optional.ofNullable(this.getItem(""));
	}

	private RadioactiveResourceType withOreSupplier(Supplier<Block> oreSupplier) {
		this.oreSupplier = oreSupplier;
		return this;
	}

	public static class Builder extends ResourceTypeBuilder.Builder<RadioactiveResourceType.Builder> {
		private String affix;

		public Builder(String resourceName) {
			super(resourceName);
		}

		public RadioactiveResourceType.Builder itemAffixName(String itemAffix) {
			this.affix = itemAffix;
			return this;
		}

		public RadioactiveResourceType build() {
			RadioactiveResourceType radioactive = new RadioactiveResourceType(this.resourceName);
			List<String> affixes = new ArrayList<>();

			if (affix != null) {
				affixes.add(affix);
			} else {
				affixes.add("");
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

			radioactive.withItemAffixes(affixes.toArray(new String[0]));

			if (!this.noBlock) {
				radioactive.withBlockAffix("block", this.blockSupplier);
			}

			if (this.overworldOre || this.netherOre || this.endOre) {
				radioactive.withOreSupplier(this.oreSupplier);
			}

			if (this.overworldOre) {
				radioactive.withBlockAffix("ore", this.oreSupplier);
			}

			if (this.netherOre) {
				radioactive.withBlockAffix("nether_ore", this.oreSupplier);
			}

			if (this.endOre) {
				radioactive.withBlockAffix("end_ore", this.oreSupplier);
			}

			return radioactive;
		}
	}
}
