package io.github.cottonmc.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.github.cottonmc.cotton.registry.CommonBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
    public boolean governs(String itemName) {
        if (itemName.equals(name) && affixes.contains("")) return true; //matches empty affix
        if (!itemName.startsWith(getDomain()+"_")) return false; //not even our prefix
        String affix = getAffix(itemName);
        
        return (affixes.contains(affix) || blockAffixes.keySet().contains(affix));
    }

    @Override
    public Item getItem(String itemName) {
        Identifier id = new Identifier("cotton", itemName); //TODO: Replace this with a Cotton call
        if (Registry.ITEM.contains(id)) {
            //It exists, get it from the registry
            return Registry.ITEM.get(id);
        }
        
        if (!itemName.startsWith(getDomain())) return null; //Reject creating things we don't own
        
        //Prefer blocks if present
        String affix = getAffix(itemName);
        Supplier<Block> blockSupplier = blockAffixes.get(affix);
        if (blockSupplier!=null) return CommonBlocks.register(itemName, blockSupplier.get()).getItem();
        
        //Fallback to items
        return super.getItem(itemName);
    }
    
    @Override
    public Block getBlock(String blockName) {
        Identifier id = new Identifier("cotton", blockName); //TODO: Replace this with a Cotton call
        if (Registry.BLOCK.contains(id)) {
            //It exists, get it from the registry
            return Registry.BLOCK.get(id);
        }
        
        String affix = getAffix(blockName);
        Supplier<Block> blockSupplier = blockAffixes.get(affix);
        return (blockSupplier==null) ? null : CommonBlocks.register(blockName, blockSupplier.get());
    }
    
    @Override
    public void registerAllBlocks() {
        for(String affix : blockAffixes.keySet()) {
            getBlock(getDomain()+"_"+affix);
        }
    }
}
