package io.github.cottonmc.resources;

import java.util.function.Supplier;

import io.github.cottonmc.cotton.registry.CommonBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MetalResourceType extends ItemResourceType {
    protected Supplier<Block> oreSupplier = BlockSuppliers.STONE_TIER_ORE;

    public MetalResourceType(String name) {
        super(name);
        withItemAffixes("ingot", "nugget", "dust");
    }

    public MetalResourceType withItemAffixes(String... affixes) {
        for(String affix : affixes) this.affixes.add(affix);
        return this;
    }

    public MetalResourceType withOreSupplier(Supplier<Block> oreSupplier) {
        this.oreSupplier = oreSupplier;
        return this;
    }

    @Override
    public String getDomain() {
        return name;
    }

    @Override
    public boolean governs(String itemName) {
        if (super.governs(itemName)) return true;
        
        //blocks
        if ((name+"_ore").equals(itemName)) return (oreSupplier!=null);
        if ((name+"_block").equals(itemName)) return true;
        return false;
    }

    @Override
    public Item getItem(String itemName) {
        if (itemName.equals(name+"_ore") || itemName.equals(name+"_block")) {
            Block block = getBlock(itemName);
            return (block==null) ? null : block.getItem();
        }
        
        return super.getItem(itemName);
    }

    @Override
    public Block getBlock(String blockName) {
        Identifier id = new Identifier("cotton", blockName);
        if (Registry.BLOCK.contains(id)) {
            //It exists, get it from the registry
            return Registry.BLOCK.get(id);
        }
        
        if (blockName.equals(name+"_ore")) {
            return (oreSupplier==null) ? null : CommonBlocks.register(blockName, oreSupplier.get());
        } else if (blockName.equals(name+"_block")) {
            return CommonBlocks.register(blockName, BlockSuppliers.METAL_BLOCK.get());
        }
        
        return null;
    }

    @Override
    public void registerAllItems() {
        super.registerAllItems();
    }

    @Override
    public void registerAllBlocks() {
        getBlock(name+"_ore");
        getBlock(name+"_block");
    }
    
}
