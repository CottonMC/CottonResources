package io.github.cottonmc.resources.type;

import io.github.cottonmc.cotton.commons.CommonBlocks;
import io.github.cottonmc.cotton.commons.CommonItems;
import io.github.cottonmc.cotton.commons.CottonCommons;
import io.github.cottonmc.resources.CottonResources;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;

public class GenericResourceType implements ResourceType {
    protected String name;
    protected HashMap<String, Supplier<Block>> blockAffixes = new HashMap<>();
    protected HashSet<String> itemAffixes = new HashSet<>();

    public GenericResourceType(String name) {
        this.name = name;
    }

    public GenericResourceType withBlockAffix(String affix, Supplier<Block> supplier) {
        blockAffixes.put(affix, supplier);
        return this;
    }

    public String getAffixFor(String itemName) {
        return itemName.substring(getBaseResource().length() + 1);
    }

    @Override
    public String getBaseResource() {
        return name;
    }

    public GenericResourceType withItemAffixes(String... affixes) {
        this.itemAffixes.addAll(Arrays.asList(affixes));
        return this;
    }

    @Override
    public boolean contains(String itemName) {
        if (itemName.equals(name) && itemAffixes.contains("")) {
            return true; //matches empty affix
        }
        if (!itemName.startsWith(getBaseResource()+"_")) {
            return false; //not even our prefix
        }
        String affix = getAffixFor(itemName);
        
        return (itemAffixes.contains(affix) || blockAffixes.keySet().contains(affix));
    }

    @Override
    public Item getItem(String itemName) {
        String fullName = (itemName!=null && !itemName.isEmpty()) ? name+"_"+itemName : name;
        
        Identifier id = new Identifier("c", fullName); //Because CommonItems.getItem has a condition flipped
        return Registry.ITEM.getOrEmpty(id).orElse(null);
    }

    public Item registerItem(String itemName) {
        return CommonItems.register(itemName, new Item((new Item.Settings()).group(CottonCommons.ITEM_GROUP)));
    }

    @Override
    public Block getBlock(String blockName) {
        Block existing = (blockName!=null) ? CommonBlocks.getBlock(name+"_"+blockName) : CommonBlocks.getBlock(name);
        if (existing != null) {
            return existing;
        }
        else {
            CottonResources.LOGGER.warn("No block found with name " + blockName + "!");
            return null;
        }
    }

    public Block registerBlock(String blockName) {
        String affix = getAffixFor(blockName);
        Supplier<Block> blockSupplier = blockAffixes.get(affix);
        if (blockSupplier == null) {
            return null;
        }
        
        Block resultBlock = blockSupplier.get();
        BlockItem resultItem =  new BlockItem(resultBlock, new Item.Settings().group(CottonCommons.ITEM_GROUP)); //Shouldn't be necessary, but is?
        
        return CommonBlocks.register(blockName, resultBlock, resultItem);
    }
    
    public Collection<String> getAffixes() {
    	return ImmutableSet.<String>builder().addAll(itemAffixes).addAll(blockAffixes.keySet()).build();
    }
    
    @Override
    public void registerAll() {
        registerBlocks();
        registerItems();
    }

    public void registerBlocks() {
        for (String affix : blockAffixes.keySet()) {
            registerBlock(getFullNameForAffix(affix));
            //CottonResources.LOGGER.devInfo("Registered block " + getFullNameForAffix(affix) + "!");
        }
    }

    public void registerItems() {
        for (String affix : itemAffixes) {
            registerItem(getFullNameForAffix(affix));
            //CottonResources.LOGGER.devInfo("Registered item " + getFullNameForAffix(affix) + "!");
        }
    }
}
