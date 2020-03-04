package io.github.cottonmc.resources.type;

import io.github.cottonmc.resources.BlockSuppliers;
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
