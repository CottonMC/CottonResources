package io.github.cottonmc.resources.type;

import io.github.cottonmc.resources.BlockSuppliers;
import net.minecraft.block.Block;

import java.util.function.Supplier;

public class MetalResourceType extends GenericResourceType {
    protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;

    public MetalResourceType(String name) {
        super(name);
        withItemAffixes("ingot", "nugget", "dust");
        withBlockAffix("block", BlockSuppliers.METAL_BLOCK);
    }

    public MetalResourceType withOreSupplier(Supplier<Block> oreSupplier) {
        withBlockAffix("ore", oreSupplier);
        return this;
    }

}
