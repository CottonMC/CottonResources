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

import java.util.function.Supplier;

import io.github.cottonmc.resources.block.BlockSuppliers;
import net.minecraft.block.Block;

public final class ResourceTypeBuilder {
	private final String resourceName;

	public ResourceTypeBuilder(String resourceName) {
		this.resourceName = resourceName;
	}

	public MetalResourceType.Builder metal() {
		return new MetalResourceType.Builder(resourceName);
	}

	public GemResourceType.Builder gem() {
		return new GemResourceType.Builder(resourceName);
	}

	public RadioactiveResourceType.Builder radioactive() {
		return new RadioactiveResourceType.Builder(resourceName);
	}

	public GenericResourceType.Builder generic() {
		return new GenericResourceType.Builder(resourceName);
	}

	abstract static class Builder<B extends Builder<B>> {
		protected final String resourceName;
		// Ore related affixes
		protected boolean overworldOre;
		protected boolean endOre;
		protected boolean netherOre;
		// By default, assume this is a stone tier ore.
		protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;
		protected Supplier<Block> blockSupplier = BlockSuppliers.METAL_BLOCK;
		// Machine related affixes
		protected boolean gearAffix;
		protected boolean plateAffix;
		// Item related affixes.
		protected boolean dustAffix;
		protected boolean nuggetAffix;
		// Blocks?
		protected boolean noBlock;

		protected Builder(String resourceName) {
			this.resourceName = resourceName;
		}

		public B allOres() {
			this.overworldOre = true;
			this.endOre = true;
			this.netherOre = true;
			return (B) this;
		}

		public B overworldOres() {
			this.overworldOre = true;
			return (B) this;
		}

		public B netherOres() {
			this.netherOre = true;
			return (B) this;
		}

		public B endOres() {
			this.endOre = true;
			return (B) this;
		}

		public B oreSupplier(Supplier<Block> oreSupplier) {
			this.oreSupplier = oreSupplier;
			return (B) this;
		}

		public B withMachineAffixes() {
			this.gearAffix = true;
			this.plateAffix = true;
			return (B) this;
		}

		public B withGearAffix() {
			this.gearAffix = true;
			return (B) this;
		}

		public B withPlateAffix() {
			this.plateAffix = true;
			return (B) this;
		}

		public B withItemAffixes() {
			this.dustAffix = true;
			this.nuggetAffix = true;
			// Ingots/Crystals are set within the specific resource types
			return (B) this;
		}

		public B withDustAffix() {
			this.dustAffix = true;
			return (B) this;
		}

		public B withNuggetAffix() {
			this.nuggetAffix = true;
			return (B) this;
		}

		public B noBlock() {
			this.noBlock = true;
			return (B) this;
		}

		public B blockSupplier(Supplier<Block> supplier) {
			this.blockSupplier = supplier;
			return (B) this;
		}
	}
}
