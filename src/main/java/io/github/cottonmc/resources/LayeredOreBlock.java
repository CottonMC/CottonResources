package io.github.cottonmc.resources;

import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.OreBlock;

public class LayeredOreBlock extends OreBlock {

    public LayeredOreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
