package io.github.cottonmc.resources;

import io.github.cottonmc.cotton.registry.CommonBlocks;
import io.github.cottonmc.cotton.registry.CommonItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GenericResourceType extends ItemResourceType {
    Map<String, Supplier<Block>> blockAffixes = new HashMap<>();

    public GenericResourceType(String name) {
        super(name);
    }

    public GenericResourceType withBlockAffix(String affix, Supplier<Block> supplier) {
        blockAffixes.put(affix, supplier);
        return this;
    }
    
    @Override
    public boolean contains(String itemName) {
        if (itemName.equals(name) && itemAffixes.contains("")) return true; //matches empty affix
        if (!itemName.startsWith(getBaseResource()+"_")) return false; //not even our prefix
        String affix = getAffix(itemName);
        
        return (itemAffixes.contains(affix) || blockAffixes.keySet().contains(affix));
    }

    @Override
    public Item getItem(String itemName) {
        Item existing = CommonItems.getItem(itemName);
        if (existing!=null) {
            return existing;
        }

        //Reject creating things we don't own
        if (!itemName.startsWith(getBaseResource())) {
            return null;
        }
        
        //Prefer blocks if present
        String affix = getAffix(itemName);
        Supplier<Block> blockSupplier = blockAffixes.get(affix);
        if (blockSupplier!=null) {
            return CommonBlocks.register(itemName, blockSupplier.get()).getItem();
        }
        
        //Fallback to items
        return super.getItem(itemName);
    }
    
    @Override
    public Block getBlock(String blockName) {
        Block existing = CommonBlocks.getBlock(blockName);
        if (existing!=null) return existing;
        
        String affix = getAffix(blockName);
        Supplier<Block> blockSupplier = blockAffixes.get(affix);
        return (blockSupplier==null) ? null : CommonBlocks.register(blockName, blockSupplier.get());
    }
    
    @Override
    public void registerAllBlocks() {
        for(String affix : blockAffixes.keySet()) {
            getBlock(getBaseResource()+"_"+affix);
        }
    }
}
