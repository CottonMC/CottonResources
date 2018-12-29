package io.github.cottonmc.resources;

import java.util.function.Supplier;

import net.minecraft.block.Block;

public class MetalResourceType extends GenericResourceType {
    protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;

    public MetalResourceType(String name) {
        super(name);
        withItemAffixes("ingot", "nugget", "dust");
        withBlockAffix("block", BlockSuppliers.METAL_BLOCK);
    }

    public MetalResourceType withItemAffixes(String... affixes) {
        for(String affix : affixes) this.affixes.add(affix);
        return this;
    }

    public MetalResourceType withOreSupplier(Supplier<Block> oreSupplier) {
        withBlockAffix("ore", oreSupplier);
        return this;
    }

    @Override
    public String getDomain() {
        return name;
    }
}
