package io.github.cottonmc.resources.type;

import io.github.cottonmc.resources.BlockSuppliers;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class GemResourceType extends GenericResourceType {
    protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;

    public GemResourceType(String name) {
        super(name);
    }

    public Optional<Item> getGem() {
        return Optional.ofNullable(this.getItem(""));
    }

    public static class Builder extends ResourceTypeBuilder.Builder<GemResourceType.Builder> {
        private boolean withGemAffix;

        public Builder(String resourceName) {
            super(resourceName);
        }

        @Override
        public GemResourceType.Builder withItemAffixes() {
            super.withItemAffixes();
            this.withGemAffix = true;
            return this;
        }

        public GemResourceType.Builder withGemAffix() {
            this.withGemAffix = true;
            return this;
        }


        public GemResourceType build() {
            GemResourceType gem = new GemResourceType(this.resourceName);
            List<String> affixes = new ArrayList<>();

            if (this.withGemAffix) {
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

            gem.withItemAffixes(affixes.toArray(new String[0]));

            if (!this.noBlock) {
                gem.withBlockAffix("block", this.blockSupplier);
            }

            if (this.overworldOre) {
                gem.withBlockAffix("ore", this.oreSupplier);
            }

            if (this.netherOre) {
                gem.withBlockAffix("nether_ore", this.oreSupplier);
            }

            if (this.endOre) {
                gem.withBlockAffix("end_ore", this.oreSupplier);
            }

            return gem;
        }
    }
}
