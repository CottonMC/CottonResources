package io.github.cottonmc.resources.type;

import java.util.function.Supplier;
import io.github.cottonmc.resources.BlockSuppliers;
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

		protected boolean noBlock;


		public Builder(String resourceName) {
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
