package io.github.cottonmc.resources;

import net.minecraft.block.Block;

import java.util.function.Supplier;

public class GemResourceType extends GenericResourceType {
    protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;

    public GemResourceType(String name) {
        super(name);
        withItemAffixes("", "dust");
        withBlockAffix("block", BlockSuppliers.METAL_BLOCK);
    }

    public GemResourceType withOreSupplier(Supplier<Block> oreSupplier) {
        withBlockAffix("ore", oreSupplier);
        return this;
    }

}
