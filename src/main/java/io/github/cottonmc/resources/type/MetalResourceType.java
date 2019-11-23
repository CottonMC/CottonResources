package io.github.cottonmc.resources.type;

import io.github.cottonmc.resources.BlockSuppliers;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MetalResourceType extends GenericResourceType {
    protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;

    public MetalResourceType(String name) {
        super(name);
    }

    private MetalResourceType withOreSupplier(Supplier<Block> oreSupplier) {
        this.oreSupplier = oreSupplier;
        return this;
    }

    public Optional<Item> getIngot() {
        return Optional.ofNullable(this.getItem("ingot"));
    }

    public static MetalResourceType.Builder builder(String resourceName) {
        return new Builder(resourceName);
    }

    public static class Builder extends ResourceTypeBuilder.Builder<MetalResourceType.Builder> {
        private boolean withIngotAffix;

        public Builder(String resourceName) {
            super(resourceName);
        }

        @Override
        public MetalResourceType.Builder withItemAffixes() {
            super.withItemAffixes();
            this.withIngotAffix = true;
            return this;
        }

        public MetalResourceType.Builder withIngotAffix() {
            this.withIngotAffix = true;
            return this;
        }

        public MetalResourceType build() {
            MetalResourceType metal = new MetalResourceType(this.resourceName);
            List<String> affixes = new ArrayList<>();

            if (this.withIngotAffix) {
                affixes.add("ingot");
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

            metal.withItemAffixes(affixes.toArray(new String[0]));

            if (!this.noBlock) {
                metal.withBlockAffix("block", this.blockSupplier);
            }

            if (this.overworldOre || this.netherOre || this.endOre) {
                metal.withOreSupplier(this.oreSupplier);
            }

            if (this.overworldOre) {
                metal.withBlockAffix("ore", this.oreSupplier);
            }

            if (this.netherOre) {
                metal.withBlockAffix("nether_ore", this.oreSupplier);
            }

            if (this.endOre) {
                metal.withBlockAffix("end_ore", this.oreSupplier);
            }

            return metal;
        }

    }

}
